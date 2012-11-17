package com.cpn.os4j.model;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cpn.os4j.ComputeEndpoint;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class IPAddressPool {

	String name = "default";

	@JsonIgnore
	private transient ComputeEndpoint computeEndpoint;

	public IPAddressPool() {
		// TODO Auto-generated constructor stub
	}

	public IPAddressPool(final String aName) {
		name = aName;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final IPAddressPool other = (IPAddressPool) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public ComputeEndpoint getComputeEndpoint() {
		return computeEndpoint;
	}

	public List<IPAddress> getIPAddresses() {
		final List<IPAddress> addresses = getComputeEndpoint().listAddresses();
		final Iterator<IPAddress> i = addresses.iterator();
		while (i.hasNext()) {
			final IPAddress ip = i.next();
			if (!ip.getPool().equals(name) || (ip.getInstanceId() != null)) {
				i.remove();
			}
		}
		return addresses;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public void setComputeEndpoint(final ComputeEndpoint computeEndpoint) {
		this.computeEndpoint = computeEndpoint;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("name", name);
		return builder.toString();
	}

}
