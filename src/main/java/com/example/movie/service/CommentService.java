package com.example.movie.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.movie.entity.Comment;
import com.example.movie.model.CommentModel;

@Service
public interface CommentService {
	Comment add(CommentModel comment);
	
	Page<Comment> findAll(int page, int size, String sortBy, String sortDirection);
	
	Optional<Comment> findById(UUID id);

	Page<Comment> findByMovieId(UUID movieId, int page, int size, String sortBy, String sortDirection);
	
	Comment update(CommentModel comment);
	
	void delete(UUID id);
}
