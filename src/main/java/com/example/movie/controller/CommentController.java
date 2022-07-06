package com.example.movie.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.movie.model.CommentModel;
import com.example.movie.model.Response;
import com.example.movie.model.ResponsePage;
import com.example.movie.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody CommentModel comment){
		try {
			Response response = new Response(commentService.add(comment));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find-all")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "createdDate") String sortBy,
			@RequestParam(defaultValue = "desc") String sortDirection) {
		try {
			ResponsePage responsePage = new ResponsePage(commentService.findAll(page, size, sortBy, sortDirection));
			return new ResponseEntity<>(responsePage, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find-by-id/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id){
		try {
			Response response = new Response(commentService.findById(id).get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find-by-movie-id/{movieId}")
	public ResponseEntity<?> findByMovieId(@PathVariable UUID movieId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "createdDate") String sortBy,
			@RequestParam(defaultValue = "desc") String sortDirection){
		try {
			ResponsePage responsePage = new ResponsePage(commentService.findByMovieId(movieId, page, size, sortBy, sortDirection));
			return new ResponseEntity<>(responsePage, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody CommentModel comment){
		try {
			Response response = new Response(commentService.update(comment));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			commentService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
