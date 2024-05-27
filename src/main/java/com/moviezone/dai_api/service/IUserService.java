package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.model.entity.User;

public interface IUserService {
    public UserDTO createUser(UserDTO userDTO);
    public UserDTO modifyUser(long userId, User user);
    public void deleteById(long userId);
    public UserDTO findUserById(long userId);
}
