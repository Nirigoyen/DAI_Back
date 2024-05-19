package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Favourite;

import java.util.List;

public interface IFavouriteDAO {
    public void addFavourite(int userId, int movieId);
    public void removeFavourite(int userId, int movieId);
    public List<Favourite> getFavouritesByUser(int userId);
}
