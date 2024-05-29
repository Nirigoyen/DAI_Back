package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface IUserDAO {
    public User findUserById(String userId);
    public void createUser(User user);
    public void deleteUser(String userId);
    public void updateUser(User user, String base64Img);
}
