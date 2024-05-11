package com.moviezone.dai_api.controller;

import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {


    @GetMapping(value = "")
    public ResponseEntity<String> discover() throws IOException {

        String API_URL = "https://api.themoviedb.org/3/discover/movie";


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNjUxODkzODQ4NDEwYWE4OThmMzQ5MTYwZjc4MjFkZiIsInN1YiI6IjY1ZmNkMzFjMzUyMGU4MDE2NWQ1MDZjZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.d_ivgbfrcNn_yuTciheY2saSHuS2F2a5ZAAZds_uGU8");
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {
            //String datos = response.getBody();
            //return new ResponseEntity<String>(datos, HttpStatus.OK);
            return response;
        } else {
            return new ResponseEntity<String>("Error en la petición", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


