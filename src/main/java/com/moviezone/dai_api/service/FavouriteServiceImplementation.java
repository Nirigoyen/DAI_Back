package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.IFavouriteDAO;
import com.moviezone.dai_api.model.dao.IRatingDAO;
import com.moviezone.dai_api.model.dao.IUserDAO;
import com.moviezone.dai_api.model.dto.FavDTO;
import com.moviezone.dai_api.model.dto.GenreDTO;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.model.entity.FavMovie;
import com.moviezone.dai_api.model.entity.Genre;
import com.moviezone.dai_api.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteServiceImplementation implements IFavouriteService{

    @Autowired
    private IFavouriteDAO favouriteDAO;

    @Autowired
    private IRatingService ratingService;

    @Autowired
    private IGenreService genreService;

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private SecurityExpressionHandler webSecurityExpressionHandler;

    @Override
    public FavDTO addFavourite(FavDTO fav, String userId) {

        if (isFavourite(userId, fav.getMovieId())) return null;

        FavMovie favMovie = toModel(fav);
        User user = userDAO.findUserById(userId);

        user.getFavMovies().add(favMovie);
        favMovie.setUser(user);

        favouriteDAO.addFavourite(favMovie);

        fav.setId(favMovie.getId());
        return fav;
    }

    @Override
    public void removeFavourite(String userId, String movieId) {
        favouriteDAO.removeFavourite(userId, movieId);
    }

    @Override
    public List<FavDTO> getFavouritesFromUser(String userId, String genres) {

        ArrayList<String> genreList = new ArrayList<>(List.of(genres.split(",")));

        List<FavDTO> response = new ArrayList<>();

        List<FavMovie> favMovies = favouriteDAO.getFavouritesByUser(userId);

        if (genreList.get(0).isEmpty()) {
            for(FavMovie favMovie : favMovies){
            response.add(toDTO(favMovie));
            }
            return response;
        }

        for(FavMovie favMovie : favMovies){

            ArrayList<Genre> favGenreList = new ArrayList<>(favMovie.getGenres());
            ArrayList<String> favGenresIds = new ArrayList<>();
            for(Genre genre : favGenreList){
                favGenresIds.add(String.valueOf(genre.getId()));
            }

            if(favGenresIds.containsAll(genreList)){
                response.add(toDTO(favMovie));
            }
        }
        return response;
    }

    private FavMovie toModel(FavDTO fav) {
        FavMovie favMovie = new FavMovie();
        favMovie.setAverageScore(fav.getAverageScore());
        favMovie.setMovieId(fav.getMovieId());
        favMovie.setTitle(fav.getTitle());
        favMovie.setOverview(fav.getOverview());
        favMovie.setPosterPath(fav.getPosterPath());

        List<Genre> genres = new ArrayList<>();
        for(GenreDTO genreDTO : fav.getGenres()){
            genres.add(genreService.getGenreById(genreDTO.getGenreId()));
        }
        favMovie.setGenres(genres);

        return favMovie;
    }

    private FavDTO toDTO(FavMovie fav) {
        FavDTO favDTO = new FavDTO();
        favDTO.setAverageScore(fav.getAverageScore());
        favDTO.setMovieId(fav.getMovieId());
        favDTO.setTitle(fav.getTitle());
        favDTO.setOverview(fav.getOverview());
        favDTO.setPosterPath(fav.getPosterPath());
        favDTO.setUserScore((ratingService.getRatingByUserAndMovie(fav.getMovieId(), fav.getUser().getId())));
        favDTO.setId(fav.getId());

        List<GenreDTO> genreDTOs = new ArrayList<>();
        for(Genre genre : fav.getGenres()){
            genreDTOs.add(genreService.toDTO(genre));
        }
        favDTO.setGenres(genreDTOs);

        return favDTO;
    }

    @Override
    public boolean isFavourite(String userId, String movieId) {
        return favouriteDAO.isFavourite(userId, movieId);
    }
}

