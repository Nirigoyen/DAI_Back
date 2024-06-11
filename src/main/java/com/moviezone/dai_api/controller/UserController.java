package com.moviezone.dai_api.controller;




import com.moviezone.dai_api.model.dto.ErrorResponseDTO;
import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.model.dto.UserEditableDTO;
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
    public ResponseEntity<?> modifyUser(@RequestBody UserEditableDTO userDTO) {

        if (userDTO == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        if (userDTO.getBase64img().equals("")) userDTO.setBase64img(null);

        UserDTO result = userServiceImplementation.modifyUser(userDTO.getId(), userDTO, userDTO.getBase64img());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        if (userId == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        userServiceImplementation.deleteById(userId);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value="/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId){
        if (userId == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        UserDTO result = userServiceImplementation.getUser(userId);
        if (result == null ) return new ResponseEntity<>(new ErrorResponseDTO("USER NOT FOUND",1), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}



