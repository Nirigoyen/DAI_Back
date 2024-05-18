package com.moviezone.dai_api.model.dao;

public interface IFavouriteDAO {
    public void addFavourite(int userId, int movieId);
    public void removeFavourite(int userId, int movieId);
    public void getFavouritesByUser(int userId);
}
