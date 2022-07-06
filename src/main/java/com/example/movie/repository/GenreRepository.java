package com.example.movie.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movie.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
	Page<Genre> findAll(Pageable pageable);
}
