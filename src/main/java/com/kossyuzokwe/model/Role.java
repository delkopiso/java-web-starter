package com.kossyuzokwe.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GenericGenerator(name = "sequence_object_id", strategy = "com.kossyuzokwe.util.ObjectIdGenerator")
	@GeneratedValue(generator = "sequence_object_id")
	@Column(name = "role_id")
	private String roleId;

	@Column(name = "role_name")
	private String roleName;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
