package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.model.entity.MovieE1;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class IMovieDAOImplementation implements IMovieDAO {
    public MovieE1 findMovie(int id) {
        return null;
    }

    public void addMovie(MovieE1 movie) {
    }

    @Override
    public List<MovieDTO> discover() {
        //Dotenv dotenv = Dotenv.load();
        String API_URL = "https://api.themoviedb.org/3/discover/movie";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  Dotenv.load().get("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();
            //return new ResponseEntity<String>(datos, HttpStatus.OK);
            //return response;
        } else {
            //return new ResponseEntity<String>("Error en la petición", HttpStatus.INTERNAL_SERVER_ERROR);
            return null;
        };
        return null;
    }

}
