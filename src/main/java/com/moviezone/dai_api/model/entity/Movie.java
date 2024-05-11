package com.moviezone.dai_api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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

    private Cast director;

    private List<Cast> actors = new ArrayList<Cast>();

    private List<String> images = new ArrayList<String>();


    @ManyToOne
    @JsonManagedReference(value = "movie-ratedMovies")
    private List<Rating> ratings = new ArrayList<Rating>();

}
