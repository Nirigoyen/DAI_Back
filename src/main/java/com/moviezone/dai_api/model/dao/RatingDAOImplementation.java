package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.dto.RatingDTO;
import com.moviezone.dai_api.model.entity.Rating;
import com.moviezone.dai_api.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RatingDAOImplementation implements IRatingDAO {


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public int rateMovie (int movieId, User user, int score) {
        Session currentSession = entityManager.unwrap(Session.class);

        Rating rating = new Rating();
        rating.setRating(score);
        rating.setUser(user);
        rating.setMovie(movieId);

        currentSession.persist(rating);

        return rating.getRating();
    }

    @Override
    public Rating getRatingByUserAndMovie (int movieId, String userId) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<Rating> theQuery = currentSession.createQuery("FROM Rating WHERE movie=:movieId AND user.userId=:userId", Rating.class);
        theQuery.setParameter("movieId", movieId);
        theQuery.setParameter("userId", userId);
        Rating rating = theQuery.uniqueResult();


        return rating;
    }

    @Override
    @Transactional
    public int modifyRating (int movieId, User user, int rating) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<Rating> theQuery = currentSession.createQuery("UPDATE Rating SET rating=:rating WHERE movie=:movieId AND user=:userId", Rating.class);
        theQuery.setParameter("rating", rating);
        theQuery.setParameter("movieId", movieId);
        theQuery.setParameter("userId", user);
        theQuery.executeUpdate();

        return rating;
    }
}
