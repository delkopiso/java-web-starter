package com.kossyuzokwe.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kossyuzokwe.dao.PasswordResetTokenRepository;
import com.kossyuzokwe.dao.RoleRepository;
import com.kossyuzokwe.dao.UserRepository;
import com.kossyuzokwe.dao.VerificationTokenRepository;
import com.kossyuzokwe.model.PasswordResetToken;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;

@Service
@Transactional
public class UserService {
	
	Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Autowired
	private PasswordResetTokenRepository resetTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Collection<User> findAll() {
		return userRepository.findAll();
	}

	public User findUserByUserId(String id) {
		return userRepository.findByUserId(id);
	}

	public User findUserByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	public User findUserByUserEmail(String email) {
		return userRepository.findByUserEmail(email);
	}

	public User findUserByVerificationToken(String verificationToken) {
		return verificationTokenRepository.findByToken(verificationToken).getUser();
	}

	public User findUserByPasswordResetToken(String token) {
		return resetTokenRepository.findByToken(token).getUser();
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public void deleteUser(String id) {
		userRepository.delete(id);
	}

	public User registerNewUserAccount(User userInfo) {
		User user = new User();
		user.setUserName(userInfo.getUserName());
		user.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
		user.setUserEmail(userInfo.getUserEmail());
		user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_USER")));
		return userRepository.save(user);
	}

	public void createVerificationTokenForUser(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		verificationTokenRepository.save(myToken);
	}

	public VerificationToken getVerificationToken(String VerificationToken) {
		return verificationTokenRepository.findByToken(VerificationToken);
	}

	public VerificationToken regenerateVerificationToken(String existingVerificationToken) {
		VerificationToken vToken = verificationTokenRepository
				.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = verificationTokenRepository.save(vToken);
		return vToken;
	}

	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		resetTokenRepository.save(myToken);
	}

	public PasswordResetToken getPasswordResetToken(String token) {
		return resetTokenRepository.findByToken(token);
	}

	public void changeUserPassword(User user, String password) {
		user.setUserPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

	public boolean validOldPassword(User user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getUserPassword());
	}

	public boolean emailExists(String email) {
		User user = userRepository.findByUserEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}
}
