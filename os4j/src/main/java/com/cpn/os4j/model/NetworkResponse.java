package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;


public class NetworkResponse {

	Network network;
	List<Network> networks;

	public Network getNetwork() {
		return network;
	}

	public List<Network> getNetworks() {
		return networks;
	}

	public void setNetwork(final Network network) {
		this.network = network;
	}

	public void setNetworks(final List<Network> networks) {
		this.networks = networks;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("network", network).append("networks", networks);
		return builder.toString();
	}

}
