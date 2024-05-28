package com.moviezone.dai_api.controller;


import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.service.IUserService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/images")
    public ResponseEntity<?> modifyImage(@RequestParam("archivo") MultipartFile archivo){

        String URL = "https://dai-obs.obs.la-south-2.myhuaweicloud.com";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);

        return null;
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
