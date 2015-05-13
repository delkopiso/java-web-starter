package com.kossyuzokwe.test;

import com.kossyuzokwe.model.Role;

public class RoleFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Role newRole() {
		String id = mockValues.nextId();
		return newRole(id);
	}

	public Role newRole(String id) {
		Role role = new Role();
		role.setRoleId(id);
		return role;
	}
	
	public Role newRoleWithName() {
		Role role = newRole();
		role.setRoleName(mockValues.nextString(mockValues.nextInteger()));
		return role;
	}
	
}
