package com.springboot.app.users.models.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.springboot.app.commons.users.models.entity.User;

@RepositoryRestResource(path = "users")		// export all methods to endpoint /${path} without a @Service
public interface UserDAO extends JpaRepository<User, Long>{
	@RestResource(path = "find-username")							// rename path endpoint to this method
	public User findByUsername(@Param("username") String username);		// @Param: rename the param
	
	// same as above
	@Query("select u from User u where u.username=?1")
	public User findUsername(String username);
}
