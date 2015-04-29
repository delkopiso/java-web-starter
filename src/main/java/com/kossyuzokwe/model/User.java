package com.kossyuzokwe.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;

import com.kossyuzokwe.validation.UniqueEmail;
import com.kossyuzokwe.validation.UniqueUsername;

@Entity
@Table(name = "app_user")
public class User {

	@Id
	@GenericGenerator(name = "sequence_object_id", strategy = "com.kossyuzokwe.util.ObjectIdGenerator")
	@GeneratedValue(generator = "sequence_object_id")
	@Column(name = "user_id")
	private String userId;

	@Size(min = 3, message = "Name must be at least 3 characters.")
	@Column(name = "user_name", unique = true)
	@UniqueUsername(message = "Username already exists.")
	private String userName;

	@Email(message = "Invalid email address.")
	@Size(min = 1, message = "Invalid email address.")
	@Column(name = "user_email", unique = true)
	@UniqueEmail(message = "Email address already exists.")
	private String userEmail;

	@Size(min = 5, message = "Password must be at least 5 characters.")
	@Column(name = "user_password")
	private String userPassword;

	@Column(name = "user_enabled")
	private boolean userEnabled;

	@ManyToMany
	@JoinTable
	private Collection<Role> roles;

	public User() {
		super();
		this.userEnabled = false;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public boolean isUserEnabled() {
		return userEnabled;
	}

	public void setUserEnabled(boolean userEnabled) {
		this.userEnabled = userEnabled;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userEmail == null) ? 0 : userEmail.hashCode());
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
		User user = (User) obj;
		if (!userEmail.equals(user.userEmail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userName=").append(userName).append("]")
				.append("[userEmail=").append(userEmail).append("]");
		return builder.toString();
	}
}
