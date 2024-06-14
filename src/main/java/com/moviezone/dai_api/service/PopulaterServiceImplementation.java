package com.moviezone.dai_api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moviezone.dai_api.model.dao.PopulaterDAOImplementation;
import com.moviezone.dai_api.model.entity.MovieDB;
import com.moviezone.dai_api.utils.IMAGE_TYPE;
import com.moviezone.dai_api.utils.ImageLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopulaterServiceImplementation implements IPopulaterService {

    @Autowired
    private PopulaterDAOImplementation populaterDAO;

    @Override
    public void populateDB() {

        List<MovieDB> movies = new ArrayList<>();

        JsonArray rawMovies = populaterDAO.populateDB();

        for (JsonElement movie : rawMovies) {
            JsonObject movieObject = movie.getAsJsonObject();

            if (movieObject.get("vote_count") == null) continue;
            else if (movieObject.get("vote_count").getAsInt() < 200) continue;


            MovieDB movieDB = new MovieDB();

            movieDB.setId(movieObject.get("id").getAsInt());
            movieDB.setVote_average(movieObject.get("vote_average").getAsDouble());
            movieDB.setRelease_year(Integer.parseInt(movieObject.get("release_date").getAsString().substring(0, 4)));
            movieDB.setPoster_path(ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER, movieObject.get("poster_path").getAsString()));

            movies.add(movieDB);
        }



        populaterDAO.saveMovies(movies.get(0));
    }
}
