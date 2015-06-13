package com.kossyuzokwe.test;

import com.kossyuzokwe.model.PasswordResetToken;
import com.kossyuzokwe.model.User;

public class PasswordResetTokenFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PasswordResetToken newPasswordResetToken(User user) {
		String id = mockValues.nextId();
		String token = mockValues.nextUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
		passwordResetToken.setPasswordResetTokenId(id);
		return passwordResetToken;
	}
	
}
