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

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {


    @GetMapping(value = "")
    public List<MovieComponentDTO> discover(@RequestParam(name = "page") int page) {
        //Dotenv dotenv = Dotenv.load();
        if (page == 0) page = 1;
        String API_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-AR&page=" + page + "&release_date.lte=" + "2024-05-15" + "&sort_by=primary_release_date.desc";

        //https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-AR&page=2&release_date.lte=2024-12-31&sort_by=primary_release_date.desc'


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

            List<MovieComponentDTO> allMovieTest = new ArrayList<>();

            for (int i = 0; i < allMovies.size(); i++) {
                MovieComponentDTO newMovie = new MovieComponentDTO();
                JsonObject TMDBmovie = allMovies.get(i).getAsJsonObject();

                int newMovieId = TMDBmovie.get("id").getAsInt(); // Get "ID" field from JSON
                String newMovieImagePosterPath = ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER, TMDBmovie.get("poster_path").getAsString()); // get "poster_path" field from JSON

                newMovie.setMovieId(newMovieId);
                newMovie.setMoviePosterPath(newMovieImagePosterPath);
                allMovieTest.add(newMovie);

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


