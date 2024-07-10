package com.moviezone.dai_api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moviezone.dai_api.model.dao.IMovieDAO;
import com.moviezone.dai_api.model.dto.*;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.utils.IMAGE_TYPE;
import com.moviezone.dai_api.utils.ImageLinks;
import com.moviezone.dai_api.utils.CertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moviezone.dai_api.utils.TrailerLinks;

import java.util.*;

@Service
public class MovieServiceImplementation implements IMovieService {

    @Autowired
    private IMovieDAO movieDAO;

    @Autowired
    private RatingServiceImplementation ratingService;

    public MovieDTO getMovieDetails(int movieId, String userId) { //! NO IMPLEMENTADO AUN

        //? OBTENEMOS EL JSON CON LOS DATOS DE LA PELICULA

        JsonObject movie = movieDAO.getMovieDetails(movieId);


        MovieDTO movieDetails = new MovieDTO();

        //? SETEAMOS LOS DATOS DE LA PELICULA
        //* DATOS BASICOS

        movieDetails.setMovieId(Integer.parseInt(movie.get("id").toString())); //* TAL VEZ CAMBIARLO A STRING?
        movieDetails.setMovieTitle(movie.get("original_title").getAsString());
        movieDetails.setMovieReleaseDate(movie.get("release_date").getAsString());
        movieDetails.setMovieOverview(movie.get("overview").getAsString());
        movieDetails.setMovieRuntime(movie.get("runtime").getAsInt());
        movieDetails.setMovieVoteAverage(Float.parseFloat(movie.get("vote_average").toString()));

        //* GENEROS
        List<GenreDTO> genres = new ArrayList<>();

        //* PARSEO DE GENEROS A DTOs
        JsonArray genresArray = movie.get("genres").getAsJsonArray();
        for (JsonElement genre : genresArray) {
            JsonObject genreObject = genre.getAsJsonObject();
            genres.add(new GenreDTO(genreObject.get("id").getAsInt(), genreObject.get("name").getAsString()));
        }
        movieDetails.setMovieGenres(genres);

        //* SE OBTIENE JSON CREDITOS
        JsonObject credits = movieDAO.getCredits(movieId);

        //* CAST
        List<CastDTO> cast = new ArrayList<>();
        //* PARSEO DE ACTORES A DTOs
        JsonArray castArray = credits.get("cast").getAsJsonArray();

        for (JsonElement actor : castArray) {
            JsonObject actorObject = actor.getAsJsonObject();

            //* CAST
            if (actorObject.get("known_for_department").getAsString().equals("Acting") &&
                    actorObject.get("popularity").getAsDouble() >= 10) { //* FILTRAMOS SOLO ACTORES CON +10 DE POPULARIDAD

                CastDTO castDTO = new CastDTO();
                castDTO.setFullName(actorObject.get("name").getAsString());
                castDTO.setCharacter(actorObject.get("character").getAsString());
                try { //* SI EL ACTOR NO TIENE IMAGEN, NO SE CARGA EN LA LISTA
                    castDTO.setProfilePath(ImageLinks.imageTypeToLink(IMAGE_TYPE.PROFILE, actorObject.get("profile_path").getAsString()));
                    cast.add(castDTO);
                } catch (Exception ignored) {}
            }
        }
        movieDetails.setMovieCast(cast);

        //* DIRECTOR
        JsonArray crew = credits.get("crew").getAsJsonArray();
        for (JsonElement crewElement : crew) {
            JsonObject crewJsonObject = crewElement.getAsJsonObject();
            if(crewJsonObject.get("job").getAsString().equals("Director")) movieDetails.setMovieDirector(crewJsonObject.get("name").getAsString());
        }

        //* IMAGENES
        List<MovieImageDTO> images = new ArrayList<>();

        //* PARSEO DE IMAGENES
        JsonArray imagesArray = movieDAO.getImages(movieId);

        for (JsonElement imageJson : imagesArray){
            JsonObject imageObject = imageJson.getAsJsonObject();
            MovieImageDTO imageDTO = new MovieImageDTO();

            imageDTO.setImageHeight(imageObject.get("height").getAsInt());
            imageDTO.setImageWidth(imageObject.get("width").getAsInt());

            try {//* SI LA IMAGEN NO TIENE PATH, NO SE CARGA EN LA LISTA
                imageDTO.setImagePath(ImageLinks.imageTypeToLink(IMAGE_TYPE.BACKDROP, imageObject.get("file_path").getAsString()));
                images.add(imageDTO);
            }catch (Exception ignored){}

        }
        movieDetails.setMovieImages(images);

        //* TRAILER YT
        String trailer = movieDAO.getTrailer(movieId);
        movieDetails.setMovieTrailerYTKey(trailer);

        //* CERTIFICACION
        movieDetails.setMovieCertification(CertUtil.convertCert(movieDAO.getCertificacion(movieId)));

        //* RATING
        int score = ratingService.getRatingByUserAndMovie(String.valueOf(movieId), userId);
        if (score == -1) movieDetails.setMovieUserRating(0);
        else movieDetails.setMovieUserRating(score);

        return movieDetails;
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
            OrderByDateAndScore(orderingDate, orderingScore, filteredMovies);
        }



