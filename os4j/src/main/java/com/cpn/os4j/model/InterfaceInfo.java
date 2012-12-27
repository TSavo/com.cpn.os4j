package com.cpn.os4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InterfaceInfo {
	private String version;
	@JsonProperty("addr")
	private String address;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
