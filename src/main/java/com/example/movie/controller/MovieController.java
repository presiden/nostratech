package com.example.movie.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.movie.entity.Movie;
import com.example.movie.model.MovieThumbnail;
import com.example.movie.model.Response;
import com.example.movie.model.ResponsePage;
import com.example.movie.service.MovieService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private static final Logger logger = LogManager.getLogger(MovieController.class);
    
	@Autowired
	MovieService movieService;

	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid Movie movie, @RequestParam MultipartFile[] file){
		try {
			Response response = new Response(movieService.add(movie, file));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GetMapping(value = "/find-movie/{movieId}/{usersId}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable UUID movieId, @PathVariable UUID usersId) {
        return movieService.getVideo(movieId, usersId);
    }

	@GetMapping("/find-all")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "title") String sortBy, @RequestParam(defaultValue = "asc") String sortDirection){
		try {
			ResponsePage responsePage = new ResponsePage(movieService.findAll(page, size, sortBy, sortDirection));
			return new ResponseEntity<>(responsePage, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find-all-recommend")
	public ResponseEntity<?> findAllRecommend(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, String sortBy, String sortDirection){
		try {
			ResponsePage responsePage = new ResponsePage(movieService.findAllRecommend(page, size, sortBy, sortDirection));
			return new ResponseEntity<>(responsePage, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find-all-new-movie")
	public ResponseEntity<?> findAllNewMovie(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, String sortBy, String sortDirection){
		try {
			ResponsePage responsePage = new ResponsePage(movieService.findAllNewMovie(page, size, sortBy, sortDirection));
			return new ResponseEntity<>(responsePage, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find-by-id/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id){
		try {
			Response response = new Response(movieService.findById(id).get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Movie movie){
		try {
			Response response = new Response(movieService.update(movie));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			movieService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping({"/find-by-title", "/find-by-title/{title}"})
	public ResponseEntity<?> findByTitle(@PathVariable(required = false) String title, 
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {
			Page<MovieThumbnail> movieThumbnail = movieService.findByTitle(title, page, size);
			ResponsePage response = new ResponsePage(movieThumbnail);
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
