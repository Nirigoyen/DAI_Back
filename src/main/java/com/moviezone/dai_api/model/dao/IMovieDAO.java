package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.model.entity.MovieE1;

import java.util.List;

public interface IMovieDAO {
    public MovieE1 findMovie(int id);
    public void addMovie(MovieE1 movie);
    public List<MovieDTO> discover();
}
