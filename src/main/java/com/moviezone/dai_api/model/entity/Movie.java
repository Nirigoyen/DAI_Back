package com.moviezone.dai_api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String director;
    private int year;
    private List<String> genres = new ArrayList<String>();
    private int duration;
    private String synopsis;

    private String poster;

    private String trailer;

    private String cast;
    private String awards;
    private String production;
    private String website;
    private String imdbRating;
    private String rottenTomatoesRating;
    private String metacriticRating;
    private String boxOffice;
    private String releaseDate;
    private String productionCompany;
    private String rated;
    private String type;
    private String response;
    private String error;




}
