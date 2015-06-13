package com.kossyuzokwe.test;

import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;

public class VerificationTokenFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public VerificationToken newVerificationToken(User user) {
		String id = mockValues.nextId();
		String token = mockValues.nextUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token, user);
		verificationToken.setVerificationId(id);
		return verificationToken;
	}
	
}
