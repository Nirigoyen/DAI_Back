package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.User;

public interface IUserDAO {
    public User findUser(int id);
}
