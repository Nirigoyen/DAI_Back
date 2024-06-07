package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.model.dto.UserEditableDTO;
import com.moviezone.dai_api.model.entity.User;

public interface IUserService {
    public UserDTO createUser(UserDTO userDTO);
    public UserDTO modifyUser(String userId, UserEditableDTO userDTO, String base64Img);
    public void deleteById(String userId);
    public UserDTO getUser(String userId);
}
