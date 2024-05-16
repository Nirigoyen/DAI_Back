package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.entity.User;

public interface IUserService {
    public void createUser(User user);
    public void modifyUser(int userId, User user);
    public void deleteById(int userId);
    public User findUserById(int userId);
}
