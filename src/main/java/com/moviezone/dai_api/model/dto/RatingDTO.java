package com.moviezone.dai_api.model.dto;


//! NO IMPLEMENTADO
public class RatingDTO {
    private int rating;
    private int movieId;
    private String userId;

    public RatingDTO(int rating, int movieId, String userId) {
        this.rating = rating;
        this.movieId = movieId;
        this.userId = userId;
    }

    public RatingDTO() {
    }
    public int getRating() {
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getUserId() {
        return userId;
    }
}
