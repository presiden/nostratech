package com.example.movie.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie.entity.Users;
import com.example.movie.model.RegisterModel;
import com.example.movie.repository.UsersRepository;
import com.example.movie.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UsersRepository usersRepo;

	@Override
	public Users register(RegisterModel registerModel) {
		boolean emailExists = usersRepo.findByEmail(registerModel.getEmail()).isPresent();
		boolean usernameExists = usersRepo.findByUsername(registerModel.getUsername()).isPresent();
		
		if(emailExists) {
			throw new RuntimeException(String.format("email '%s' already exists", registerModel.getEmail()));
		} else if(usernameExists) {
			throw new RuntimeException(String.format("username '%s' already exists", registerModel.getUsername()));
		}
		
		Users users = modelMapper.map(registerModel, Users.class);
		
		return usersRepo.save(users);
	}

}
