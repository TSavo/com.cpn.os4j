package com.cpn.os4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NetworkInterface {

	//At any point of time only one is in use.
	String port;
	String uuid;

	public NetworkInterface() {
		// TODO Auto-generated constructor stub
	}
	
	public NetworkInterface(String port, String uuid) {
		super();
		this.port = port;
		this.uuid = uuid;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
