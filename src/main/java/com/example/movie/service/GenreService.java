package com.example.movie.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.movie.entity.Genre;

@Service
public interface GenreService {

	Genre add(Genre genre);
	
	Page<Genre> findAll(int page, int size, String sortBy, String sortDirection);
	
	Optional<Genre> findById(UUID id);
	
	Genre update(Genre genre);
	
	void delete(UUID id);
}
