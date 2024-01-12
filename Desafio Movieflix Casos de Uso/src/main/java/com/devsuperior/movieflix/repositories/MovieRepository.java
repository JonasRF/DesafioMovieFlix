package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
            SELECT DISTINCT tb_movie.id, tb_movie.title, tb_movie.sub_title, tb_movie.movie_year, tb_movie.img_url
            FROM tb_movie
            INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id
            WHERE (:genre = 0 OR tb_movie.genre_id IN :genre)
            ORDER BY tb_movie.title
            """, countQuery = """
            SELECT COUNT(*) FROM (
            SELECT DISTINCT tb_movie.id, tb_movie.title, tb_movie.sub_title, tb_movie.movie_year, tb_movie.img_url
            FROM tb_movie
            INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id
            WHERE (:genre IS NULL OR tb_movie.genre_id IN :genre)
            ORDER BY tb_movie.title
            ) 
            """)
    Page<MovieProjection> findAll(List<Long> genre, Pageable pageable);

    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.id IN :movies ORDER BY obj.title")
   List<Movie> searchMoviesWithGenre(List<Long> movies);

}

