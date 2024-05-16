package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.ProfilePicture;

public interface IProfilePictureDAO {
    public ProfilePicture findProfilePictureByUser(int id);
}
