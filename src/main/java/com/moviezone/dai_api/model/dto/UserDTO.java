package com.moviezone.dai_api.model.dto;

import com.moviezone.dai_api.model.entity.User;

import java.util.Date;
import java.util.List;

public class UserDTO {
    private String id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String profilePictureURL;


    public UserDTO(String id, String username, String name, String lastName, String email, String profilePictureURL) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.profilePictureURL = profilePictureURL;
    }


    public UserDTO() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public User toEntity(){
        return (new User(this.id, this.username, this.name, this.lastName, this.email, this.profilePictureURL));
    }
}