        //? CREADO DE DTOs Y CARGADO A LA LISTA FINAL DE LOS RESULTADOS
        Set<Integer> movieIds = new HashSet<>();

        for (JsonObject movie: filteredMovies){
            MovieComponentDTO newMovie = new MovieComponentDTO();

            try { //* SI LA PELICULA NO TIENE POSTER, NO SE CARGA EN LA LISTA

                String newMovieImagePosterPath = ImageLinks.imageTypeToLink(IMAGE_TYPE.POSTER, movie.get("poster_path").getAsString()); // get "poster_path" field from JSON

                newMovie.setMovieId(movie.get("id").getAsInt());
                newMovie.setMoviePosterPath(newMovieImagePosterPath);

                if (!movieIds.contains(newMovie.getMovieId()))
                {
                    result.add(newMovie);
                    movieIds.add(newMovie.getMovieId());
                }


            } catch (Exception ignored) {}
        }

        //? PAGINADO
//        System.err.println(result);
//        System.err.println(result.size());
//        System.err.println(Math.ceil((double) result.size() / 39));

        if (Math.ceil((double) result.size() / 39) < Integer.parseInt(page)) return new ArrayList<MovieComponentDTO>();

        List<MovieComponentDTO> moviePage = getPartition(result, Integer.parseInt(page) - 1);
        System.err.println(moviePage);
        return moviePage;
    }

    private static List<MovieComponentDTO> getPartition(List<MovieComponentDTO> list, int partitionIndex) {
        int pageSize = 39;
        int startIndex = partitionIndex * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());

        List<MovieComponentDTO> aux = new ArrayList<>();
        aux.addAll(list);


        return aux.subList(startIndex, endIndex);
    }

    private static void OrderByDate(String orderingDate, List<JsonObject> filteredMovies) {
        if (orderingDate.equals("asc")) { //* ORDENAMOS POR AÑO DE MANERA ASCENDENTE
            Collections.sort(filteredMovies, (o1, o2) -> {

                int year1 = Integer.parseInt(o1.get("release_date").getAsString().substring(0, 4));
                int year2 = Integer.parseInt(o2.get("release_date").getAsString().substring(0, 4));

                return Integer.compare(year1, year2);
            });
        } else { //* ORDENAMOS POR AÑO DE MANERA DESCENDENTE
            Collections.sort(filteredMovies, (o1, o2) -> {

                int year1 = Integer.parseInt(o1.get("release_date").getAsString().substring(0, 4));
                int year2 = Integer.parseInt(o2.get("release_date").getAsString().substring(0, 4));

                return Integer.compare(year2, year1);
            });
        }
    }

    private static void OrderByScore(String orderingScore, List<JsonObject> filteredMovies) {
        Comparator<JsonObject> comparator = Comparator.comparingDouble(jsonObject -> jsonObject.get("vote_average").getAsDouble());

        if (orderingScore.equals("asc")) {
            filteredMovies.sort(comparator);
        } else {
            filteredMovies.sort(comparator.reversed());
        }
    }

    private static void OrderByDateAndScore(String orderingDate, String orderingScore, List<JsonObject> filteredMovies) {
        Collections.sort(filteredMovies, (o1, o2) -> {
            int year1 = Integer.parseInt(o1.get("release_date").getAsString().substring(0, 4));
            int year2 = Integer.parseInt(o2.get("release_date").getAsString().substring(0, 4));

            int yearComparison = Integer.compare(year1, year2);

            if (yearComparison == 0) { //* SI LAS PELICULAS SON DEL MISMO AÑO, ORDENAMOS POR PUNTAJE
                double score1 = o1.get("vote_average").getAsDouble();
                double score2 = o2.get("vote_average").getAsDouble();

                if (orderingScore.equals("asc")) {
                    return Double.compare(score1, score2);
                } else {
                    return Double.compare(score2, score1);
                }
            } else {
                if (orderingDate.equals("asc")) { //* SI LAS PELICULAS SON DE AÑOS DISTINTOS, ORDENAMOS POR AÑO
                    return yearComparison;
                } else {
                    return -yearComparison;
                }
            }
        });
    }


}
