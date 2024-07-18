package com.moviezone.dai_api.model.dto;

import java.util.List;


//! NO IMPLEMENTADO, SIRVE PARA EL DETALLE DE PELICULAS
public class MovieDTO {
    private int movieId;
    private String movieTitle;
    private String movieDirector;
    private String movieReleaseDate;
    private String movieCertification;
    private List<GenreDTO> movieGenres;
    private String movieOverview;
    private int movieRuntime;
    private float movieVoteAverage;
    private int movieUserRating;
    private String movieTrailerYTKey;
    private List<CastDTO> movieCast;
    private List<MovieImageDTO> movieImages;
    private boolean isFavorite;
    private int voteCount;
    private String moviePosterPathDetails;

    public MovieDTO() {
    }


    public MovieDTO(int movieId, String movieTitle, String movieReleaseDate, String movieCertification,
                    List<GenreDTO> movieGenres, String movieOverview, int movieRuntime, float movieVoteAverage,
                    int movieUserRating, String movieTrailerYTKey, List<CastDTO> movieCast, List<MovieImageDTO> movieImages) {

        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieReleaseDate = movieReleaseDate;
        this.movieCertification = movieCertification;
        this.movieGenres = movieGenres;
        this.movieOverview = movieOverview;
        this.movieRuntime = movieRuntime;
        this.movieVoteAverage = movieVoteAverage;
        this.movieUserRating = movieUserRating;
        this.movieTrailerYTKey = movieTrailerYTKey;
        this.movieCast = movieCast;
        this.movieImages = movieImages;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
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

    public List<GenreDTO> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(List<GenreDTO> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public List<CastDTO> getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(List<CastDTO> movieCast) {
        this.movieCast = movieCast;
    }

    public List<MovieImageDTO> getMovieImages() {
        return movieImages;
    }

    public void setMovieImages(List<MovieImageDTO> movieImages) {
        this.movieImages = movieImages;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getMoviePosterPathDetails() {
        return moviePosterPathDetails;
    }

    public void setMoviePosterPathDetails(String moviePosterPathDetails) {
        this.moviePosterPathDetails = moviePosterPathDetails;
    }
}
