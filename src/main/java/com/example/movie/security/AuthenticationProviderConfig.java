package com.example.movie.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.movie.entity.Users;
import com.example.movie.repository.UsersRepository;
import com.example.movie.service.UsersService;
import com.example.movie.service.impl.UsersServiceImpl;

@Component
public class AuthenticationProviderConfig implements AuthenticationProvider {

	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	UsersServiceImpl usersService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UsersServiceImpl usersServiceImpl;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
		if(usersRepo.findByUsername(username).isEmpty()) {
			return null;
		}

		Users users = usersRepo.findByUsername(authentication.getName()).get();
		
		if(!bCryptPasswordEncoder.matches(password, users.getPassword())) {
			return null;
		}
		
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(bCryptPasswordEncoder);
//		provider.setUserDetailsService(usersServiceImpl);
		
		return new UsernamePasswordAuthenticationToken(
				username, password, new ArrayList<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
