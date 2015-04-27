package com.kossyuzokwe.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kossyuzokwe.dao.RoleRepository;
import com.kossyuzokwe.dao.UserRepository;
import com.kossyuzokwe.model.Role;
import com.kossyuzokwe.model.User;

@Service
public class InitDbService implements ApplicationListener<ContextRefreshedEvent> {
	
	private boolean alreadySetup = false;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

/*	@PostConstruct
	public void init() {
		if (roleRepository.findByRoleName("ROLE_ADMIN") == null) {
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
			userAdmin.setUserPassword(passwordEncoder.encode("admin"));
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleUser);
			roles.add(roleAdmin);
			userAdmin.setRoles(roles);
			userRepository.save(userAdmin);

			User userNotAdmin = new User();
			userNotAdmin.setUserEnabled(true);
			userNotAdmin.setUserName("test");
			userNotAdmin.setUserEmail("test@test.com");
			userNotAdmin.setUserPassword(passwordEncoder.encode("test"));
			List<Role> roles2 = new ArrayList<Role>();
			roles2.add(roleUser);
			userNotAdmin.setRoles(roles2);
			userRepository.save(userNotAdmin);
		}
	}
*/
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
            return;
        }
		
		createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setUserPassword(passwordEncoder.encode("admin"));
        adminUser.setUserEmail("admin@admin.com");
        adminUser.setRoles(Arrays.asList(adminRole, userRole));
        adminUser.setUserEnabled(true);
        userRepository.save(adminUser);

        User testUser = new User();
        testUser.setUserName("test");
        testUser.setUserPassword(passwordEncoder.encode("test"));
        testUser.setUserEmail("test@test.com");
        testUser.setRoles(Arrays.asList(userRole));
        testUser.setUserEnabled(true);
        userRepository.save(testUser);

        alreadySetup = true;
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
}
