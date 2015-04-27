package com.kossyuzokwe.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kossyuzokwe.dao.PasswordResetTokenRepository;
import com.kossyuzokwe.dao.RoleRepository;
import com.kossyuzokwe.dao.UserRepository;
import com.kossyuzokwe.dao.VerificationTokenRepository;
import com.kossyuzokwe.model.Role;
import com.kossyuzokwe.model.User;

@Service
public class InitDbService implements ApplicationListener<ContextRefreshedEvent> {

	Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Autowired
	private PasswordResetTokenRepository resetTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		createRoleIfNotFound("ROLE_ADMIN");
		createRoleIfNotFound("ROLE_USER");
		createAdminIfNotFound();
		createTestUserIfNotFound();
	}

	@Transactional
	private Role createRoleIfNotFound(String name) {
		Role role = roleRepository.findByRoleName(name);
		if (role == null) {
			role = new Role(name);
			roleRepository.save(role);
		}
		return role;
	}

	@Transactional
	private User createAdminIfNotFound() {
		User user = userRepository.findByUserName("admin");
		if (user == null) {
			Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
			Role userRole = roleRepository.findByRoleName("ROLE_USER");
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setUserPassword(passwordEncoder.encode("admin"));
			adminUser.setUserEmail("admin@admin.com");
			adminUser.setRoles(Arrays.asList(adminRole, userRole));
			adminUser.setUserEnabled(true);
			user = userRepository.save(adminUser);
		}
		return user;
	}

	@Transactional
	private User createTestUserIfNotFound() {
		User user = userRepository.findByUserName("test");
		if (user == null) {
			Role userRole = roleRepository.findByRoleName("ROLE_USER");
			User testUser = new User();
			testUser.setUserName("test");
			testUser.setUserPassword(passwordEncoder.encode("test"));
			testUser.setUserEmail("test@test.com");
			testUser.setRoles(Arrays.asList(userRole));
			testUser.setUserEnabled(true);
			user = userRepository.save(testUser);
		}
		return user;
	}
}
