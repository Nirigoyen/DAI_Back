package com.moviezone.dai_api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moviezone.dai_api.model.dao.IMovieDAO;
import com.moviezone.dai_api.model.dao.MovieDAOImplementation;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.utils.IMAGE_TYPE;
import com.moviezone.dai_api.utils.ImageLinks;
import kotlin.collections.ArrayDeque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImplementation implements IMovieService {

    @Autowired
    private IMovieDAO movieDAO;

    public void getMovieById(int id) {
        System.out.println("Getting movie by id: " + id);
    }

    public List<MovieComponentDTO> discover(String page, String genres) {

        if (genres == null) genres = ""; // Genre formatting : <GenreID>%2C<GenreID>%2C>GenreID> - Example: 28%2C18 - GenreName to GenreId should be handled for frontend
        List<MovieComponentDTO> result = new ArrayList<>();

        JsonArray allMovies = movieDAO.discover(page, genres);
        if ( allMovies == null) return null; // if there was an error, return null.


        for (int i = 0; i < allMovies.size(); i++) {
            MovieComponentDTO newMovie = new MovieComponentDTO();
            JsonObject TMDBmovie = allMovies.get(i).getAsJsonObject();

            int newMovieId = TMDBmovie.get("id").getAsInt(); // Get "ID" field from JSON
            try { // If a posterPath doesn't exist, the movie is not added to the list
                String newMovieImagePosterPath = ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER, TMDBmovie.get("poster_path").getAsString()); // get "poster_path" field from JSON

                newMovie.setMovieId(newMovieId);
                newMovie.setMoviePosterPath(newMovieImagePosterPath);
                result.add(newMovie);

            } catch (Exception ignored) {}
        }
        return result;
    }
}
