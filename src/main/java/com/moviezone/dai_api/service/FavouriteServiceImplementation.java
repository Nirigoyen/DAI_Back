package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.IFavouriteDAO;
import com.moviezone.dai_api.model.dto.FavDTO;
import com.moviezone.dai_api.model.dto.MovieComponentDTO;
import com.moviezone.dai_api.model.entity.FavMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteServiceImplementation implements IFavouriteService{

    @Autowired
    private IFavouriteDAO favouriteDAO;

    @Override
    public FavDTO addFavourite(FavDTO fav) {

        favouriteDAO.addFavourite(toModel(fav));
        return fav;
    }

    @Override
    public void removeFavourite(String userId, int movieId) {
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
        favMovie.setUserScore(fav.getUserScore());

        return favMovie;
    }

    private FavDTO toDTO(FavMovie fav) {
        FavDTO favDTO = new FavDTO();
        favDTO.setAverageScore(fav.getAverageScore());
        favDTO.setMovieId(fav.getMovieId());
        favDTO.setTitle(fav.getTitle());
        favDTO.setOverview(fav.getOverview());
        favDTO.setPosterPath(fav.getPosterPath());
        favDTO.setUserScore(fav.getUserScore());
        favDTO.setId(fav.getId());

        return favDTO;
    }
}

