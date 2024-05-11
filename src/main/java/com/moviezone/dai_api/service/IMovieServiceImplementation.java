package com.moviezone.dai_api.service;

public class IMovieServiceImplementation implements IMovieService{
    public void getMovieById(int id){
        System.out.println("Getting movie by id: " + id);
    }

    public void discover(){
        System.out.println("Discovering movies");
    }
}
