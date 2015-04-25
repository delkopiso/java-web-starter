package com.kossyuzokwe.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kossyuzokwe.dao.RoleRepository;
import com.kossyuzokwe.dao.UserRepository;
import com.kossyuzokwe.model.Role;
import com.kossyuzokwe.model.User;

@Transactional
@Service
public class InitDbService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init() {
		if (roleRepository.findByRoleName("ROLE_ADMIN") == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			Role roleUser = new Role();
			roleUser.setRoleName("ROLE_USER");
			roleRepository.save(roleUser);

			Role roleAdmin = new Role();
			roleAdmin.setRoleName("ROLE_ADMIN");
			roleRepository.save(roleAdmin);

			User userAdmin = new User();
			userAdmin.setUserEnabled(true);
			userAdmin.setUserName("admin");
			userAdmin.setUserEmail("admin@admin.com");
			userAdmin.setUserPassword(encoder.encode("admin"));
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleUser);
			roles.add(roleAdmin);
			userAdmin.setRoles(roles);
			userRepository.save(userAdmin);

			User userNotAdmin = new User();
			userNotAdmin.setUserEnabled(true);
			userNotAdmin.setUserName("test");
			userNotAdmin.setUserEmail("test@test.com");
			userNotAdmin.setUserPassword(encoder.encode("test"));
			List<Role> roles2 = new ArrayList<Role>();
			roles2.add(roleUser);
			userNotAdmin.setRoles(roles2);
			userRepository.save(userNotAdmin);
		}
	}
}
