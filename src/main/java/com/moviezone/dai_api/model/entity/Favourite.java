package com.moviezone.dai_api.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "favourites")
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favId;


    @ManyToOne
    @JsonManagedReference(value = "user-favourite")
    private User user;


    @ManyToOne
    @JsonManagedReference(value = "movie-favourite")
    private Movie movie;


    public Favourite() {
        super();
    }
    public Favourite(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
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
