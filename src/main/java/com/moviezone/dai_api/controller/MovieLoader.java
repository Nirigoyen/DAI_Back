package com.moviezone.dai_api.controller;

import com.moviezone.dai_api.service.IPopulaterService;
import com.moviezone.dai_api.service.MovieServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/loadDB")
public class MovieLoader {

    @Autowired
    private IPopulaterService populater;

    @PostMapping
    public void load() {
        populater.populateDB();
    }
}
