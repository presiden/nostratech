package com.example.movie.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movie.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
	Page<Movie> findAll(Pageable pageable);
	
	Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
