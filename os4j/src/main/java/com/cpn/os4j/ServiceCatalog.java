package com.cpn.os4j;

import com.cpn.apiomatic.rest.RestCommand;
import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.AccessResponse;

public class ServiceCatalog {
	private final OpenStackCredentials credentials;
	private final String serverUrl;
	private static RestCommand<OpenStackCredentials,AccessResponse> restCommand = new RestCommand<>();

	public ServiceCatalog(final String aServerUrl, final OpenStackCredentials someCredentials) {
		serverUrl = aServerUrl;
		credentials = someCredentials;
	}

	public Access getAccess() {
		restCommand.setUrl(serverUrl + "/v2.0/tokens");
		restCommand.setRequestModel(credentials);
		restCommand.setResponseModel(AccessResponse.class);
		return ServiceCatalog.restCommand.post().getAccess();
	}
}
