package com.moviezone.dai_api.controller;



import com.huaweicloud.sdk.obs.v1.model.Bucket;
import com.huaweicloud.sdk.obs.v1.model.PutObjectRequest;
import com.huaweicloud.sdk.obs.v1.model.PutObjectResponse;
import com.huaweicloud.sdk.obs.v1.region.ObsRegion;
import com.moviezone.dai_api.model.dao.UserDAOImplementation;
import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.service.IUserService;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.twelvemonkeys.io.FileUtil;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private IUserService userServiceImplementation;
    @Autowired
    private UserDAOImplementation userDAOImplementation;


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
    public ResponseEntity<?> modifyImage(@RequestParam String key)throws Exception { //, @RequestParam("archivo") File archivo) throws Exception {

//        String fotoGoogle = "https://lh3.googleusercontent.com/a/ACg8ocLqCYrKJba4wsLo9oZqHvPhvHv6m9yGDcynwpEBrTmT45Zw7Q=s96-c";
//
//        RestTemplate restTemplateGet = new RestTemplate();
//        HttpHeaders headersGet = new HttpHeaders();
//        headersGet.add("accept", "multipart/form-data");
//
//        headersGet.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        ByteArrayResource byteArrayResource = restTemplateGet.getForObject(fotoGoogle, ByteArrayResource.class);
//
//        Bucket bucket = new Bucket();
//        bucket.setName("dai-obs");
//
//        ObsConfiguration config = new ObsConfiguration();
//        config.setEndPoint("obs.la-south-2.myhuaweicloud.com");
//
//        try {
//            ObsClient obsClient = new ObsClient(config);
//            obsClient.putObject("dai-obs", "pruebajava.jpg", new ByteArrayInputStream(byteArrayResource.getByteArray()), null);
//        }catch (Exception e){
//            System.out.println(e);}


        return null;
    }


    @PatchMapping
    public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO, @RequestBody String base64img){

        if (userDTO == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        if (base64img == null) base64img = null; //! CHEQUEAR COMO HACERLO BIEN

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



