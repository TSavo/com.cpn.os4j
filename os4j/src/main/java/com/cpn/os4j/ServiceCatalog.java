package com.cpn.os4j;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.AccessResponse;

public class ServiceCatalog {

	private static RestTemplate restTemplate = new RestTemplate();
	private final OpenStackCredentials credentials;
	private final String serverUrl;

	public ServiceCatalog(final String aServerUrl, final OpenStackCredentials someCredentials) {
		serverUrl = aServerUrl;
		credentials = someCredentials;
	}

	public Access getAccess() {
		return ServiceCatalog.restTemplate.postForEntity(serverUrl + "/v2.0/tokens", credentials, AccessResponse.class).getBody().getAccess();
	}

	public HttpHeaders getHttpHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		return headers;
	}
}
