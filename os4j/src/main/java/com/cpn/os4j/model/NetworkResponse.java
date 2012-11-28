package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NetworkResponse {

	List<Network> networks;
	Network network;

	public List<Network> getNetworks() {
		return networks;
	}

	public void setNetworks(final List<Network> networks) {
		this.networks = networks;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(final Network network) {
		this.network = network;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("networks", networks).append("network",networks);
		return builder.toString();
	}

}
