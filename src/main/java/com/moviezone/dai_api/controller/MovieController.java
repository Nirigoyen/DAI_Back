package com.moviezone.dai_api.controller;


import com.moviezone.dai_api.model.dto.MovieComponentDTO;


import com.moviezone.dai_api.model.dto.MovieDTO;
import com.moviezone.dai_api.service.IMovieService;
import com.moviezone.dai_api.model.dto.ErrorResponseDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @GetMapping("/{MovieId}")
    public ResponseEntity<?> movieDetails(@RequestParam (name = "movieId", required = true) String movieId){

        if (movieId == null) return new ResponseEntity<>("Movie ID not specified.", HttpStatus.BAD_REQUEST);

        MovieDTO movieDetails = movieService.getMovieDetails(Integer.parseInt(movieId));


        return new ResponseEntity<>(movieDetails, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> discover(@RequestParam(name = "page", required = false) String page,
                                      @RequestParam(name = "search", required = false) String search,
                                      @RequestParam(name = "genres", required = false) String genres,
                                      @RequestParam(name = "orderByScore", required = false) String orderByScore,
                                      @RequestParam(name = "orderingScore", required = false) String orderingScore,
                                      @RequestParam(name = "orderByDate", required = false) String orderByDate,
                                      @RequestParam(name = "orderingDate", required = false) String orderingDate) {


        //? SI EXISTE PAGE, ESTAMOS EN LA LANDING PAGE
        //? SI EXISTE SEARCH, ESTAMOS EN LA BUSQUEDA
        //! SI EXISTEN AMBOS, RETORNAR UN BAD REQUEST
        if (page == null || page.isEmpty()) return new ResponseEntity<>
                (new ErrorResponseDTO("Bad Request, page not sent", 4), HttpStatus.BAD_REQUEST);




        //* INICIALIZAMOS LA LISTA A RETORNAR
        List<MovieComponentDTO> finalResult = null;



        //? SI EL PARAMETRO DE BUSQUEDA ES NULL, ENTONCES ESTAMOS EN LA LANDING PAGE
        if (search == null || search.isEmpty()){

            //* NOS ASEGURAMOS DE QUE "PAGE" ESTE ENTRE LOS LIMITES QUE SOPORTA TMDB
            if (Integer.parseInt(page) < 1) page = "1"; // if page is less than 1 return page 1
            else if (Integer.parseInt(page) > 500) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK); // if page is greater than 500 return page 500

            //* SI NO HAY GENEROS, ENTONCES LOS INICIALIZAMOS COMO UN STRING VACIO
            if (genres == null) genres = ""; // Genre formatting : <GenreID>%2C<GenreID>%2C>GenreID> - Example: 28%2C18 - GenreName to GenreId should be handled for frontend

            //* OBTENEMOS LOS RESULTADOS DE LA LANDING
            finalResult = movieService.discover(page, genres);
        } else {

            //? SI NO, ENTONCES ESTAMOS EN LA BUSQUEDA
            finalResult = movieService.search(page, search, orderByScore, orderingScore, orderByDate, orderingDate);
        }

        //* SI HAY RESULTADOS, LOS RETORNAMOS
        if (finalResult != null) {
            return new ResponseEntity<>(finalResult, HttpStatus.OK);
        }
        else {//* SI NO HAY RESULTADOS, RETORNAMOS UN NOT FOUND
            return new ResponseEntity<>(new ErrorResponseDTO("Resource Not Found.", 3), HttpStatus.NOT_FOUND);
        }
    }

}


