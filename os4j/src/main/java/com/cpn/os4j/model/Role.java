package com.cpn.os4j.model;

import java.io.Serializable;

public class Role implements Serializable {

	private static final long serialVersionUID = -3354277509186160658L;
	String id;
	String name;
	String tenantId;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setTenantId(final String tenantId) {
		this.tenantId = tenantId;
	}
}
