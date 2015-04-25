package com.kossyuzokwe.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kossyuzokwe.dao.RoleRepository;
import com.kossyuzokwe.dao.UserRepository;
import com.kossyuzokwe.model.Role;
import com.kossyuzokwe.model.User;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOneByUserId(String id) {
		return userRepository.findOne(id);
	}

	public void save(User user) {
		user.setUserEnabled(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setUserPassword(encoder.encode(user.getUserPassword()));
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepository.findByRoleName("ROLE_USER"));
		user.setRoles(roles);
		userRepository.save(user);
	}

	public void delete(String id) {
		userRepository.delete(id);
	}
	
	public User findOneByUserName(String username) {
		return userRepository.findByUserName(username);
	}
}
