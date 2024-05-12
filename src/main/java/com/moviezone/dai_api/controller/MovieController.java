package com.moviezone.dai_api.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.utils.IMAGE_TYPE;
import com.moviezone.dai_api.utils.ImageLinks;
import io.github.cdimascio.dotenv.Dotenv;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {


    @GetMapping(value = "")
    public List<MovieComponentDTO> discover() throws IOException {
        //Dotenv dotenv = Dotenv.load();
        String API_URL = "https://api.themoviedb.org/3/discover/movie";


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  Dotenv.load().get("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();
            //return response;

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(datos);
            JsonArray allMovies = jsonObject.getAsJsonArray("results");
            Gson gson = new Gson();
            ArrayList<?> jsonObjList = gson.fromJson(allMovies, ArrayList.class);
            //ArrayList<?> auxArray = gson.fromJson(jsonObjList.get(0).toString(), ArrayList.class);
            String movieId;
            List<MovieComponentDTO> allMovieTest = new ArrayList<>();
            System.out.println(jsonObjList.get(0).toString());
            for(Object movie : jsonObjList){
                //String movie = jsonObjList.get(i).toString();
                MovieComponentDTO movieDTO = new MovieComponentDTO();
                String[] movieAttributes = movie.toString().split(",");
                for(String attribute: movieAttributes){
                    if (attribute.contains("id=")) movieDTO.setMovieId((int)Float.parseFloat(attribute.split("=")[1]));
                    if (attribute.contains("poster_path")) movieDTO.setMoviePosterPath(ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER)+attribute.split("=")[1]);
                if (!allMovieTest.contains(movieDTO))
                    allMovieTest.add(movieDTO);
                }
            }

            System.out.println("All Movies = " + allMovieTest);


            //return new ResponseEntity<List<MovieComponentDTO>>(allMovieTest, HttpStatus.OK);
            return allMovieTest;

        } else {
            return null;
            //return new ResponseEntity<String>("Error en la petición", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


