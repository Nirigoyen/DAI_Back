package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Movie;
import com.moviezone.dai_api.model.entity.MovieE1;

public interface IMovie {
    public MovieE1 findMovie(int id);
    public void addMovie(MovieE1 movie);
}
