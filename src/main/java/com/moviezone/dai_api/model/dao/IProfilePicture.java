package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.ProfilePicture;

public interface IProfilePicture {
    public ProfilePicture findProfilePictureByUser(int id);
}
