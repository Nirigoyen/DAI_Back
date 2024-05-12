package com.moviezone.dai_api.model.dto;

import com.moviezone.dai_api.model.entity.ProfilePicture;

import java.util.Date;
import java.util.List;

public class UserDTO {
    private int id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String birthdate;
    private ProfilePictureDTO profilePicture;
    private List<MovieDTO> favouriteMovies;
    private List<RatingDTO> ratings;


    public UserDTO(int id, String username, String name, String lastName, String email, Date birthdate, ProfilePictureDTO profilePicture, List<MovieDTO> favouriteMovies, List<RatingDTO> ratings) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate.toString();
        this.profilePicture = profilePicture;
        this.favouriteMovies = favouriteMovies;
        this.ratings = ratings;
    }


    public UserDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


    public ProfilePictureDTO getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePictureDTO profilePicture) {
        this.profilePicture = profilePicture;
    }
}
