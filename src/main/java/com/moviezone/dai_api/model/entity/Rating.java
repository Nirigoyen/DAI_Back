package com.moviezone.dai_api.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ratingId;

    private int rating;
    
    @ManyToOne
    @JsonManagedReference(value = "user-rating")
    private User user;


    @ManyToOne
    @JsonManagedReference(value = "movie-rating")
    private Movie movie;


    public Rating() {
        super();
    }
    public Rating(int rating, User user, Movie movie) {
        this.rating = rating;
        this.user = user;
        this.movie = movie;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
