package com.deep.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.deep.blog.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@CrossOrigin("*")
public class SecurityConfig{
	

	@Autowired
	private CustomUserDetailsService cDetailsService;
	
	@Bean
	 SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {
		
		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.csrf().disable()
		.authorizeHttpRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
		
		
		
		return null;
		
	}
	
	
//	 @Bean(name ="mymanager")
//	    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//
//
//	        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class).parentAuthenticationManager(null);
//
//	        authenticationManagerBuilder.authenticationProvider(cDetailsService);
//
//	       
//
//
//	        return authenticationManagerBuilder.build();
//	    })
//	
	
	@Bean
	 PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}
	
}
