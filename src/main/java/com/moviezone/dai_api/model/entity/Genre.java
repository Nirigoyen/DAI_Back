package com.moviezone.dai_api.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;
    private GenreName name;

    public Genre() {
        super();
    }
    public Genre(int id, GenreName name) {
        this.genreId = id;
        this.name = name;
    }
    public int getId() {
        return genreId;
    }
    public void setId(int id) {
        this.genreId = id;
    }
    public GenreName getName() {
        return name;
    }
    public void setName(GenreName name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "genre-movies")
    private List<Movie> movie = new ArrayList<Movie>();

}
