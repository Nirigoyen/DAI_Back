package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Rating;
import com.moviezone.dai_api.model.entity.User;

import java.util.List;

public interface IRatingDAO {
    public int rateMovie(String movieId, User user, int rating);
    public Rating getRatingByUserAndMovie(String movieId, String userId);
    public int modifyRating(Rating rating);
    public int countRatings(String movieId);
    public List<Rating> getAllRatingsByMovieId(String movieId);
}
