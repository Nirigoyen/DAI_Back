package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.UserDAOImplementation;
import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements IUserService{ //TODO: CAMBIAR ENTITY POR DTOs.

    @Autowired
    private UserDAOImplementation userDAO;


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDAO.createUser(userDTO.toEntity());
        return userDTO;
    }

    @Override
    public UserDTO modifyUser(String userId, User user) {
        User currentUser = userDAO.findUserById(userId);
        currentUser.setName(user.getName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        currentUser.setProfilePicture(user.getProfilePicture());

        userDAO.createUser(currentUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(currentUser.getId());
        userDTO.setName(currentUser.getName());
        userDTO.setLastName(currentUser.getLastName());
        userDTO.setEmail(currentUser.getEmail());
        userDTO.setProfilePictureURL(currentUser.getProfilePicture());

        return userDTO;

    }

    @Override
    public void deleteById(String userId) {
        userDAO.deleteUser(userId);
    }

    @Override
    public UserDTO findUserById(String userId) {
        User user = userDAO.findUserById(userId);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setProfilePictureURL(user.getProfilePicture());

            return userDTO;
        }
        return null;
    }
}
