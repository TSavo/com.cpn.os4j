package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SubnetResponse {

	Subnet subnet;
	List<Subnet> subnets;

	public Subnet getSubnet() {
		return subnet;
	}

	public void setSubnet(final Subnet subnet) {
		this.subnet = subnet;
	}

	public List<Subnet> getSubnets() {
		return subnets;
	}

	public void setSubnets(final List<Subnet> subnets) {
		this.subnets = subnets;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("subnet", subnet).append("subnets", subnets);
		return builder.toString();
	}
}
