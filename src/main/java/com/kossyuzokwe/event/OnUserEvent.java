package com.kossyuzokwe.event;

import org.springframework.context.ApplicationEvent;

import com.kossyuzokwe.model.User;

public abstract class OnUserEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 2025217756408301120L;
	protected User user;
	protected String appUrl;

	public OnUserEvent(User user, String appUrl) {
		super(user);
		this.user = user;
		this.appUrl = appUrl;
	}

	public abstract User getUser();

	public abstract String getAppUrl();
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserEvent [user=").append(user).append("]")
				.append("[appUrl=").append(appUrl).append("]");
		return builder.toString();
	}

}
