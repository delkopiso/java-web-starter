package com.kossyuzokwe.event;

import com.kossyuzokwe.model.User;

public class OnResetPasswordEvent extends OnUserEvent {
	
	private static final long serialVersionUID = 5675341276284485337L;

	public OnResetPasswordEvent(User user, String appUrl) {
		super(user, appUrl);
	}

	public User getUser() {
		return user;
	}

	public String getAppUrl() {
		return appUrl;
	}

}
