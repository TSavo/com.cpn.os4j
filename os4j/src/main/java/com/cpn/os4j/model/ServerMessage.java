package com.cpn.os4j.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ServerMessage {

	Server server;
	List<Server> servers;

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(final List<Server> servers) {
		this.servers = servers;
	}
	public Server getServer() {
		return server;
	}

	public void setServer(final Server server) {
		this.server = server;
	}

}
