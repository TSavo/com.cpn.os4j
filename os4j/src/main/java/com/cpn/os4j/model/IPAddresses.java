package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class IPAddresses implements Serializable {

	private static final long serialVersionUID = 1892397415639139533L;
	@JsonProperty("public")
	List<IPAddress> publicAddresses;
	@JsonProperty("private")
	List<IPAddress> privateAddresses;

	public List<IPAddress> getPrivateAddresses() {
		return privateAddresses;
	}

	public List<IPAddress> getPublicAddresses() {
		return publicAddresses;
	}

	public void setPrivateAddresses(final List<IPAddress> privateAddresses) {
		this.privateAddresses = privateAddresses;
	}

	public void setPublicAddresses(final List<IPAddress> publicAddresses) {
		this.publicAddresses = publicAddresses;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("publicAddresses", publicAddresses).append("privateAddresses", privateAddresses);
		return builder.toString();
	}

}
