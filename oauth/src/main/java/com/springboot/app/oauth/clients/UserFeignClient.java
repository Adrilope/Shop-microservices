package com.springboot.app.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.app.commons.users.models.entity.User;

@FeignClient("service-users")
public interface UserFeignClient {

	@GetMapping("/users/search/find-username")
	public User findByUsername(@RequestParam String username);
	
}
