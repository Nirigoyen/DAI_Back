package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.FavDTO;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.entity.FavMovie;

import java.util.List;

public interface IFavouriteService {
    public FavDTO addFavourite(FavDTO fmovie);
    public void removeFavourite(String userId, int movieId);
    public List<FavDTO> getFavouritesFromUser(String userId);
}
