package com.cpn.os4j;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.AccessResponse;

public class IdentityEndpoint {

	private static RestTemplate restTemplate = new RestTemplate();

	private String serverUrl;

	public IdentityEndpoint(String aServerUrl) {
		serverUrl = aServerUrl;
	}
	public HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		return headers;
	}
	public Access getAccess(OpenStackCredentials someCredentials) {
		
		return restTemplate.postForEntity(serverUrl + "/v2.0/tokens", someCredentials, AccessResponse.class).getBody().getAccess();
	}
}
