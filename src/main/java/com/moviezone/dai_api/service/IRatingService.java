package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.MovieComponentDTO;

import java.util.List;

public interface IRatingService {
    public List<MovieComponentDTO> getRatedMoviesFromUser(int userId);
}
