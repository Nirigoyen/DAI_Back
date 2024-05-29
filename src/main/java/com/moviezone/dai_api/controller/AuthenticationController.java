package com.moviezone.dai_api.controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huaweicloud.sdk.obs.v1.model.Bucket;
import com.moviezone.dai_api.model.dto.TokenDTO;
import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.service.IUserService;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;

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
    public ResponseEntity<String> login(@RequestBody TokenDTO googleToken) {

        final String GOOGLE_TOKEN_VALIDATION_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=" + googleToken.getToken(); // Should change this https://developers.google.com/identity/sign-in/web/backend-auth?hl=es-419

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

        String userId = jsonObject.get("sub").getAsString();

        if (userService.findUserById(userId) == null){ // Si El usuario no esta en nuestra BD registrarlo
            UserDTO user = createUserDTO(jsonObject);

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
        bucket.setName(Dotenv.load().get("BUCKET_NAME")); //* HACER ENV

        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(Dotenv.load().get("OBS_URL")); //* HACER ENV

        String userid = "profile-pictures/" + userJSON.get("sub").getAsString() + ".jpg";

        try {
            ObsClient obsClient = new ObsClient(config);
            obsClient.putObject(Dotenv.load().get("BUCKET_NAME"), userid, new ByteArrayInputStream(byteArrayResource.getByteArray()), null);
            finalURL = Dotenv.load().get("OBS_URL") + userid;
        }catch (Exception e){}

        return finalURL;
    }


}
