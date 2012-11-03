package com.cpn.os4j;

import java.util.List;

import com.cpn.os4j.command.HttpHeaderDelegate;
import com.cpn.os4j.command.RestCommand;
import com.cpn.os4j.model.Network;
import com.cpn.os4j.model.NetworkResponse;
import com.cpn.os4j.model.Token;

public class NetworkEndpoint {

	String serverUrl;
	Token token;
	HttpHeaderDelegate headerDelegate;
	public NetworkEndpoint(final String aServerUrl, final Token aToken) {
		super();
		token = aToken;
		serverUrl = aServerUrl;
		headerDelegate=new CommonHttpHeaderDelegate(aToken);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getTenantId() {
		return token.getTenant().getId();
	}

	
	public List<Network> listNetworks() {
		final RestCommand<String, NetworkResponse> command = new RestCommand<>(token);
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/tenants/" + getTenantId() + "/networks/detail");
		command.setResponseModel(NetworkResponse.class);
		return command.get().getNetworks();
	}
}
