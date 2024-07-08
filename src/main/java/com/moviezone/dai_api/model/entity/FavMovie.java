package com.moviezone.dai_api.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Favourites")
public class FavMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String movieId;
    private String title;
    private String posterPath;
    private String overview;
    private double averageScore;
    private double userScore;

    @ManyToOne
    private User user;



    public FavMovie(int id, String title, String posterPath, String overview, double averageScore, double userScore) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.averageScore = averageScore;
        this.userScore = userScore;
    }

    public FavMovie() {
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
