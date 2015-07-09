package com.kossyuzokwe.test;

import com.kossyuzokwe.model.PasswordChange;
import com.kossyuzokwe.model.User;

public class UserFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public User newUser() {
		String id = mockValues.nextId();
		return newUser(id);
	}

	public User newUser(String id) {
		User user = new User();
		user.setUserId(id);
		return user;
	}
	
	public User newUserWithUserName() {
		User user = newUser();
		String userName = mockValues.nextString(mockValues.nextInteger());
		user.setUserName(userName);
		return user;
	}
	
	public User newUserWithEmail() {
		User user = newUser();
		String email = mockValues.nextString(mockValues.nextInteger());
		user.setUserEmail(email);
		return user;
	}
	
	public User newUserWithPassword() {
		User user = newUser();
		String password = mockValues.nextString(mockValues.nextInteger());
		user.setUserPassword(password);
		return user;
	}
	
	public User newRegisteredUser() {
		User user = newUser();
		String userName = mockValues.nextString(mockValues.nextInteger());
		user.setUserName(userName);
		String email = mockValues.nextString(mockValues.nextInteger());
		user.setUserEmail(email);
		String password = mockValues.nextString(mockValues.nextInteger());
		user.setUserPassword(password);
		return user;
	}
	
	public PasswordChange newPasswordChange(User user) {
		PasswordChange passwordChange = new PasswordChange();
		passwordChange.setUserName(user.getUserName());
		passwordChange.setOldPassword(user.getUserPassword());
		passwordChange.setNewPassword(mockValues.nextString(mockValues.nextInteger()));
		return passwordChange;
	}
	
}
