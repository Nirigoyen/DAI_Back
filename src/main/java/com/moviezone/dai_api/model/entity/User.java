package com.moviezone.dai_api.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.moviezone.dai_api.model.dto.UserDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class User {
    @Id
    private String userId;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String profilePictureLink;


    //? Relaciones
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "user-favoriteMovies")
//    private List<String> favoriteMovies = new ArrayList<>();


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "user-ratedMovies")
//    private List<Rating> ratedMovies = new ArrayList<>();


    public User() {
        super();
    }

    public User(String userId, String username, String name, String lastName, String email, String profilePictureLink) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.profilePictureLink = profilePictureLink;
    }

    public String getId() {
        return userId;
    }
    public void setId(String id) {
        this.userId = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getProfilePicture() {
        return profilePictureLink;
    }
    public void setProfilePicture(String profilePictureLink) {
        this.profilePictureLink = profilePictureLink;
    }


//        public List<String> getFavoriteMovies() {
//        return favoriteMovies;
//    }
//    public void setFavoriteMovies(List<String> favoriteMovies) {
//        this.favoriteMovies = favoriteMovies;
//    }
//    public List<Rating> getRatedMovies() {
//        return ratedMovies;
//    }
//    public void setRatedMovies(List<Rating> ratedMovies) {
//        this.ratedMovies = ratedMovies;
//    }

    public UserDTO toDTO(){
        return new UserDTO(this.userId, this.username, this.name, this.lastName, this.email,
                this.profilePictureLink);
        //FIJARSE EN QUE MOMENTO CONVERTIR LAS PELICULAS EN DTOS.
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", profilePictureLink='" + profilePictureLink + '\'' +
                '}';
    }
}
