package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieDTOGenre;
import com.devsuperior.movieflix.services.MovieService;
import com.devsuperior.movieflix.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @Autowired
    private ReviewService reviewService;


    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id){
        MovieDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<MovieDTOGenre>> findAllPaged(
            @RequestParam(value = "genreId", defaultValue = "0") Long genreId,
            Pageable pageable) {
        Page<MovieDTOGenre> list = service.getAllPaged(genreId, pageable);
        return ResponseEntity.ok().body(list);
    }
}
