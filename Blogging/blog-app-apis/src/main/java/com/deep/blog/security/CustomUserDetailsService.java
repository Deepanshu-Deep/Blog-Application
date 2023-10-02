package com.deep.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deep.blog.entities.User;
import com.deep.blog.exceptions.ResourceNotFoundException;
import com.deep.blog.repositories.UserRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user", "email", username));
	
		
		return user;
	}

	
	
	
	
}
