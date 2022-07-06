package com.example.movie.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.movie.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

	Page<Comment> findAll(Pageable pageable);
	
	@Query("select c from Comment c where c.movie.id = :movieId")
	Page<Comment> findByMovieId(UUID movieId, Pageable pageable);
}
