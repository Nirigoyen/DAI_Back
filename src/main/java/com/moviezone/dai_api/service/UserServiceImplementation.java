package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.UserDAOImplementation;
import com.moviezone.dai_api.model.dto.UserDTO;
import com.moviezone.dai_api.model.dto.UserEditableDTO;
import com.moviezone.dai_api.model.dto.FavDTO;
import com.moviezone.dai_api.model.entity.FavMovie;
import com.moviezone.dai_api.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public UserDTO modifyUser(String userId, UserEditableDTO userDTO, String base64Img) {

        //? OBTENEMOS EL USUARIO ACTUAL Y ACTUALIZAMOS SUS DATOS
        User currentUser = userDAO.findUserById(userId);
        currentUser.setUsername(userDTO.getUsername());
        currentUser.setName(userDTO.getName());
        currentUser.setLastName(userDTO.getLastName());
        currentUser.setEmail(userDTO.getEmail());

        System.out.println(currentUser);
        //? ACTUALIZAMOS LOS DATOS DEL USUARIO EN LA BASE DE DATOS (Y OBS SI FUERA NECESARIO)
        userDAO.updateUser(currentUser, base64Img);


        //? RETORNAMOS EL USUARIO ACTUALIZADO
        return new UserDTO(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),
                currentUser.getLastName(), currentUser.getEmail(), currentUser.getProfilePicture());
    }


    @Override
    public void deleteById(String userId) {
        userDAO.deleteUser(userId);
    }

    @Override
    public UserDTO getUser(String userId) {
        User user = userDAO.findUserById(userId);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setUsername(user.getUsername());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setProfilePictureURL(user.getProfilePicture());

            return userDTO;
        }
        return null;
    }

    @Override
    public List<FavDTO> getFavs(String userId){

//        List<FavMovie> favMovies = userDAO.getUserFavs(userId);

        return null;
    }
}
