package com.moviezone.dai_api.model.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.model.entity.Movie;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieDAOImplementation implements IMovieDAO {


    public Movie findMovie(int id) {

        Movie movie = new Movie();

        return movie;
    }

    @Override
    public JsonArray discover(String page, String genres) {

        final String RELEASE_DATE_LOWER_THAN = "2024-05-01"; // Final means CONSTANT - DATE FORMAT YYYY/MM/DD

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

            return allMovies;
        }
        else
            return null;

    }
    @Override
    public List<MovieDTO> search(String query) {
        return List.of();
    }

}
