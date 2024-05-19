package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.MovieComponentDTO;

import java.util.List;

public class FavouriteServiceImplementation implements IFavouriteService{
    @Override
    public void addFavourite(int userId, int movieId) {

    }

    @Override
    public void removeFavourite(int userId, int movieId) {

    }

    @Override
    public List<MovieComponentDTO> getFavouritesFromUser(int userId) {
        return List.of();
    }
}
