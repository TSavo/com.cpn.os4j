package com.cpn.os4j.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Network {

	String id;
	String name;

	public Network() {
	}

	public Network(final String id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("name", name);
		return builder.toString();
	}

}
