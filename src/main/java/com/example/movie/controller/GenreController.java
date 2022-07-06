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

import com.example.movie.entity.Genre;
import com.example.movie.service.GenreService;

@RestController
@RequestMapping("/genre")
public class GenreController {

	@Autowired
	GenreService genreService;

	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody Genre genre){
		try {
			return new ResponseEntity<>(genreService.add(genre), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find-all")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "description") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection){
		try {
			return new ResponseEntity<>(genreService.findAll(page, size, sortBy, sortDirection), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Genre genre){
		try {
			return new ResponseEntity<>(genreService.update(genre), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id){
		try {
			genreService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
