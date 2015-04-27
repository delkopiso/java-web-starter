package com.kossyuzokwe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kossyuzokwe.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	User findByUserName(String username);

	User findByUserEmail(String email);
	
	void delete(User user);

}
