package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.GenreDTO;
import com.moviezone.dai_api.model.entity.Genre;

public interface IGenreService {
    public Genre getGenreById(int id);
    public GenreDTO toDTO(Genre genre);
}
