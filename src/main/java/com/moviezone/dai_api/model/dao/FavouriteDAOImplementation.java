package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.FavMovie;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FavouriteDAOImplementation implements IFavouriteDAO{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public void addFavourite(FavMovie fmovie) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(fmovie);
    }

    @Override
    @Transactional
    public void removeFavourite(String userId, String movieId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query deleteQuery = currentSession.createQuery("DELETE from FavMovie where user.userId=:userId and movieId=:movieId");
        deleteQuery.setParameter("userId", userId);
        deleteQuery.setParameter("movieId", movieId);
        deleteQuery.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavMovie> getFavouritesByUser(String userId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<FavMovie> getQuery = currentSession.createQuery("from FavMovie where user.userId=:userId", FavMovie.class);
        getQuery.setParameter("userId", userId);
        return getQuery.getResultList();
    }
}
