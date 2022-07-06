package com.example.movie.service;

import org.springframework.stereotype.Service;

import com.example.movie.entity.Users;
import com.example.movie.model.RegisterModel;

@Service
public interface UsersService {
	
	Users register(RegisterModel registerModel);
}
