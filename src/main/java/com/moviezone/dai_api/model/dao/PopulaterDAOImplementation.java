package com.moviezone.dai_api.model.dao;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moviezone.dai_api.model.entity.MovieDB;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class PopulaterDAOImplementation implements IPopulaterDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public JsonArray populateDB() {

        JsonArray finalResponse = new JsonArray();

        for (int page = 1; page<=50; page++) {

            LocalDate fecha = LocalDate.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fecha_actual = fecha.format(formato);

            String API_URL = "https://api.themoviedb.org/3/discover/movie" +
                    "?include_adult=false" +
                    "&include_video=false" +
                    "&language=es-AR" +
                    "&page=" + page +
                    "&release_date.lte=" + fecha_actual +
                    "&sort_by=primary_release_date.desc&";


            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "application/json");
            headers.add("Authorization", Dotenv.load().get("TMDB_TOKEN") );
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);

            if (response.getStatusCodeValue() == 200) {

                String datos = response.getBody();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject) parser.parse(datos);
                JsonArray allMovies = jsonObject.getAsJsonArray("results");


                //* AGREGAMOS LOS VALORES DE LA PRIMERA REQUEST
                finalResponse.addAll(allMovies);
            }

        }
        return finalResponse;
    }

    @Override
    @Transactional
    public void saveMovies(MovieDB movies) {

        Session currentSession = entityManager.unwrap(Session.class);


        currentSession.persist(movies);



    }
}
