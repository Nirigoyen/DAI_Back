package com.moviezone.dai_api.model.dto;

import java.util.List;


//! NO IMPLEMENTADO
public class MovieFavouriteComponentDTO {
    private int movieId;
    private float movieVoteAverage;
    private int movieUserVote;
    private String movieTitle;
    private String movieOverview;
    private String moviePosterPath;
    private List<String> movieGenres;

    public MovieFavouriteComponentDTO() {
    }

    public MovieFavouriteComponentDTO(List<String> movieGenres, String moviePosterPath, String movieOverview,
                                      String movieTitle, int movieUserVote, float movieVoteAverage, int movieId) {
        this.movieGenres = movieGenres;
        this.moviePosterPath = moviePosterPath;
        this.movieOverview = movieOverview;
        this.movieTitle = movieTitle;
        this.movieUserVote = movieUserVote;
        this.movieVoteAverage = movieVoteAverage;
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public float getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public void setMovieVoteAverage(float movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }

    public int getMovieUserVote() {
        return movieUserVote;
    }

    public void setMovieUserVote(int movieUserVote) {
        this.movieUserVote = movieUserVote;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public List<String> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(List<String> movieGenres) {
        this.movieGenres = movieGenres;
    }
}
