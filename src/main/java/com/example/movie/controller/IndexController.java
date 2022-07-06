package com.example.movie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {
	
	@GetMapping("/index")
	public ResponseEntity<?> index(){
		try {
			return new ResponseEntity<>("Index", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
