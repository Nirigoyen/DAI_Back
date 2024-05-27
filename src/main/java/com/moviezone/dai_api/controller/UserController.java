package com.moviezone.dai_api.controller;


import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private IUserService userServiceImplementation;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        if (userDTO == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        UserDTO result = userServiceImplementation.createUser(userDTO);

        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping
    public ResponseEntity<?> modifyUser(UserDTO userDTO){

        return null;
    }

//    @DeleteMapping
//    public ResponseEntity<?> deleteUser(int userID){
//        if (userID == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//    }

}
