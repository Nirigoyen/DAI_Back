package com.moviezone.dai_api.controller;


import com.google.api.client.googleapis.auth.oauth2.GooglePublicKeysManager;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.dto.GoogleTokenDTO;
import com.moviezone.dai_api.model.entity.User;
import com.moviezone.dai_api.service.IUserService;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.util.Date;

@RestController
@RequestMapping("/v1/auths")
public class AuthenticationController {
    private final int EXPIRATION_TIME = 864_000_000; // 10 days Y esto? xDD

    @Autowired
    private IUserService userService;
//    @Autowired
//    private SecretKey secretKey;

    /*
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> login (@RequestBody UserLoginDTO credentials){
        if (userLoginService.findUser(credentials.getUsername(), credentials.getPassword()) != null){
            String token = Jwts.builder()
                    .setSubject(credentials.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();
            //System.out.println(token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Credenciales Invalidas", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
     */

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody GoogleTokenDTO googleToken) {

        final String GOOGLE_TOKEN_VALIDATION_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=" + googleToken; // Should change this https://developers.google.com/identity/sign-in/web/backend-auth?hl=es-419

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_TOKEN_VALIDATION_URL, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK)
            return new ResponseEntity<>("Invalid GoogleToken", HttpStatus.CONFLICT); // Si el token es invalido no devolver JWT

        String data = response.getBody();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(data);
        long userId = jsonObject.get("sub").getAsLong();

        if (userService.findUserById(userId) == null) // Si El usuario no esta en nuestra BD registrarlo
        {
            User user = new User();
            // Datos que estan si o si
            user.setId(userId);
            user.setEmail(jsonObject.get("email").getAsString());
            user.setName(jsonObject.get("given_name").getAsString());

            // Datos que pueden ser null
            if(jsonObject.has("family_name")) user.setLastName(jsonObject.get("family_name").getAsString());
            if(jsonObject.has("picture")) user.setProfilePicture(jsonObject.get("picture").getAsString());
            //! NO DEVUELVE EL USERNAME, HAY QUE PERMITIR AL USUARIO EDITARLO EN LA APP

            userService.createUser(user);
        }

//        String token = Jwts.builder()
//                .setSubject(jsonObject.get("given_name").toString())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(secretKey, SignatureAlgorithm.HS256)
//                .compact();
//        return new ResponseEntity<>(token, HttpStatus.OK);

        return new ResponseEntity<>(data, HttpStatus.OK);

    }


}
