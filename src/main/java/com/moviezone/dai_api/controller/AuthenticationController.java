package com.moviezone.dai_api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auths")
public class AuthenticationController {
    private final int EXPIRATION_TIME = 864_000_000; // 10 days
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

}