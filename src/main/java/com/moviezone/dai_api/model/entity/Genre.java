package com.moviezone.dai_api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Genre {
    @Id
    private int id;
    @Enumerated(EnumType.STRING)
    private GenreName genre;

    public Genre(int id, GenreName genre) {
        this.id = id;
        this.genre = genre;
    }

    public Genre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GenreName getGenre() {
        return genre;
    }

    public void setGenre(GenreName genre) {
        this.genre = genre;
    }
}
