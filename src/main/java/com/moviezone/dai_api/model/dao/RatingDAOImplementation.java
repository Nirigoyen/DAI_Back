package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingDAOImplementation implements IRatingDAO {
    @Override
    public void rateMovie(int movieId, int rating, int userId) {

    }

    @Override
    public List<Rating> getRatingsByUser(int userId) {
        return List.of();
    }

    @Override
    public void modifyRating(int movieId, int newRating, int userId) {

    }
}
