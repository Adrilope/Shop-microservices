package com.springboot.app.oauth.services;

import com.springboot.app.commons.users.models.entity.User;

public interface UserService {
	
	public User findByUsername(String username);
	
}
