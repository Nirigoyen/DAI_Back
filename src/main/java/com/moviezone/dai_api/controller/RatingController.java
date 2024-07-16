package com.moviezone.dai_api.controller;


import com.moviezone.dai_api.model.dto.RatingDTO;
import com.moviezone.dai_api.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ratings")
public class RatingController {

    @Autowired
    private IRatingService ratingService;

    @GetMapping(value = "")
    public ResponseEntity<?> getRating(@RequestParam String movieId, @RequestParam String userId) {

        if (movieId == null || movieId.isEmpty() || userId == null || userId.isEmpty())
            return new ResponseEntity<>("Missing parameters", HttpStatus.BAD_REQUEST);

        int result = ratingService.getRatingByUserAndMovie(movieId, userId);

        if (result != -1) return new ResponseEntity<>(result, HttpStatus.OK);

        else return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping
    public ResponseEntity<?> rateMovie(@RequestBody RatingDTO ratingDTO) {

        if (ratingDTO == null || ratingDTO.getMovieId().isEmpty() || ratingDTO.getUserId().isEmpty())
            return new ResponseEntity<>("Missing parameters", HttpStatus.BAD_REQUEST);

        int result = ratingService.rateMovie(ratingDTO);

        if (result == -3) return new ResponseEntity<>("Movie already rated", HttpStatus.BAD_REQUEST);
        if (result == -2) return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        if (result == -1) return new ResponseEntity<>("Error rating movie", HttpStatus.INTERNAL_SERVER_ERROR);
        else return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<?> modifyRating(@RequestBody RatingDTO ratingDTO) {

        if (ratingDTO == null || ratingDTO.getMovieId().isEmpty() || ratingDTO.getUserId().isEmpty())
            return new ResponseEntity<>("Missing parameters", HttpStatus.BAD_REQUEST);

        int result = ratingService.updateRating(ratingDTO);

        if (result == -2) return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        if (result == -1) return new ResponseEntity<>("Error rating movie", HttpStatus.INTERNAL_SERVER_ERROR);
        else return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
