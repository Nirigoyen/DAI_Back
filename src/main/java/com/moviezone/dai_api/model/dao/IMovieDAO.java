package com.moviezone.dai_api.model.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface IMovieDAO {
    public JsonObject getMovieDetails(int movieId);
    public JsonArray discover(String page, String genres);
    public JsonArray search(String search);
    public JsonArray getGenres(int movieId);
    public JsonArray getImages(int movieId);
    public JsonObject getCredits(int movieId);
    public String getTrailer(int movieId);
    public String getCertificacion(int movieId);
}
