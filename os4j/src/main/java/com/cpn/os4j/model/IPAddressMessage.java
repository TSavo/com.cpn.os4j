package com.cpn.os4j.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IPAddressMessage {

	@JsonProperty("floating_ip")
	IPAddress ipAddress;
	@JsonProperty("floating_ips")
	List<IPAddress> ipAddresses;
	@JsonProperty("floating_ip_pools")
	List<IPAddressPool> pools;

	public IPAddress getIpAddress() {
		return ipAddress;
	}

	public List<IPAddress> getIpAddresses() {
		return ipAddresses;
	}

	public List<IPAddressPool> getPools() {
		return pools;
	}

	public void setIpAddress(final IPAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setIpAddresses(final List<IPAddress> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public void setPools(final List<IPAddressPool> pools) {
		this.pools = pools;
	}

}
