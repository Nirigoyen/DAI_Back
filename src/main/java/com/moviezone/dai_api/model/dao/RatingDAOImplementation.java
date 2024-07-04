package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingDAOImplementation implements IRatingDAO {
    @Override
    public void rateMovie (int movieId, int rating, int userId) {

    }

    @Override
    public int getRatingByUserAndMovie (int movieId, int userId) {
        return 0;
    }

    @Override
    public void modifyRating (int movieId, int newRating, int userId) {

    }
}
