package com.moviezone.dai_api.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    private String title;

    private int year;

    private int duration;

    private String director;

    private int globalRating;

    private String synopsis;

    private String poster;

    private String trailer;


    @ManyToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-genres")
    private List<Genre> genres = new ArrayList<Genre>();


    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-ratedMovies")
    private List<Rating> ratings = new ArrayList<Rating>();

}
