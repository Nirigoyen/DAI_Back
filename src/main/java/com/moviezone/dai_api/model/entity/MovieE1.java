package com.moviezone.dai_api.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MovieE1 {
    @Id
    private int id;

    public MovieE1() {
        super();
    }
    public MovieE1(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
