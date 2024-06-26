package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.RefreshToken;
import com.moviezone.dai_api.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RefreshTokenDAOImpl implements IRefreshTokenDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(RefreshToken refreshToken) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken findByRefreshToken(String token) {
        Session currentSession = entityManager.unwrap(Session.class);

        //RefreshToken refreshToken = currentSession.get(RefreshToken.class, token);

        Query<RefreshToken> theQuery = currentSession.createQuery("FROM RefreshToken WHERE token=:token", RefreshToken.class);
        theQuery.setParameter("token", token);
        RefreshToken refreshToken = theQuery.uniqueResult();
        return refreshToken;
    }

    @Override
    @Transactional
    public void delete(RefreshToken refreshToken) {
        Session currentSession = entityManager.unwrap(Session.class);

        //currentSession.remove(refreshToken);

        Query theQuery = currentSession.createQuery("DELETE FROM RefreshToken WHERE id=:id");
        theQuery.setParameter("id", refreshToken.getId());
        theQuery.executeUpdate();
    }

    @Override
    @Transactional
    public RefreshToken findByUser(String userId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<RefreshToken> theQuery = currentSession.createQuery("FROM RefreshToken WHERE user.userId=:userId", RefreshToken.class);
        theQuery.setParameter("userId", userId);
        RefreshToken refreshToken = theQuery.uniqueResult();

        return refreshToken;

    }

    @Override
    @Transactional
    public void deleteByUser(String userId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("DELETE FROM RefreshToken WHERE user.userId=:userId");
        theQuery.setParameter("userId", userId);
        theQuery.executeUpdate();
    }
    
}
