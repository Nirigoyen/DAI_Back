package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.MovieComponentDTO;

import java.util.List;

public interface IFavouriteService {
    public void addFavourite(int userId, int movieId);
    public void removeFavourite(int userId, int movieId);
    public List<MovieComponentDTO> getFavouritesFromUser(int userId);
}
