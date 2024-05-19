package com.moviezone.dai_api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            JsonObject TMDBmovie = allMovies.get(i).getAsJsonObject();

            if (!TMDBmovie.get("media_type").getAsString().equals("movie")) continue; // If the media type is different from movie, skip the iteration (don't add it to the list)

            MovieComponentDTO newMovie = new MovieComponentDTO();

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

    @Override
    public List<MovieComponentDTO> search(String search, String page, String orderBy, String ordering) {

        List<MovieComponentDTO> result = new ArrayList<>();

        JsonArray allMovies = movieDAO.search(search, page);

        List<JsonObject> filteredMovies = new ArrayList<>();

        if (allMovies == null) return null; // if there was an error, return null.


        //? FILTRADO

        for (JsonElement movie : allMovies) { //* FILTRADO DE PELICULAS CON MENOS DE 500 VOTOS
            JsonObject TMDBmovie = movie.getAsJsonObject();
            try {
                if (TMDBmovie.get("media_type").getAsString().equals("movie")) continue; // If the media type is person, skip the iteration (don't add it to the list)
            } catch (Exception ignored) {}

            if (TMDBmovie.get("vote_count").getAsInt() < 500) continue;; // If the media type is different from movie, skip the iteration (don't add it to the list

            filteredMovies.add(TMDBmovie);
        }


        //? ORDENAMIENTO

        if (orderBy == null && ordering == null) { //* POR DEFECTO ORDENAMOS LOS RESULTADOS POR PUNTAJE DE MANERA DESCENDENTE
            filteredMovies.sort(Comparator.comparingDouble(jsonObject -> jsonObject.get("vote_average").getAsDouble()));
            Collections.reverse(filteredMovies);
        }
        else if (orderBy != null && ordering != null) {
            if (orderBy.equals("vote_average")) { //* ORDENAMIENTO POR PUNTAJE
                if (ordering.equals("asc")) {
                    filteredMovies.sort(Comparator.comparingDouble(jsonObject -> jsonObject.get("vote_average").getAsDouble()));
                } else {
                    filteredMovies.sort(Comparator.comparingDouble(jsonObject -> jsonObject.get("vote_average").getAsDouble()));
                    Collections.reverse(filteredMovies);
                }
            }
            else if (orderBy.equals("release_date")) { //* ORDENAMIENTO POR FECHA DE LANZAMIENTO
                if (ordering.equals("asc")) {
                    Collections.sort(filteredMovies, (o1, o2) -> {

                        LocalDate releaseDate1 = LocalDate.parse(o1.get("release_date").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate releaseDate2 = LocalDate.parse(o1.get("release_date").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        return releaseDate1.compareTo(releaseDate2);
                    });
                } else {
                    Collections.sort(filteredMovies, (o1, o2) -> {

                        LocalDate releaseDate1 = LocalDate.parse(o1.get("release_date").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        LocalDate releaseDate2 = LocalDate.parse(o1.get("release_date").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        return releaseDate2.compareTo(releaseDate1);
                    });
                };
            }
            else { //* ORDENAMIENTO POR VOTOS Y FECHA DE LANZAMIENTO
                // TODO: IMPLEMENTAR ORDENAMIENTO CRUZADO
            }
        }


        //? CREADO DE DTOS Y CARGADO A LA LISTA FINAL

        for (JsonObject movie: filteredMovies){
            MovieComponentDTO newMovie = new MovieComponentDTO();

            try { // If a posterPath doesn't exist, the movie is not added to the list

                String newMovieImagePosterPath = ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER, movie.get("poster_path").getAsString()); // get "poster_path" field from JSON

                newMovie.setMovieId(movie.get("id").getAsInt());
                newMovie.setMoviePosterPath(newMovieImagePosterPath);

                result.add(newMovie);

            } catch (Exception ignored) {}
        }
        return result;
    }


}
