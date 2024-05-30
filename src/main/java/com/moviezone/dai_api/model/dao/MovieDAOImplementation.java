package com.moviezone.dai_api.model.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.entity.Movie;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Repository
public class MovieDAOImplementation implements IMovieDAO {


    public Movie getMovieDetails(int movieId) { //! NO IMPLEMENTADO

        Movie movie = new Movie();

        return movie;
    }

    @Override
    public JsonArray discover(String page, String genres) { //? RESULTADOS DE LA LANDING PAGE

        //* FIJAMOS QUE SEAN PELICULAS QUE HAYAN SALIDO YA
        final String RELEASE_DATE_LOWER_THAN = "2024-05-01"; // Final means CONSTANT - DATE FORMAT YYYY/MM/DD

        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha_actual = fecha.format(formato);

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

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(datos);
            JsonArray allMovies = jsonObject.getAsJsonArray("results");

            return allMovies;
        }
        else
            return null;

    }
    @Override
    public JsonArray search(String search) {

        final int MAX_PAGES_TO_RETURN = 6;

        JsonArray finalResponse = new JsonArray();

        String API_URL = "https://api.themoviedb.org/3/search/multi" +
                "?query="+ search +
                "&include_adult=false" +
                "&language=es-AR" +
                "&page=1";


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

                //* HACEMOS EL PRIMER LLAMADO PARA VER LA CANTIDAD DE PAGINAS DE RESULTADOS

                String NEW_API_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-AR" +
                        "&page=" + 1 +
                        "&sort_by=popularity.desc&with_cast=" +
                        person.get("id");

                response = restTemplate.exchange(NEW_API_URL,HttpMethod.GET, entity, String.class);

                if (response.getStatusCodeValue() == 200) {

                    datos = response.getBody();
                    parser = new JsonParser();
                    jsonObject = (JsonObject) parser.parse(datos);
                    allMovies = jsonObject.getAsJsonArray("results");

                    //* AGREGAMOS LOS VALORES DE LA PRIMERA REQUEST

                    finalResponse.addAll(allMovies);
                }

                //* VERIFICAMOS LA CANTIDAD DE PAGINAS DE RESULTADOS. COLOCAMOS UN MAXIMO DE 6 PAGINAS (120 RESULTADOS).

                int pages_count = Math.min(jsonObject.get("total_pages").getAsInt(), MAX_PAGES_TO_RETURN);


                //* HACEMOS LAS REQUESTS RESTANTES HASTA OBTENER TODOS LOS VALORES

                for (int i = 2; i <= pages_count; i++) {
                    NEW_API_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=es-AR" +
                            "&page=" + i +
                            "&sort_by=popularity.desc&with_cast=" +
                            person.get("id");


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

                //* HACEMOS EL PRIMER LLAMADO PARA VER LA CANTIDAD DE PAGINAS DE RESULTADOS

                String NEW_API_URL = "https://api.themoviedb.org/3/search/movie" +
                        "?query="+ search +
                        "&include_adult=false" +
                        "&language=es-AR" +
                        "&page=" + 1;

                response = restTemplate.exchange(NEW_API_URL,HttpMethod.GET, entity, String.class);

                if (response.getStatusCodeValue() == 200) {

                    datos = response.getBody();
                    parser = new JsonParser();
                    jsonObject = (JsonObject) parser.parse(datos);
                    allMovies = jsonObject.getAsJsonArray("results");

                    //* AGREGAMOS LOS VALORES DE LA PRIMERA REQUEST

                    finalResponse.addAll(allMovies);
                }

                //* VERIFICAMOS LA CANTIDAD DE PAGINAS DE RESULTADOS. COLOCAMOS UN MAXIMO DE 6 PAGINAS (120 RESULTADOS).
                int pages_count = Math.min(jsonObject.get("total_pages").getAsInt(), MAX_PAGES_TO_RETURN);

                for (int i = 2; i <= pages_count; i++) { //* HACEMOS LAS REQUESTS RESTANTES HASTA OBTENER TODOS LOS VALORES
                    NEW_API_URL = "https://api.themoviedb.org/3/search/movie" +
                            "?query="+ search +
                            "&include_adult=false" +
                            "&language=es-AR" +
                            "&page=" + i;

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
        return finalResponse;
    }
}
