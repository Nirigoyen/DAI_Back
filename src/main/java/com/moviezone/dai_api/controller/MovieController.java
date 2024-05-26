package com.moviezone.dai_api.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;

import com.moviezone.dai_api.model.entity.Movie;
import com.moviezone.dai_api.service.IMovieService;
import com.moviezone.dai_api.utils.ErrorResponse;
import com.moviezone.dai_api.utils.IMAGE_TYPE;
import com.moviezone.dai_api.utils.ImageLinks;
import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {

    @Autowired
    private IMovieService movieService;

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

        List<MovieComponentDTO> result = movieService.discover(page, genres);
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse("Resource Not Found.", 3), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> search(@RequestParam(name = "search", required = true) String search,
                                    @RequestParam(name = "orderByScore", required = false) String orderByScore,
                                    @RequestParam(name = "orderingScore", required = false) String orderingScore,
                                    @RequestParam(name = "orderByDate", required = false) String orderByDate,
                                    @RequestParam(name = "orderingDate", required = false) String orderingDate) {

        if (search == null) return new ResponseEntity<>(new ErrorResponse("Bad Request, mandatory parameters not sent", 4), HttpStatus.BAD_REQUEST);

        List<MovieComponentDTO> finalResult = movieService.search(search, orderByScore, orderingScore, orderByDate, orderingDate);

        if (finalResult != null) {
            return new ResponseEntity<>(finalResult, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse("Resource Not Found.", 3), HttpStatus.NOT_FOUND);
        
    }

}


