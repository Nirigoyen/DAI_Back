package com.moviezone.dai_api.controller;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.dto.TokenDTO;
import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

        System.out.println(data);

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(data);

        System.out.println(jsonObject);

        String userId = jsonObject.get("sub").getAsString();

        if (userService.findUserById(userId) == null){ // Si El usuario no esta en nuestra BD registrarlo
            UserDTO user = new UserDTO();
            // Datos que estan si o si
            user.setId(userId);
            user.setEmail(jsonObject.get("email").getAsString());
            user.setName(jsonObject.get("given_name").getAsString());

            // Datos que pueden ser null
            if(jsonObject.has("family_name")) user.setLastName(jsonObject.get("family_name").getAsString());
            if(jsonObject.has("picture")) user.setProfilePictureURL(jsonObject.get("picture").getAsString());

            user.setUsername(jsonObject.get("name").getAsString());
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
