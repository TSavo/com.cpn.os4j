package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PortResponse {
	
	Port aPort;
	List<Port> somePort;

	public Port getPort() {
		return aPort;
	}

	public void setPort(final Port aPort) {
		this.aPort = aPort;
	}

	public List<Port> getSomePort() {
		return somePort;
	}

	public void setSomePort(final List<Port> somePort) {
		this.somePort = somePort;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("aPort", aPort).append("somePort", somePort);
		return builder.toString();
	}

}
