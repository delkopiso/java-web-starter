package com.kossyuzokwe.test;

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
	
}