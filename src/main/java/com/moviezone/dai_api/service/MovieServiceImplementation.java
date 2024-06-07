package com.moviezone.dai_api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moviezone.dai_api.model.dao.IMovieDAO;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.utils.IMAGE_TYPE;
import com.moviezone.dai_api.utils.ImageLinks;
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

    public void getMovieDetails(int movieId) { //! NO IMPLEMENTADO AUN
        System.out.println("Getting movie by id: " + movieId);
    }

    public List<MovieComponentDTO> discover(String page, String genres) {

        //? SI NO HAY GENEROS, LOS PASAMOS COMO VACIO
        if (genres == null) genres = ""; // Genre formatting : <GenreID>%2C<GenreID>%2C>GenreID> - Example: 28%2C18 - GenreName to GenreId should be handled for frontend

        //? OBTENEMOS LOS RESULTADOS DE LA LANDING
        List<MovieComponentDTO> result = new ArrayList<>();
        JsonArray allMovies = movieDAO.discover(page, genres);

        //! SI HUBO UN ERROR RETORNAMOS NULL
        if ( allMovies == null) return null; // if there was an error, return null.

        //? CREADO DE DTOs Y CARGADO A LA LISTA FINAL DE LOS RESULTADOS
        for (int i = 0; i < allMovies.size(); i++) {
            JsonObject TMDBmovie = allMovies.get(i).getAsJsonObject();

            MovieComponentDTO newMovie = new MovieComponentDTO();

            int newMovieId = TMDBmovie.get("id").getAsInt(); // Get "ID" field from JSON

            try { //* SI LA PELICULA NO TIENE POSTER, NO SE CARGA EN LA LISTA
                String newMovieImagePosterPath = ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER, TMDBmovie.get("poster_path").getAsString()); // get "poster_path" field from JSON

                newMovie.setMovieId(newMovieId);
                newMovie.setMoviePosterPath(newMovieImagePosterPath);
                result.add(newMovie);

            } catch (Exception ignored) {}
        }
        return result;
    }

    @Override
    public List<MovieComponentDTO> search(String page, String search, String orderByScore, String orderingScore, String orderByDate, String orderingDate) {

        //? OBTENEMOS LOS RESULTADOS DE LA BUSQUEDA Y LOS AÑADIMOS A UNA LISTA
        List<MovieComponentDTO> result = new ArrayList<>();

        JsonArray allMovies = movieDAO.search(search);

        List<JsonObject> filteredMovies = new ArrayList<>();

        //! SI HUBO UN ERROR RETORNAMOS NULL
        if (allMovies == null) return null;
        

        //? FILTRADO

        for (JsonElement movie : allMovies) {
            JsonObject TMDBmovie = movie.getAsJsonObject();
            try { //* SI EL MEDIA TYPE ES DISTINTO A PELICULA NO SUMAMOS A RESULTADOS (ES UNA SERIE O PERSONA)
                if (!TMDBmovie.get("media_type").getAsString().equals("movie")) continue; // If the media type is different from "movie", skip the iteration (don't add it to the list)
            } catch (Exception ignored) {}

            //* SI LA PELICULA TIENE MENOS DE 200 VOTOS NO LA SUMAMOS A LOS RESULTADOS
            if (TMDBmovie.get("vote_count") == null) continue;
            else if (TMDBmovie.get("vote_count").getAsInt() < 200) continue;

            filteredMovies.add(TMDBmovie);
        }


        //? ORDENAMIENTO

        //* NOS ASEGURAMOS DE QUE LAS VARIABLES DE ORDENAMIENTO NO SEAN NULL
        if (orderByScore == null) orderByScore = "False";
        if (orderByDate == null) orderByDate = "False";
        if (orderingScore == null) orderingScore = "desc";
        if (orderingDate == null) orderingDate = "desc";

        if (orderByScore.equals("False") && orderByDate.equals("False")) { //* POR DEFECTO ORDENAMOS LOS RESULTADOS POR PUNTAJE DE MANERA DESCENDENTE
            filteredMovies.sort(Comparator.comparingDouble(jsonObject -> jsonObject.get("vote_average").getAsDouble()));
            Collections.reverse(filteredMovies);
        }
        else if (orderByScore.equals("True") && orderByDate.equals("False")) { //* ORDENAMIENTO POR PUNTAJE
            OrderByScore(orderingScore, filteredMovies);
        }
        else if (orderByScore.equals("False") && orderByDate.equals("True")) { //* ORDENAMIENTO POR FECHA DE LANZAMIENTO
            OrderByDate(orderingDate, filteredMovies);
        }
        else { //* ORDENAMIENTO POR PUNTAJE Y FECHA DE LANZAMIENTO (PRIMERO POR PUNTAJE Y DESPUES POR FECHA)
            OrderByScore(orderingScore, filteredMovies);
            OrderByDate(orderingDate, filteredMovies);
        }



        //? CREADO DE DTOs Y CARGADO A LA LISTA FINAL DE LOS RESULTADOS

        for (JsonObject movie: filteredMovies){
            MovieComponentDTO newMovie = new MovieComponentDTO();

            try { //* SI LA PELICULA NO TIENE POSTER, NO SE CARGA EN LA LISTA

                String newMovieImagePosterPath = ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER, movie.get("poster_path").getAsString()); // get "poster_path" field from JSON

                newMovie.setMovieId(movie.get("id").getAsInt());
                newMovie.setMoviePosterPath(newMovieImagePosterPath);

                result.add(newMovie);

            } catch (Exception ignored) {}
        }

        //? PAGINADO

        List<MovieComponentDTO> moviePage = getPartition(result, Integer.parseInt(page) - 1);

        return moviePage;
    }

    private static List<MovieComponentDTO> getPartition(List<MovieComponentDTO> list, int partitionIndex) {
        int startIndex = partitionIndex * 40;
        int endIndex = Math.min(startIndex + 40, list.size());
        return list.subList(startIndex, endIndex);
    }

    private static void OrderByDate(String orderingDate, List<JsonObject> filteredMovies) {
        if (orderingDate.equals("asc")) { //* ORDENAMOS POR FECHA DE MANERA ASCENDENTE
            Collections.sort(filteredMovies, (o1, o2) -> {

                LocalDate releaseDate1 = LocalDate.parse(o1.get("release_date").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate releaseDate2 = LocalDate.parse(o2.get("release_date").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                return releaseDate1.compareTo(releaseDate2);
            });
        } else { //* ORDENAMOS POR FECHA DE MANERA DESCENDENTE
            Collections.sort(filteredMovies, (o1, o2) -> {

                LocalDate releaseDate1 = LocalDate.parse(o1.get("release_date").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate releaseDate2 = LocalDate.parse(o2.get("release_date").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                return releaseDate2.compareTo(releaseDate1);
            });
        };
    }

    private static void OrderByScore(String orderingScore, List<JsonObject> filteredMovies) {
        if (orderingScore.equals("asc")) { //* ORDENAMOS POR PUNTAJE DE MANERA ASCENDENTE
            filteredMovies.sort(Comparator.comparingDouble(jsonObject -> jsonObject.get("vote_average").getAsDouble()));
        } else { //* ORDENAMOS POR PUNTAJE DE MANERA DESCENDENTE
            filteredMovies.sort(Comparator.comparingDouble(jsonObject -> jsonObject.get("vote_average").getAsDouble()));
            Collections.reverse(filteredMovies);
        }
    }


}
