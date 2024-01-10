package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_movie
            INNER JOIN tb_genre
            WHERE (coalesce(genre, 'default_value') AS genre IS NULL OR tb_movie.genre_id = genre)
            ORDER BY tb_movie.title
           """)
    Page<Movie> find(List<Genre> genre, Pageable pageable);

    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj IN :movies")
    void findMoviesWithGenre(List<Movie> movies);
}