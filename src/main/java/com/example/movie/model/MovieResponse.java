package com.example.movie.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.example.movie.entity.Genre;

import lombok.Data;

@Data
public class MovieResponse {
	
	private UUID id;

	private String title;

	private String description;

	private String director;

	private LocalDate releaseDate;

	private int duration;

	private Set<Genre> genre;

	private double rating;

	private LocalDateTime created_date;

	private LocalDateTime updated_date;
	
	private String cover;
}
