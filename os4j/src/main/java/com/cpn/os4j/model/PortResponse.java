package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PortResponse {
	
	Port aPort;
	List<Port> somePort;

	public Port getPort() {
		return aPort;
	}

	public void setPort(Port aPort) {
		this.aPort = aPort;
	}

	public List<Port> getSomePort() {
		return somePort;
	}

	public void setSomePort(List<Port> somePort) {
		this.somePort = somePort;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("aPort", aPort).append("somePort", somePort);
		return builder.toString();
	}

}
