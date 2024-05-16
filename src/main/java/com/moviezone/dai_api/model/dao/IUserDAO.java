package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.User;

public interface IUserDAO {
    public User findUserById(int userId);
    public void createUser(User user);
    public void deleteUser(int userId);
}
