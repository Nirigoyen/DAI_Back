package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.dto.RatingDTO;
import com.moviezone.dai_api.model.entity.Rating;
import com.moviezone.dai_api.model.entity.User;

import java.util.List;

public interface IRatingDAO {
    public int rateMovie(int movieId, User user, int rating);
    public Rating getRatingByUserAndMovie(int movieId, String userId);
    public int modifyRating(int movieId, User user, int rating);
}
