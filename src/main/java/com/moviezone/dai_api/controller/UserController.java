package com.moviezone.dai_api.controller;


import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.service.IUserService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
    public ResponseEntity<?> modifyImage(@RequestParam String key, @RequestParam("archivo") MultipartFile archivo){
//        String foto = "https://lh3.googleusercontent.com/a/ACg8ocLqCYrKJba4wsLo9oZqHvPhvHv6m9yGDcynwpEBrTmT45Zw7Q=s96-c";
//
//        RestTemplate restTemplateGet = new RestTemplate();
//        HttpHeaders headersGet = new HttpHeaders();
//
//        headersGet.add("accept", "multipart/form-data");
//
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntityGet = new HttpEntity<>(headersGet);
//
//        ResponseEntity<MultipartFile> fotobajada = restTemplateGet.exchange(foto, HttpMethod.GET, requestEntityGet, MultipartFile.class);
//
//        MultipartFile asubir = fotobajada.getBody();
//        String URL = "https://dai-obs.obs.la-south-2.myhuaweicloud.com/";
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("key", key);
//        body.add("file", asubir.getResource());
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntityPost = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, requestEntityPost, String.class);
//
//        return new  ResponseEntity<>(response, HttpStatus.OK);
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
