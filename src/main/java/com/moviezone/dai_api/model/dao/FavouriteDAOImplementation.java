package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.FavMovie;
import com.moviezone.dai_api.model.entity.Favourite;

import java.util.List;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FavouriteDAOImplementation implements IFavouriteDAO{

    private EntityManager entityManager;


    @Override
    @Transactional
    public void addFavourite(FavMovie fmovie) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(fmovie);
    }

    @Override
    //Transactional
    public void removeFavourite(String userId, int movieId) {
        //TODO
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavMovie> getFavouritesByUser(String userId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<FavMovie> getQuery = currentSession.createQuery("from FavMovie where User.userId=:userId", FavMovie.class);
        return getQuery.getResultList();
    }
}
