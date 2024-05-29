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
    public UserDTO modifyUser(String userId, UserDTO userDTO, String base64Img) {
        User currentUser = userDAO.findUserById(userId);
        currentUser.setUsername(userDTO.getUsername());
        currentUser.setName(userDTO.getName());
        currentUser.setLastName(userDTO.getLastName());
        currentUser.setEmail(userDTO.getEmail());


        //? SI NO ACTUALIZA LA IMAGEN, NO HACEMOS REQUEST AL OBS.
        if (base64Img != null) {
            userDAO.updateUserWithImg(currentUser, base64Img);
        } else {
            userDAO.updateUser(currentUser);
        }

        return new UserDTO(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),
                currentUser.getLastName(), currentUser.getEmail());
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
