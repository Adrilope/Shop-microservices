package com.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.app.commons.users.models.entity.User;
import com.springboot.app.oauth.clients.UserFeignClient;

@Service
public class UserService implements UserDetailsService {

	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserFeignClient feignClient;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = feignClient.findByUsername(username);
		
		if (user == null) {
			log.error("Login error, user '"+ username +"' not found");
			throw new UsernameNotFoundException("Login error, user '"+ username +"' not found");
		}
		
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(authority -> log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		log.info("Authenticated user: " + username);
		
		return new org.springframework.security.core.userdetails.
				User(user.getUsername(), user.getPassword(), user.getEnabled(), 
						true, true, true, authorities);
	}

}
