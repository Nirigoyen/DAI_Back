package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.model.entity.User;

public interface IUserService {
    public UserDTO createUser(User user);
    public UserDTO modifyUser(int userId, User user);
    public void deleteById(int userId);
    public UserDTO findUserById(int userId);
}
