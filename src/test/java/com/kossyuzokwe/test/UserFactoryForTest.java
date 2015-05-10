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
	
}
