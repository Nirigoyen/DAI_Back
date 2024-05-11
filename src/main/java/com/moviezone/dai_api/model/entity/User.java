package com.moviezone.dai_api.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastName;
    private String email;
    private Date birthDate;

    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-favoriteMovies")
    private List<Movie> favoriteMovies = new ArrayList<Movie>();


    public User(String name, String lastName, String email, Date birthDate) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
    }

    public User() {
        super();
    }


}
