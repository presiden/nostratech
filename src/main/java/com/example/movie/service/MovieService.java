package com.example.movie.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.movie.entity.Movie;
import com.example.movie.model.MovieResponse;
import com.example.movie.model.MovieThumbnail;

import reactor.core.publisher.Mono;

@Service
public interface MovieService {

	Movie add(Movie movie, MultipartFile[] file) throws IOException;
	
	Mono<Resource> getVideo(UUID movieId, UUID usersId);
	
//	byte[] getCover(UUID id) throws IOException;
	
	Page<MovieThumbnail> findAll(int page, int size, String sortBy, String sortDirection) throws IOException;
	
	Page<MovieThumbnail> findAllRecommend(int page, int size, String sortBy, String sortDirection) throws IOException;
	
	Page<MovieThumbnail> findAllNewMovie(int page, int size, String sortBy, String sortDirection) throws IOException;
	
	Optional<Movie> findById(UUID id) throws IOException;
	
	Movie update(Movie movie);
	
	void delete(UUID id) throws IOException ;

	Page<MovieThumbnail> findByTitle(String title, int page, int size) throws IOException;
}
