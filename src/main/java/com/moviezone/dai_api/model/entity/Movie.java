package com.moviezone.dai_api.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    
    private int year;

    private List<String> genres = new ArrayList<String>();

    private int duration;

    private String synopsis;

    private String poster;

    private String trailer;

    private String director;

    private List<String> actors = new ArrayList<String>();

    private List<String> images = new ArrayList<String>();


    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-ratedMovies")
    private List<Rating> ratings = new ArrayList<Rating>();

}
