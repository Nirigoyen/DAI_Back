package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GenreDAOImplementation implements IGenreDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Genre getGenreById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Genre> theQuery = currentSession.createQuery("FROM Genre WHERE id=:id", Genre.class);
        theQuery.setParameter("id", id);
        Genre genre = theQuery.uniqueResult();
        return genre;
    }

    @Override
    @Transactional
    public void save(Genre genre) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(genre);
    }
}
