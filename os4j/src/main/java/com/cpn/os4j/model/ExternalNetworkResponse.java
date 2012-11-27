package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ExternalNetworkResponse {

	ExternalNetwork anExternalNetwork;
	List<ExternalNetwork> someExternalNetwork;


	public ExternalNetwork getAnExternalNetwork() {
		return anExternalNetwork;
	}


	public void setAnExternalNetwork(ExternalNetwork anExternalNetwork) {
		this.anExternalNetwork = anExternalNetwork;
	}


	public List<ExternalNetwork> getSomeExternalNetwork() {
		return someExternalNetwork;
	}


	public void setSomeExternalNetwork(List<ExternalNetwork> someExternalNetwork) {
		this.someExternalNetwork = someExternalNetwork;
	}


	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("network", anExternalNetwork).append("networks", someExternalNetwork);
		return builder.toString();
	}
}
