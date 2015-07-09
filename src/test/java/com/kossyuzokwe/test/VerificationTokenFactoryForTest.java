package com.kossyuzokwe.test;

import java.util.Calendar;
import java.util.Date;

import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;

public class VerificationTokenFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	private VerificationToken newVerificationToken(User user) {
		String id = mockValues.nextId();
		String token = mockValues.nextUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token, user);
		verificationToken.setVerificationId(id);
		return verificationToken;
	}
	
	public VerificationToken newVerificationToken(User user, boolean expired) {
		VerificationToken verificationToken = newVerificationToken(user);
		if (expired) {
			Date now = Date.from(Calendar.getInstance().toInstant());
			verificationToken.setExpiryDate(now);
		}
		return verificationToken;
	}
	
}
