package com.moviezone.dai_api.controller;



import com.huaweicloud.sdk.obs.v1.model.Bucket;
import com.huaweicloud.sdk.obs.v1.model.PutObjectRequest;
import com.huaweicloud.sdk.obs.v1.model.PutObjectResponse;
import com.huaweicloud.sdk.obs.v1.region.ObsRegion;
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

        String foto = "https://lh3.googleusercontent.com/a/ACg8ocLqCYrKJba4wsLo9oZqHvPhvHv6m9yGDcynwpEBrTmT45Zw7Q=s96-c";

        RestTemplate restTemplateGet = new RestTemplate();
        HttpHeaders headersGet = new HttpHeaders();
        headersGet.add("accept", "multipart/form-data");

        headersGet.setContentType(MediaType.MULTIPART_FORM_DATA);

        URL url = new URL(foto);

        ByteArrayResource byteArrayResource = restTemplateGet.getForObject(foto, ByteArrayResource.class);


//        Resource resource = new UrlResource(url);
//
//        Path tempfile = Files.createTempFile("tempfile", ".tmp");
//
//        Files.copy(resource.getInputStream(), tempfile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
//
//        System.out.println("termino");

//        ResponseEntity<BufferedImage> fotobajada = restTemplateGet.exchange(foto, HttpMethod.GET, null, BufferedImage.class);
//
//        ImageIO.write(fotobajada.getBody(), "jpg", new ByteArrayOutputStream());
//
//        BufferedImage asubir = ImageIO.write(fotobajada.getBody(), "jpg", new ByteArrayOutputStream());

//        return new ResponseEntity<>(asubir, HttpStatus.OK);

        String URL = "https://dai-obs.obs.la-south-2.myhuaweicloud.com/";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        Bucket bucket = new Bucket();
        bucket.setName("dai-obs");

        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint("obs.la-south-2.myhuaweicloud.com");

        try {
            ObsClient obsClient = new ObsClient(config);
            obsClient.putObject("dai-obs", "pruebajava.jpg", new ByteArrayInputStream(byteArrayResource.getByteArray()), null);
        }catch (Exception e){
            System.out.println(e);}


        return null;



//        InputStream inputStream = new URL(foto).openStream();


//        obsClient.putObject("dai-obs", "pruebajava", inputStream, null);
//
//        PutObjectRequest putObjectRequest = new PutObjectRequest();
//        putObjectRequest.setBucketName("dai-obs");
//        putObjectRequest.setObjectKey("pruebajava");
//        putObjectRequest.setI;
//
//        obsClient.putObject(putObjectRequest);
//
//
//
////        headers.setContentType(MediaType.IMAGE_JPEG);
//
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("key", key);
//        body.add("file", tempfile.toFile());

//        HttpEntity<MultiValueMap<String, Object>> requestEntityPost = new HttpEntity<>(body, headers);

//        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, requestEntityPost, String.class);

//        return new  ResponseEntity<>(response, HttpStatus.OK);
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



