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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private Date birthDate;
    private String profilePictureLink;



    //Relaciones
    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-favoriteMovies")
    private List<Movie> favoriteMovies = new ArrayList<Movie>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-ratedMovies")
    private List<Rating> ratedMovies = new ArrayList<Rating>();


    public User() {
        super();
    }

    public User(String username, String name, String lastName, String email, Date birthDate, String profilePicture) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.profilePictureLink = profilePictureLink;
    }

    public int getId() {
        return userId;
    }
    public void setId(int id) {
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
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public String getProfilePicture() {
        return profilePictureLink;
    }
    public void setProfilePicture(String profilePictureLink) {
        this.profilePictureLink = profilePictureLink;
    }
    public List<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }
    public void setFavoriteMovies(List<Movie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }
    public List<Rating> getRatedMovies() {
        return ratedMovies;
    }
    public void setRatedMovies(List<Rating> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

//    public UserDTO toDTO(){
//        return new UserDTO(this.userId, this.username, this.name, this.lastName, this.email, this.birthDate,
//                this.profilePicture.toDTO(), this.favoriteMovies, this.ratedMovies);
//        //FIJARSE EN QUE MOMENTO CONVERTIR LAS PELICULAS EN DTOS.
//    }



}
