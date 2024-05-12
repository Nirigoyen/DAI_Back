package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Rating;

import java.util.List;

public class IRatingImplementation implements IRating{
    @Override
    public void rateMovie(int id, int rating, int userId) {

    }

    @Override
    public List<Rating> getRatingsByUser(int userId) {
        return List.of();
    }

    @Override
    public void modifyRating(int id, int rating, int userId) {

    }
}
