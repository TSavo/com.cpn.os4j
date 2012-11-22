package com.cpn.os4j;

import java.util.List;

import com.cpn.apiomatic.rest.HttpHeaderDelegate;
import com.cpn.apiomatic.rest.RestCommand;
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
		headerDelegate=new XAuthTokenHeaderDelegate(aToken);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getTenantId() {
		return token.getTenant().getId();
	}

	
	public List<Network> listNetworks() {
		final RestCommand<String, NetworkResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/tenants/" + getTenantId() + "/networks/detail");
		command.setResponseModel(NetworkResponse.class);
		return command.get().getNetworks();
	}
}
