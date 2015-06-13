package com.kossyuzokwe.test;

import com.kossyuzokwe.event.OnRegistrationCompleteEvent;
import com.kossyuzokwe.event.OnUserEvent;
import com.kossyuzokwe.model.User;

public class OnUserEventFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public OnUserEvent newOnRegistrationCompleteEvent(User user) {
		String url = mockValues.nextUrl();
		return new OnRegistrationCompleteEvent(user, url);
	}
	
	public OnUserEvent newOnReverifyAccountEvent(User user) {
		String url = mockValues.nextUrl();
		return new OnRegistrationCompleteEvent(user, url);
	}
	
	public OnUserEvent newOnResetPasswordEvent(User user) {
		String url = mockValues.nextUrl();
		return new OnRegistrationCompleteEvent(user, url);
	}

}
