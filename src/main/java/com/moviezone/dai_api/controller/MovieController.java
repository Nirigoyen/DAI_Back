package com.moviezone.dai_api.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;

import com.moviezone.dai_api.utils.ErrorResponse;
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
    public ResponseEntity<?> discover(@RequestParam(name = "page", required = true) String page, @RequestParam(name = "genres", required = false) String genres) {
        //List<MovieComponentDTO>
        // Query params validation
        final String RELEASE_DATE_LOWER_THAN = "2024-05-01"; // Final means CONSTANT - DATE FORMAT YYYY/MM/DD
        if (page == null) return new ResponseEntity<>(new ErrorResponse("Bad Request, mandatory parameters not sent", 4), HttpStatus.BAD_REQUEST);
        // since movie pages range is between 1 and 500...
        else if (Integer.parseInt(page) < 1) page = "1"; // if page is less than 1 return page 1
        else if (Integer.parseInt(page) > 500) page = "500"; // if page is greater than 500 return page 500
        if (genres == null) genres = ""; // Genre formatting : <GenreID>%2C<GenreID>%2C>GenreID> - Example: 28%2C18 - GenreName to GenreId should be handled for frontend

        String API_URL = "https://api.themoviedb.org/3/discover/movie" +
                "?include_adult=false" +
                "&include_video=false" +
                "&language=es-AR" +
                "&page=" + page +
                "&release_date.lte=" + RELEASE_DATE_LOWER_THAN +
                "&sort_by=primary_release_date.desc&with_genres=" + genres;


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
                try { // If a posterPath doesn't exist, the movie is not added to the list
                    String newMovieImagePosterPath = ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER, TMDBmovie.get("poster_path").getAsString()); // get "poster_path" field from JSON

                    newMovie.setMovieId(newMovieId);
                    newMovie.setMoviePosterPath(newMovieImagePosterPath);
                    allMovieTest.add(newMovie);
                } catch (Exception ignored){

                }
            }

            //System.out.println("All Movies = " + allMovieTest);
            return new ResponseEntity<>(allMovieTest, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new ErrorResponse("Resource Not Found.", 3), HttpStatus.NOT_FOUND);
        }
    }

}


