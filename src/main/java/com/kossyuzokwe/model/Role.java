package com.kossyuzokwe.model;

import java.util.Collection;

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
	private Collection<User> users;

	public Role() {
		super();
	}

	public Role(String name) {
		super();
		this.roleName = name;
	}

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

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role role = (Role) obj;
		if (!role.equals(role.roleName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [name=").append(roleName).append("]")
				.append("[id=").append(roleId).append("]");
		return builder.toString();
	}
}
