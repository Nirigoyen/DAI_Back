package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.FavMovie;
import com.moviezone.dai_api.model.entity.Favourite;

import java.util.List;

public interface IFavouriteDAO {
    public void addFavourite(FavMovie fmovie);
    public void removeFavourite(int userId, int movieId);
    public List<FavMovie> getFavouritesByUser(int userId);
}
