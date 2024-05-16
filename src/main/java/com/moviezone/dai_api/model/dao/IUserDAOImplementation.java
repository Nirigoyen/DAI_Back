package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.User;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

@Repository
public class IUserDAOImplementation implements IUserDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public User findUserById(int userId) {
        Session currentSession = entityManager.unwrap(Session.class);

        User user = currentSession.get(User.class, userId);

        return user;
    }

    @Override
    @Transactional
    public void createUser(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(user);

    }

    @Override
    public void deleteUser(int userId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("delete from User where id=:userId");
        theQuery.setParameter("userId", userId);
        theQuery.executeUpdate();
    }

}