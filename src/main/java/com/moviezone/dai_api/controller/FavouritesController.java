package com.moviezone.dai_api.controller;

import com.moviezone.dai_api.service.IFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/favourites")
public class FavouritesController {

    @Autowired
    private IFavouriteService favouriteService;

    @PostMapping(value = "/{movieId}")
    public ResponseEntity<?> save()
    {
        return null;
    }


}
