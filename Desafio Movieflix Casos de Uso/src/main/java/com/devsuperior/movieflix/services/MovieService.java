package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieDTOGenre;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {
        Optional<Movie> obj = repository.findById(id);
        Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MovieDTO(entity);
    }

    @Transactional
    public Page<MovieDTOGenre> getAllPaged(Long genreId, Pageable pageable) {
        List<Genre> genre = (genreId == 0) ? null : List.of(genreRepository.getReferenceById(genreId));
        Page<Movie> page = repository.find(genre, pageable);
        repository.findMoviesWithGenre(page.getContent());
        return page.map(MovieDTOGenre::new);
    }
}