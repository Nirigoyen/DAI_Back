package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Favourite;

import java.util.List;
//import jakarta.persistence.EntityManager;

public class FavouriteDAOImplementation implements IFavouriteDAO{

    //private EntityManager entityManager;


    @Override
    //@Transactional
    public void addFavourite(int userId, int movieId) {
        //Session currentSession = entityManager.unwrap(Session.class);
        Favourite favourite = new Favourite();
    }

    @Override
    //Transactional
    public void removeFavourite(int userId, int movieId) {

    }

    @Override
    //@Transactional
    public List<Favourite> getFavouritesByUser(int userId) {
        return List.of();
    }
}
