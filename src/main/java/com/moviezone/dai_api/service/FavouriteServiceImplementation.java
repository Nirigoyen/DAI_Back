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

    @Override
    public FavDTO addFavourite(FavDTO fav, String userId) {

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
    public List<FavDTO> getFavouritesFromUser(String userId) {
        List<FavMovie> favMovies = favouriteDAO.getFavouritesByUser(userId);
        List<FavDTO> favDTOs = new ArrayList<>();
        for(FavMovie favMovie : favMovies){
            favDTOs.add(toDTO(favMovie));
        }
        return favDTOs;
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

