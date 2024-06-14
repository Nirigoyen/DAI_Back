package com.moviezone.dai_api.model.dao;

import com.google.gson.JsonArray;
import com.moviezone.dai_api.model.entity.MovieDB;

import java.util.List;

public interface IPopulaterDAO {
    JsonArray populateDB();

    void saveMovies(MovieDB movies);

    boolean findMovieById(int id);
}
