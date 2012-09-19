package com.cpn.os4j.model;

import java.io.Serializable;

public class Token implements Serializable {

	private static final long serialVersionUID = 558710544261035380L;
	String expires;
	String id;
	Tenant tenant;

	public String getExpires() {
		return expires;
	}

	public String getId() {
		return id;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setExpires(final String expires) {
		this.expires = expires;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setTenant(final Tenant tenant) {
		this.tenant = tenant;
	}

}
