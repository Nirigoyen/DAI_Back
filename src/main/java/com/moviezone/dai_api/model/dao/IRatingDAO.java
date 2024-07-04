package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Rating;

import java.util.List;

public interface IRatingDAO {
    public void rateMovie(int id, int rating, int userId);
    public int getRatingByUserAndMovie(int movieId, int userId);
    public void modifyRating(int id, int rating, int userId);
}
