package com.kossyuzokwe.service;

import java.util.Arrays;
import java.util.List;
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

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOneByUserId(String id) {
		return userRepository.findOne(id);
	}

	public void delete(String id) {
		userRepository.delete(id);
	}

	public User findOneByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	public User findOneByUserEmail(String email) {
		return userRepository.findByUserEmail(email);
	}

	public User registerNewUserAccount(User userInfo) {
		User user = new User();
		user.setUserName(userInfo.getUserName());
		user.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
		user.setUserEmail(userInfo.getUserEmail());
		user.setRoles(Arrays.asList(roleRepository.findByRoleName("ROLE_USER")));
		return userRepository.save(user);
	}

	public User getUser(String verificationToken) {
        User user = verificationTokenRepository.findByToken(verificationToken).getUser();
		return user;
	}

	public VerificationToken getVerificationToken(String VerificationToken) {
		return verificationTokenRepository.findByToken(VerificationToken);
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public void createVerificationTokenForUser(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		verificationTokenRepository.save(myToken);
	}

	public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
		VerificationToken vToken = verificationTokenRepository
				.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = verificationTokenRepository.save(vToken);
		return vToken;
	}

	public void createPasswordResetTokenForUser(User user,
			String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		resetTokenRepository.save(myToken);
	}

	public User findUserByEmail(String email) {
		return userRepository.findByUserEmail(email);
	}

	public PasswordResetToken getPasswordResetToken(String token) {
		return resetTokenRepository.findByToken(token);
	}

	public User getUserByPasswordResetToken(String token) {
		return resetTokenRepository.findByToken(token).getUser();
	}

	public User getUserByID(String id) {
		return userRepository.findOne(id);
	}

	public void changeUserPassword(User user, String password) {
		user.setUserPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

	public boolean checkIfValidOldPassword(User user,
			String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getUserPassword());
	}

	public boolean emailExist(String email) {
		User user = userRepository.findByUserEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}
}
