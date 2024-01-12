package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieDTOGenre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    @Transactional(readOnly = true)
    public Page<MovieDTOGenre> findAllPaged(String genreId, Pageable pageable) {

        List<Long> genreIds = List.of();
        genreIds = Stream.of(genreId.split(",")).map(Long::parseLong).toList();

        Page<MovieProjection> page = repository.findAll(genreIds, pageable);
        List<Long> movieIds = page.map(MovieProjection::getId).toList();

        List<Movie> entities = repository.searchMoviesWithGenre(movieIds);
        List<MovieDTOGenre> dtos = entities.stream().map(MovieDTOGenre::new).toList();

        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

}
