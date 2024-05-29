package com.moviezone.dai_api.controller;


import com.google.gson.JsonObject;
import com.moviezone.dai_api.model.dto.HealthDTO;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/v1/health")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping(value = "")
    public ResponseEntity<?> health() {

        HealthDTO healthDTO = new HealthDTO();


        String API_URL = "https://api.themoviedb.org/3/configuration" ;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  Dotenv.load().get("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {
            healthDTO.setAPI_STATUS(true);
        }

        try {
            Connection connection =  dataSource.getConnection();
            if (connection.isValid(2))
                healthDTO.setDB_STATUS(true);
        } catch (Exception e) {
        }


        return new ResponseEntity<>(healthDTO, HttpStatus.OK);
    }
}
