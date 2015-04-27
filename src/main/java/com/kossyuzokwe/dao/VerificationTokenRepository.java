package com.kossyuzokwe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String>{

	VerificationToken findByToken(String token);

	VerificationToken findByUser(User user);
}
