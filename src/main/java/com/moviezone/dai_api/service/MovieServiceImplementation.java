package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImplementation implements IMovieService{
    public void getMovieById(int id){
        System.out.println("Getting movie by id: " + id);
    }

    public List<MovieDTO> discover(){
        System.out.println("Discovering movies");
        return null;
    }
}
