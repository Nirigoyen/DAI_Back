package com.moviezone.dai_api.controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huaweicloud.sdk.obs.v1.model.Bucket;
import com.moviezone.dai_api.model.dto.AuthResponseDTO;
import com.moviezone.dai_api.model.dto.ErrorResponseDTO;
import com.moviezone.dai_api.model.dto.TokenDTO;
import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.model.entity.RefreshToken;
import com.moviezone.dai_api.service.IRefreshTokenService;
import com.moviezone.dai_api.service.IUserService;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.util.Date;

@RestController
@RequestMapping("/v1/auths")
public class AuthenticationController {
//    private final int EXPIRATION_TIME = 60 * 1000 * 5; //* 1000 milisegundos ( 1 segundo ) * 60 ( PARA QUE DE 1 MINUTO ) * 5 ( PARA QUE DE 5 MINUTOS )
    private final int EXPIRATION_TIME = 60 * 1000 * 2; //* 1000 milisegundos ( 1 segundo ) * 60 ( PARA QUE DE 1 MINUTO ) * 2 ( PARA QUE DE 2 MINUTOS )


    @Autowired
    private IUserService userService;
    @Autowired
    private IRefreshTokenService refreshTokenService;
    @Autowired
    private SecretKey secretKey;

    @DeleteMapping
    public ResponseEntity<?> logout(@RequestBody TokenDTO token)
    {
        //! ESTO SE PUEDE HACER DE VARIAS MANERAS, ACA LE ESTAMOS PIDIENDO AL FRONT EL REFRESH TOKEN
        //! EN VEZ DEL TOKEN SE LE PODRIA PEDIR TAMBIEN EL USER ID
        RefreshToken refreshToken = refreshTokenService.findByRefreshToken(token.getToken());
        refreshTokenService.delete(refreshToken);
        return new ResponseEntity<>("Successful logout", HttpStatus.NO_CONTENT);
    }


    @PutMapping("")
    ResponseEntity<?> refreshToken(@RequestBody TokenDTO refreshToken){

        //* Buscamos si el REFRESH TOKEN que nos pasan esta en la DB
        RefreshToken persistedRefreshToken = refreshTokenService.findByRefreshToken(refreshToken.getToken());

        //* Si el refresh token no existe en la DB devolvemos bad request ( DESDE EL FRONT DEBERAIN DESLOGGEAR )
        if(persistedRefreshToken == null){ return new ResponseEntity<>(new ErrorResponseDTO("El REFRESH TOKEN no existe", 2), HttpStatus.BAD_REQUEST);}

        //* CHEQUEAMOS SI ES VALIDO, SI NO LO ES TIRA ERROR POR ESO EN UN BLOQUE TRY CATCH
        try {
            persistedRefreshToken =  refreshTokenService.verifyRefreshTokenExpiration(persistedRefreshToken);

            //* CREAMOS OTRO JWT ACCESS TOKEN CON EL ID DEL USUARIO
            String token = Jwts.builder()
                    .setSubject(persistedRefreshToken.getUser().getId())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();

            //* CREAMOS EL DTO PARA DEVOLVER EL TOKEN Y EL MISMO REFRESH TOKEN
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, persistedRefreshToken.getToken());

            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        } catch (Exception ex){

            //* SI EL REFRESH TOKEN VENCIO DEVOLVEMOS FORBIDDEN
            return new ResponseEntity<>(new ErrorResponseDTO(ex.getMessage(), 3), HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody TokenDTO googleToken) {

        final String GOOGLE_TOKEN_VALIDATION_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=" + googleToken.getToken(); // Should change this https://developers.google.com/identity/sign-in/web/backend-auth?hl=es-419

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_TOKEN_VALIDATION_URL, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK)
            return new ResponseEntity<>(new ErrorResponseDTO("INVALID GOOGLE ACCOUNT", 1), HttpStatus.CONFLICT); // Si el token es invalido no devolver JWT

        String data = response.getBody();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(data);

        String userId = jsonObject.get("sub").getAsString();

        if (userService.getUser(userId) == null){ // Si El usuario no esta en nuestra BD registrarlo
            UserDTO user = createUserDTO(jsonObject);

            userService.createUser(user);
        }
        else if (refreshTokenService.findByUser(userId)){ // Si el usuario ya esta registrado y tiene un refresh token, borrarlo
            refreshTokenService.deleteByUser(userId);
        }

//        if (refreshTokenService.findByUser("1")){ // Si el usuario ya esta registrado y tiene un refresh token, borrarlo
//            refreshTokenService.deleteByUser("1");
//        }

//        String givenNameTest = "TheMaxcraft1"; // TEST

        //* ACCESS Token ( JWT )
        String token = Jwts.builder()
                .setSubject(jsonObject.get("given_name").toString())
//                .setSubject(givenNameTest) //TEST
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        //* REFRESH Token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken("1"); //TEST

        //* Creamos el DTO para devolver ACCESS y REFRESH tokens
        AuthResponseDTO loginResponse = new AuthResponseDTO();
        loginResponse.setAccessToken(token);
        loginResponse.setRefreshToken(refreshToken.getToken());

        //* DEVOLVEMOS LA RESPUESTA
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);

        //return new ResponseEntity<>(data, HttpStatus.OK);

    }

    private UserDTO createUserDTO(JsonObject userJSON){
        UserDTO user = new UserDTO();
        user.setId(userJSON.get("sub").getAsString());
        user.setUsername(userJSON.get("name").getAsString());
        user.setName(userJSON.get("given_name").getAsString());
        user.setEmail(userJSON.get("email").getAsString());

        if (userJSON.has("family_name")) user.setLastName(userJSON.get("family_name").getAsString());

        if (userJSON.has("picture")) user.setProfilePictureURL(uploadImage(userJSON));

        return user;
    }

    private static String uploadImage(JsonObject userJSON) {

        String finalURL = "";

        String imgURL = userJSON.get("picture").getAsString();

        RestTemplate restTemplateGet = new RestTemplate();

        ByteArrayResource byteArrayResource = restTemplateGet.getForObject(imgURL, ByteArrayResource.class);

        Bucket bucket = new Bucket();
        bucket.setName(System.getenv("BUCKET_NAME"));

        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(System.getenv("OBS_URL"));

        String userid = "profile-pictures/" + userJSON.get("sub").getAsString() + ".jpg";

        try {
            ObsClient obsClient = new ObsClient(config);
            obsClient.putObject(System.getenv("BUCKET_NAME"), userid, new ByteArrayInputStream(byteArrayResource.getByteArray()), null);
            finalURL = System.getenv("OBS_URL") + userid;
        }catch (Exception e){}

        return finalURL;
    }


}
