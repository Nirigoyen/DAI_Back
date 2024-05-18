package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.dto.MovieDTO;

import java.util.List;

public interface IMovieService {
    public void getMovieById(int id);
    public List<MovieComponentDTO> discover(String page, String genres);
}
