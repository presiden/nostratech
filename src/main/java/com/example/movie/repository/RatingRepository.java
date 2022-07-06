package com.example.movie.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.movie.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
	
	@Query("select avg(r.rating) from Rating r where r.movie.id = :movieId")
	double findRatingByMovieId(UUID movieId);
}
