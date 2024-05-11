package com.moviezone.dai_api.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int rating;
    
    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-rating")
    private User user;


    @OneToMany(mappedBy = "movie")
    @JsonManagedReference(value = "movie-rating")
    private Movie movie;

    

    
}
