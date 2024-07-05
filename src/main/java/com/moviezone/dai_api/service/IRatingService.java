package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.dto.RatingDTO;

import java.util.List;

public interface IRatingService {
    public int rateMovie(RatingDTO rating);
    public int getRatingByUserAndMovie(int movieId, String userId);
}
