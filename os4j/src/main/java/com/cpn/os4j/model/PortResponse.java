package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PortResponse {
	
	Port port;
	List<Port> ports;

	public Port getPort() {
		return port;
	}

	public void setPort(final Port port) {
		this.port = port;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public void setPorts(final List<Port> ports) {
		this.ports = ports;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("port", port).append("ports", ports);
		return builder.toString();
	}

}
