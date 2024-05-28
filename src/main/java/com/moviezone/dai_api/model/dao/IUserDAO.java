package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.User;

public interface IUserDAO {
    public User findUserById(String userId);
    public void createUser(User user);
    public void deleteUser(String userId);
}
