package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieDTOGenre;
import com.devsuperior.movieflix.services.MovieService;
import com.devsuperior.movieflix.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id){
        MovieDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @GetMapping
    public ResponseEntity<Page<MovieDTOGenre>> findAll(
            @RequestParam(value = "genreId", defaultValue = "0") String genreId,
            Pageable pageable) {
        Page<MovieDTOGenre> list = service.findAllPaged(genreId, pageable);
        return ResponseEntity.ok().body(list);
    }
}
