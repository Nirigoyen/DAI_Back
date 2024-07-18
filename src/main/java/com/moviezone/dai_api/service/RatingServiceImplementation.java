package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.IRatingDAO;
import com.moviezone.dai_api.model.dao.IUserDAO;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.dto.RatingDTO;
import com.moviezone.dai_api.model.entity.Rating;
import com.moviezone.dai_api.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImplementation implements IRatingService{

    @Autowired
    private IRatingDAO ratingDAO;

    @Autowired
    private IUserDAO userDAO;


    @Override
    public int rateMovie(RatingDTO rating) {

        User user = userDAO.findUserById(rating.getUserId());

        if (user == null) {
            return -2;
        }

        if (ratingDAO.getRatingByUserAndMovie(rating.getMovieId(), rating.getUserId()) != null) {
            return -3;
        }

        return ratingDAO.rateMovie(rating.getMovieId(), user, Integer.parseInt(rating.getRating()));
    }

    @Override
    public int updateRating(RatingDTO rating) {

        User user = userDAO.findUserById(rating.getUserId());

        if (user == null) {
            return -2;
        }

        if (ratingDAO.getRatingByUserAndMovie(rating.getMovieId(), rating.getUserId()) == null) {
            return -1;
        }
        Rating ratingDB = ratingDAO.getRatingByUserAndMovie(rating.getMovieId(), rating.getUserId());

        ratingDB.setRating(Integer.parseInt(rating.getRating()));

        return ratingDAO.modifyRating(ratingDB);
    }

    @Override
    public int getRatingByUserAndMovie(String movieId, String userId) {

        Rating rating = ratingDAO.getRatingByUserAndMovie(movieId, userId);
        if (rating == null) {
            return -1;
        }
        else {
            return rating.getRating();
        }
    }

    @Override
    public int countRatings(String movieId) {
        return ratingDAO.countRatings(movieId);
    }

}
