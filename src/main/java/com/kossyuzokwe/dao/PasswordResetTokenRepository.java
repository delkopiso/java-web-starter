package com.kossyuzokwe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kossyuzokwe.model.PasswordResetToken;
import com.kossyuzokwe.model.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String>{

	PasswordResetToken findByToken(String token);

	PasswordResetToken findByUser(User user);
}
