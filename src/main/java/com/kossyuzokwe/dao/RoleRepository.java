package com.kossyuzokwe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kossyuzokwe.model.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

	Role findByRoleName(String roleName);

}
