package com.moviezone.dai_api.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int rating;
    
    @ManyToOne
    @JsonManagedReference(value = "user-rating")
    private User user;


    @ManyToOne
    @JsonManagedReference(value = "movie-rating")
    private Movie movie;

    

    
}
