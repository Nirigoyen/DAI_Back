package com.moviezone.dai_api.model.dto;

public class MovieIdDTO {
    private int movieId;

    public MovieIdDTO() {
    }

    public MovieIdDTO(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
