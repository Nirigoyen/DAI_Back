package com.moviezone.dai_api.service;

public interface IFavouriteService {
    public void addFavourite(int userId, int movieId);
    public void removeFavourite(int userId, int movieId);
    public void getFavouritesByUser(int userId);
}
