package com.moviezone.dai_api.model.dao;

import com.google.api.client.json.Json;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.model.entity.Movie;

import java.util.List;

public interface IMovieDAO {
    public JsonObject getMovieDetails(int movieId);
    public JsonArray discover(String page, String genres);
    public JsonArray search(String search);
    public JsonArray getGenres(int movieId);
    public JsonArray getImages(int movieId);
    public JsonArray getCast(int movieId);
    public String getTrailer(int movieId);
}
