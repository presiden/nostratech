package com.example.movie.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.entity.Users;
import com.example.movie.model.RegisterModel;
import com.example.movie.service.impl.UsersServiceImpl;

@RestController
@RequestMapping("/users")
public class UsersController {
	
    private static final Logger logger = LogManager.getLogger(UsersController.class);
    
	@Autowired
	UsersServiceImpl usersService;
	
	@Autowired
	ModelMapper modelMapper;
	
//	@GetMapping("/login")
//	public ResponseEntity<?> login(@Valid @RequestBody LoginModel loginModel){
//		try {
//			return new ResponseEntity<>(usersService.login(loginModel), HttpStatus.OK);
//		}catch(Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterModel registerModel){
		try {
			Users users = modelMapper.map(registerModel, Users.class);
			
			return new ResponseEntity<>(usersService.register(users), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
