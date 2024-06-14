package com.moviezone.dai_api.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "moviedb")
public class MovieDB {
    @Id
    private int id;
    private int release_year;
    private double vote_average;
    private String poster_path;

    public MovieDB(int id, int release_year, double vote_average, String poster_path) {
        this.id = id;
        this.release_year = release_year;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
    }

    public MovieDB() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
