package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.IGenreDAO;
import com.moviezone.dai_api.model.dto.GenreDTO;
import com.moviezone.dai_api.model.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImplementation implements IGenreService{

    @Autowired
    private IGenreDAO genreDAO;

    @Override
    public GenreDTO getGenreById(int id) {
        return toDTO(genreDAO.getGenreById(id));
    }


    public GenreDTO toDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setGenreId(genre.getId());
        genreDTO.setGenreName(genre.getGenre());

        return genreDTO;
    }
}
