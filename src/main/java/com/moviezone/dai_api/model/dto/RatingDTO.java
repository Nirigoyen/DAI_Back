package com.moviezone.dai_api.model.dto;


//! NO IMPLEMENTADO
public class RatingDTO {
    private String rating;
    private String movieId;
    private String userId;

    public RatingDTO(String rating, String movieId, String userId) {
        this.rating = rating;
        this.movieId = movieId;
        this.userId = userId;
    }

    public RatingDTO() {
    }
    public String getRating() {
        return rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getUserId() {
        return userId;
    }
}
