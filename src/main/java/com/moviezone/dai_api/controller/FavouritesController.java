package com.moviezone.dai_api.controller;

import com.moviezone.dai_api.model.dto.FavDTO;
import com.moviezone.dai_api.model.dto.MovieIdDTO;
import com.moviezone.dai_api.model.dto.RatingDTO;
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

    @PostMapping(value = "/{userId}")
    public ResponseEntity<?> save(@PathVariable String userId, @RequestBody FavDTO favMovie)
    {
        if(userId == null || userId.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(userService.getUser(userId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        FavDTO response = favouriteService.addFavourite(favMovie, userId);

        if (response == null) return new ResponseEntity<>("Already a Favourite.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> deleteFav(@PathVariable String userId, @RequestParam int movie)
    {
        if(userId == null || userId.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(userService.getUser(userId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        favouriteService.removeFavourite(userId, String.valueOf(movie));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getAllFromUser(@PathVariable String userId, @RequestParam String genres)
    {
        if(userId == null || userId.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(userService.getUser(userId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<FavDTO> favourites = favouriteService.getFavouritesFromUser(userId, genres);
        return new ResponseEntity<>(favourites, HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<?> updateFavRatings(@PathVariable String userId, @RequestBody FavDTO fav){
        if(userId == null || userId.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(userService.getUser(userId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        FavDTO response = favouriteService.updateFavRatings(userId, fav);
        if (response == null) return new ResponseEntity<>("Favourite does not exists", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
