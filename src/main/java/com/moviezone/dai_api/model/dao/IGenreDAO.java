package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Genre;

public interface IGenreDAO {
    public Genre getGenreById(int id);
    public void save(Genre genre);
}
