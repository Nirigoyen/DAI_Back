package com.moviezone.dai_api.model.dto;

public class RatingDTO {
    private int id;
    private int rating;
    private int movieId;
    private int userId;

    public RatingDTO(int id, int rating, int movieId, int userId) {
        this.id = id;
        this.rating = rating;
        this.movieId = movieId;
        this.userId = userId;
    }

    public RatingDTO() {
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getUserId() {
        return userId;
    }
}
