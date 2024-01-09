package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private AuthService authService;

    @Autowired
    private ReviewRepository repository;

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        User user = authService.authenticated();

        Review entity = new Review();
        entity.setText(dto.getText());
        entity.setMovie(new Movie(dto.getMovieId(), null, null, null, null, null, null));
        entity.setUser(user);
        entity = repository.save(entity);
        return new ReviewDTO(entity);
    }
}
