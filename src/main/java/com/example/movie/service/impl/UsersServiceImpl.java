package com.example.movie.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.entity.Users;
import com.example.movie.model.RegisterModel;
import com.example.movie.repository.UsersRepository;
import com.example.movie.service.UsersService;

@Service
public class UsersServiceImpl implements UserDetailsService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

//	@Override
	public Users register(Users users) {
		boolean emailExists = usersRepo.findByEmail(users.getEmail()).isPresent();
		boolean usernameExists = usersRepo.findByUsername(users.getUsername()).isPresent();
		
		if(emailExists) {
			throw new RuntimeException(String.format("email '%s' already exists", users.getEmail()));
		} else if(usernameExists) {
			throw new RuntimeException(String.format("username '%s' already exists", users.getUsername()));
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(users.getPassword());
		users.setPassword(encodedPassword);
		
//		Users users = modelMapper.map(registerModel, Users.class);
		
		return usersRepo.save(users);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usersRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("username '%s' not found", username)));
	}

}
