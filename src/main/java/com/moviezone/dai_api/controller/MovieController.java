package com.moviezone.dai_api.controller;

import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class MovieController {


    @GetMapping(value = "/movies")
    public ResponseEntity<String> discover() throws IOException {

        String API_URL = "https://mi-api.com/recursos";


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);

            if (response.getStatusCodeValue() == 200) {
                String datos = response.getBody();
                return new ResponseEntity<String>(datos, HttpStatus.OK);
            } else {

            }
            return response;
        }





//        String url = "https://api.themoviedb.org/3/discover/movie";
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .addHeader("accept", "application/json")
//                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNjUxODkzODQ4NDEwYWE4OThmMzQ5MTYwZjc4MjFkZiIsInN1YiI6IjY1ZmNkMzFjMzUyMGU4MDE2NWQ1MDZjZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.d_ivgbfrcNn_yuTciheY2saSHuS2F2a5ZAAZds_uGU8")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response;
    }

}
