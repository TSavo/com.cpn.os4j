package com.cpn.os4j;

import java.io.Serializable;

public class PasswordCredentials implements Serializable {

	private static final long serialVersionUID = -2018402245732990822L;
	String username;
	String password;

	public PasswordCredentials(final String aUsername, final String aPassword) {
		username = aUsername;
		password = aPassword;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

}
