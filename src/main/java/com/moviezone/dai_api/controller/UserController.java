package com.moviezone.dai_api.controller;


import com.moviezone.dai_api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private IUserService userServiceImplementation;

    public ResponseEntity<?> createUser(String username, String name, String lastName, String email, String birthDate){

        
        return null;
    }

}
