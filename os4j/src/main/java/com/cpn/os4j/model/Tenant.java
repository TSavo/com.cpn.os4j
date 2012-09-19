package com.cpn.os4j.model;

import java.io.Serializable;

public class Tenant implements Serializable {

	private static final long serialVersionUID = -8570469557845043470L;
	String id;
	String name;
	String description;
	String enabled;

	public String getDescription() {
		return description;
	}

	public String getEnabled() {
		return enabled;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setEnabled(final String enabled) {
		this.enabled = enabled;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
