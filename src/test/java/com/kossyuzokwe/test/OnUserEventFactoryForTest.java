package com.kossyuzokwe.test;

import com.kossyuzokwe.event.OnRegistrationCompleteEvent;
import com.kossyuzokwe.event.OnResetPasswordEvent;
import com.kossyuzokwe.event.OnReverifyAccountEvent;
import com.kossyuzokwe.model.User;

public class OnUserEventFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public OnRegistrationCompleteEvent newOnRegistrationCompleteEvent(User user) {
		String url = mockValues.nextUrl();
		return new OnRegistrationCompleteEvent(user, url);
	}
	
	public OnReverifyAccountEvent newOnReverifyAccountEvent(User user) {
		String url = mockValues.nextUrl();
		return new OnReverifyAccountEvent(user, url);
	}
	
	public OnResetPasswordEvent newOnResetPasswordEvent(User user) {
		String url = mockValues.nextUrl();
		return new OnResetPasswordEvent(user, url);
	}

}
