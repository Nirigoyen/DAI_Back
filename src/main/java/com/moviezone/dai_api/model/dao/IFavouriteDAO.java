package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.FavMovie;

import java.util.List;

public interface IFavouriteDAO {
    public void addFavourite(FavMovie fmovie);
    public void removeFavourite(String userId, String movieId);
    public List<FavMovie> getFavouritesByUser(String userId);
}
