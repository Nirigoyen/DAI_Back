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

        User user = userDAO.findUserById(String.valueOf(rating.getUserId()));

        if (user == null) {
            return -2;
        }

        Rating ratingDB = ratingDAO.getRatingByUserAndMovie(rating.getMovieId(), rating.getUserId());

        ratingDAO.rateMovie(rating.getMovieId(), user, rating.getRating());

        if (ratingDAO.getRatingByUserAndMovie(rating.getMovieId(), rating.getUserId()) == null) {
            return ratingDAO.rateMovie(rating.getMovieId(), user, rating.getRating());
        } else {
            return ratingDAO.modifyRating(rating.getMovieId(), user, rating.getRating());
        }
    }

    @Override
    public int getRatingByUserAndMovie(int movieId, String userId) {

        return ratingDAO.getRatingByUserAndMovie(movieId, userId).getRating();
    }

}
