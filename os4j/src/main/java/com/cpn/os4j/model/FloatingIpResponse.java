package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FloatingIpResponse {
	Floatingip floatingip;
	List<Floatingip> floatingips;

	
	public Floatingip getFloatingip() {
		return floatingip;
	}


	public void setFloatingIp(Floatingip floatingip) {
		this.floatingip = floatingip;
	}


	public List<Floatingip> getFloatingips() {
		return floatingips;
	}


	public void setFloatingIps(List<Floatingip> floatingips) {
		this.floatingips = floatingips;
	}


	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("floatingIp", floatingip).append("floatingIps", floatingips);
		return builder.toString();
	}
}
