package com.example.movie.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Rating;
import com.example.movie.entity.Users;
import com.example.movie.model.RatingModel;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.RatingRepository;
import com.example.movie.repository.UsersRepository;
import com.example.movie.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	RatingRepository ratingRepo;

	@Autowired
	MovieRepository movieRepo;

	@Autowired
	UsersRepository usersRepo;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Rating add(RatingModel ratingModel) throws Exception {
		Movie movie = movieRepo.findById(ratingModel.getMovieId()).get();
		Users users = usersRepo.findById(ratingModel.getUsersId()).get();
		
		Rating rating = new Rating();
		rating.setMovie(movie);
		rating.setUsers(users);
		rating.setRating(ratingModel.getRating());
		
		ratingRepo.save(rating);
		calculateRating(movie);
		
		return rating;
	}
	
	@Override
	public Optional<Rating> findById(UUID id) {
		return ratingRepo.findById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Rating update(RatingModel ratingModel) {
		Movie movie = movieRepo.findById(ratingModel.getMovieId()).get();
		Users users = usersRepo.findById(ratingModel.getUsersId()).get();
		
		Rating rating = new Rating();
		rating.setMovie(movie);
		rating.setUsers(users);
		rating.setRating(ratingModel.getRating());
		rating.setUpdatedDate(LocalDateTime.now());
		
		ratingRepo.save(rating);
		calculateRating(movie);
		
		return rating;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(UUID id) {
		if(findById(id).isPresent()) {
			Rating rating = findById(id).get();
			ratingRepo.delete(rating);

			calculateRating(rating.getMovie());
		}
	}
	
	private void calculateRating(Movie movie) {
		double getRating = ratingRepo.findRatingByMovieId(movie.getId());
		movie.setRating(getRating);
		movieRepo.save(movie);
	}
}
