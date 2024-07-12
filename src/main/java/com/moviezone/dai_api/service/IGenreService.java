package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.GenreDTO;

public interface IGenreService {
    public GenreDTO getGenreById(int id);
}
