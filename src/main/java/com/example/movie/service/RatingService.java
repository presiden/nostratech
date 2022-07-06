package com.example.movie.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.movie.entity.Rating;
import com.example.movie.model.RatingModel;

@Service
public interface RatingService {

	Rating add(RatingModel ratingModel) throws Exception;
	
	Optional<Rating> findById(UUID id);
	
	Rating update(RatingModel ratingModel);
	
	void delete(UUID id);
}
