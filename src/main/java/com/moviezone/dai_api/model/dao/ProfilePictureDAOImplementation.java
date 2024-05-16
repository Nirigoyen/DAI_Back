package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.ProfilePicture;
import org.springframework.stereotype.Repository;

@Repository
public class ProfilePictureDAOImplementation implements IProfilePictureDAO {

    public ProfilePicture findProfilePictureByUser(int id){
        //BUSCAR EN LA DB LA FOTO DE PERFIL DEL USUARIO
        return null;
    }
}
