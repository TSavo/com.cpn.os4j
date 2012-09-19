package com.cpn.os4j;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cpn.os4j.model.Tenant;

public class AuthenticationCredentials implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8443488473309706325L;
	PasswordCredentials passwordCredentials;
	String tenantName;

	public AuthenticationCredentials() {
	}

	public AuthenticationCredentials(final PasswordCredentials someCreds, final String tenantName) {
		super();
		passwordCredentials = someCreds;
		this.tenantName = tenantName;
	}

	public AuthenticationCredentials(final PasswordCredentials someCreds, final Tenant aTenant) {
		this(someCreds, aTenant.getName());
	}

	public PasswordCredentials getPasswordCredentials() {
		return passwordCredentials;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setPasswordCredentials(final PasswordCredentials passwordCredentials) {
		this.passwordCredentials = passwordCredentials;
	}

	public void setTenantName(final String tenantName) {
		this.tenantName = tenantName;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("passwordCredentials", passwordCredentials).append("tenantName", tenantName);
		return builder.toString();
	}

}
