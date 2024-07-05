package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.FavMovie;
import com.moviezone.dai_api.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserDAO {
    public User findUserById(String userId);
    public void createUser(User user);
    public void deleteUser(String userId);
    public void updateUser(User user, String base64Img);
//    public List<FavMovie> getUserFavs(String userId);
}
