package com.moviezone.dai_api.controller;

import com.moviezone.dai_api.model.dto.FavDTO;
import com.moviezone.dai_api.model.entity.FavMovie;
import com.moviezone.dai_api.service.IFavouriteService;
import com.moviezone.dai_api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/favourites")
public class FavouritesController {

    @Autowired
    private IFavouriteService favouriteService;

    @Autowired
    private IUserService userService;

    @PostMapping(value = "")
    public ResponseEntity<?> save(@RequestBody FavDTO favMovie)
    {
        FavDTO response = favouriteService.addFavourite(favMovie);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteFav()
    {
        return null;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getAllFromUser(@PathVariable String userId)
    {
        if(userId == null || userId.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(userService.getUser(userId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<FavDTO> favourites = favouriteService.getFavouritesFromUser(userId);
        return new ResponseEntity<>(favourites, HttpStatus.OK);
    }

}
