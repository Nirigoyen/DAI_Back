package com.moviezone.dai_api.model.dto;

import java.util.List;


//! NO IMPLEMENTADO, SIRVE PARA EL DETALLE DE PELICULAS
public class MovieDTO {
    private int movieId;
    private int movieRuntime;
    private int movieUserRating;
    private float movieVoteAverage;
    private String movieTitle;
    private String movieReleaseDate;
    private String movieCertification;
    private String movieOverview;
    private String movieTrailerYTKey;
    private List<String> movieGenres;
    private List<MovieImageDTO> movieImages;

    public MovieDTO() {
    }

    public MovieDTO(int movieId, int movieRuntime, int movieUserRating, float movieVoteAverage, String movieTitle, String movieReleaseDate, String movieCertification, String movieOverview, String movieTrailerYTKey) {
        this.movieId = movieId;
        this.movieRuntime = movieRuntime;
        this.movieUserRating = movieUserRating;
        this.movieVoteAverage = movieVoteAverage;
        this.movieTitle = movieTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.movieCertification = movieCertification;
        this.movieOverview = movieOverview;
        this.movieTrailerYTKey = movieTrailerYTKey;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    public void setMovieRuntime(int movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public int getMovieUserRating() {
        return movieUserRating;
    }

    public void setMovieUserRating(int movieUserRating) {
        this.movieUserRating = movieUserRating;
    }

    public float getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public void setMovieVoteAverage(float movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieCertification() {
        return movieCertification;
    }

    public void setMovieCertification(String movieCertification) {
        this.movieCertification = movieCertification;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieTrailerYTKey() {
        return movieTrailerYTKey;
    }

    public void setMovieTrailerYTKey(String movieTrailerYTKey) {
        this.movieTrailerYTKey = movieTrailerYTKey;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
