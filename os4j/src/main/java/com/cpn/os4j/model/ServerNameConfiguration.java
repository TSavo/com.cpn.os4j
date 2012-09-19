package com.cpn.os4j.model;

public class ServerNameConfiguration implements ServerConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5701996450907355403L;
	String name;

	public ServerNameConfiguration() {
	}

	public ServerNameConfiguration(final String aName) {
		setName(aName);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
