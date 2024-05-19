package com.moviezone.dai_api.model.dao;

import com.google.gson.JsonArray;
import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.model.entity.Movie;

import java.util.List;

public interface IMovieDAO {
    public Movie getMovieDetails(int movieId);
    public JsonArray discover(String page, String genres);
    public JsonArray search(String search, String page);
}
