package com.cpn.os4j.model;

public class ServerIPAddressConfiguration implements ServerConfiguration {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5701996450907355403L;
	String accessIPv4;

	public ServerIPAddressConfiguration() {
	}

	public ServerIPAddressConfiguration(final String anIPAddress) {
		setAccessIPv4(anIPAddress);
	}

	public String getAccessIPv4() {
		return accessIPv4;
	}

	public void setAccessIPv4(final String accessIPv4) {
		this.accessIPv4 = accessIPv4;
	}

}
