package com.kossyuzokwe.test;

import java.util.Calendar;
import java.util.Date;

import com.kossyuzokwe.model.PasswordResetToken;
import com.kossyuzokwe.model.User;

public class PasswordResetTokenFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	private PasswordResetToken newPasswordResetToken(User user) {
		String id = mockValues.nextId();
		String token = mockValues.nextUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
		passwordResetToken.setPasswordResetTokenId(id);
		return passwordResetToken;
	}
	
	public PasswordResetToken newPasswordResetToken(User user, boolean expired) {
		PasswordResetToken passwordResetToken = newPasswordResetToken(user);
		if (expired) {
			Date now = Date.from(Calendar.getInstance().toInstant());
			passwordResetToken.setExpiryDate(now);
		}
		return passwordResetToken;
	}
	
}
