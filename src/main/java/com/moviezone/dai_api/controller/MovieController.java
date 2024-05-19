package com.moviezone.dai_api.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;

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
@RequestMapping("/movies")
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
    public ResponseEntity<?> search(@RequestParam(name = "page", required = true) String page, @RequestParam(name = "search", required = true) String search, @RequestParam(name = "orderBy", required = false) String orderBy, @RequestParam(name = "ordering", required = false) String ordering) {

        JsonArray finalResponse = new JsonArray();

        String API_URL = "https://api.themoviedb.org/3/search/multi" +
                "?query="+ search +
                "&include_adult=false" +
                "&language=es-AR" +
                "&page=" + page;


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization",  Dotenv.load().get("TMDB_TOKEN")  );
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL,HttpMethod.GET, entity, String.class);

        if (response.getStatusCodeValue() == 200) {

            String datos = response.getBody();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(datos);
            JsonArray allMovies = jsonObject.getAsJsonArray("results");

            JsonObject person = allMovies.get(0).getAsJsonObject();

            if (person.get("media_type").getAsString().equals("person")) { //? ES UNA PERSONA
                for (int i = Integer.parseInt(page); i <= Integer.parseInt(page) + 2; i++) { //* HACEMOS LAS 3 REQUESTS
                    String NEW_API_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-AR" +
                            "&page=" + i +
                            "&sort_by=popularity.desc&with_cast=" +
                            person.get("id");

                    restTemplate = new RestTemplate();
                    headers = new HttpHeaders();
                    headers.add("accept", "application/json");
                    headers.add("Authorization",  Dotenv.load().get("TMDB_TOKEN")  );
                    entity = new HttpEntity<String>(headers);

                    response = restTemplate.exchange(NEW_API_URL,HttpMethod.GET, entity, String.class);

                    if (response.getStatusCodeValue() == 200) {

                        datos = response.getBody();
                        parser = new JsonParser();
                        jsonObject = (JsonObject) parser.parse(datos);
                        allMovies = jsonObject.getAsJsonArray("results");

                        finalResponse.addAll(allMovies);
                    }
                }
            }

            else { //? NO ES UNA PERSONA

                //* AGREGAMOS LOS VALORES DE LA PRIMERA REQUEST
                finalResponse.addAll(allMovies);

                for (int i = Integer.parseInt(page) + 1; i <= Integer.parseInt(page) + 2; i++) { //* HACEMOS LAS 2 REQUESTS RESTANTES
                    String NEW_API_URL = "https://api.themoviedb.org/3/search/multi" +
                            "?query="+ search +
                            "&include_adult=false" +
                            "&language=es-AR" +
                            "&page=" + i;

                    restTemplate = new RestTemplate();
                    headers = new HttpHeaders();
                    headers.add("accept", "application/json");
                    headers.add("Authorization",  Dotenv.load().get("TMDB_TOKEN")  );
                    entity = new HttpEntity<String>(headers);

                    response = restTemplate.exchange(NEW_API_URL,HttpMethod.GET, entity, String.class);

                    if (response.getStatusCodeValue() == 200) {

                        datos = response.getBody();
                        parser = new JsonParser();
                        jsonObject = (JsonObject) parser.parse(datos);
                        allMovies = jsonObject.getAsJsonArray("results");

                        finalResponse.addAll(allMovies);
                    }
                }
            }
        }




//        String sumaResponse = "";
//        //List<?> finalResponse = new ArrayList<>();
//
//
//for (int i = Integer.parseInt(page); i <= Integer.parseInt(page)+2; i++) {
//
//            if (response.getStatusCodeValue() == 200) {
//
//                String datos = response.getBody();
//                //return response;
//                sumaResponse += response.getBody();
//
//                JsonParser parser = new JsonParser();
//                JsonObject jsonObject = (JsonObject) parser.parse(datos);
//
//
//                JsonArray allMovies = jsonObject.getAsJsonArray("results");
//                //System.out.println(allMovies);
//                //List<JsonElement> prueba = allMovies.asList();
//                //prueba.addAll(allMovies);
//
//                finalResponse.addAll(allMovies);
//            }
//
//        }
//        System.out.println(finalResponse);

        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }

}


