package com.moviezone.dai_api.model.dto;

public class MovieComponentDTO {
    private int movieId;
    private String moviePosterPath;

    public MovieComponentDTO() {
    }

    public MovieComponentDTO(int movieId, String moviePosterPath) {
        this.movieId = movieId;
        this.moviePosterPath = moviePosterPath;
    }


    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }
}
