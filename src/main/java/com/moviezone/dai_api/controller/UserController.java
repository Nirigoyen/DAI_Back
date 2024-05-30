package com.moviezone.dai_api.controller;




import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/users")
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
    public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO, @RequestBody String base64img){

        if (userDTO == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        UserDTO result = userServiceImplementation.modifyUser(userDTO.getId(), userDTO, base64img);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(String userID){
        if (userID == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        userServiceImplementation.deleteById(userID);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}



