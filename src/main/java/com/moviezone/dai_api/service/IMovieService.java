package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.dto.MovieDTO;

import java.util.List;

public interface IMovieService {
    public MovieDTO getMovieDetails(int movieId, String userId);
    public List<MovieComponentDTO> discover(String page, String genres);
    public List<MovieComponentDTO> search(String page, String search, String orderByScore, String orderingScore,
                                          String orderByDate, String orderingDate);
}
