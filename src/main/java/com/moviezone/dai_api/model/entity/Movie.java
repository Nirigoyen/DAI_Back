package com.moviezone.dai_api.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    private int id;


    //RELACIONES
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-favoriteMovies")
    private List<Favourite> favoriteMovies = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-ratedMovies")
    private List<Rating> ratedMovies = new ArrayList<>();

    public Movie() {
        super();
    }
    public Movie(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
