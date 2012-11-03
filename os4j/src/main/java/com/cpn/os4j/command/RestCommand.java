package com.cpn.os4j.command;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.cpn.os4j.model.Token;

public class RestCommand<Request, Response> {

	private static final RestTemplate restTemplate = new RestTemplate();
	private String path;
	private Request requestModel;
	private Class<Response> responseModel;
	private HttpHeaderDelegate headerDelegate;
	private final Token token;

	public RestCommand(final Token aToken) {
		token = aToken;
	}

	
	public void delete() {
		if (getRequestModel() == null) {
			RestCommand.restTemplate.exchange(getPath(), HttpMethod.DELETE, new HttpEntity<String>(getHttpHeaders()), null);
		} else {
			RestCommand.restTemplate.exchange(getPath(), HttpMethod.DELETE, new HttpEntity<Request>(getRequestModel(), getHttpHeaders()), null);
		}
	}

	public Response get() {
		return RestCommand.restTemplate.exchange(getPath(), HttpMethod.GET, new HttpEntity<String>(getHttpHeaders()), getResponseModel()).getBody();
	}

	public HttpHeaders getHttpHeaders() {
		return headerDelegate.getHttpHeaders();
	}

	public String getPath() {
		return path;
	}

	public Request getRequestModel() {
		return requestModel;
	}

	public Class<Response> getResponseModel() {
		return responseModel;
	}

	public Response post() {
		return RestCommand.restTemplate.exchange(getPath(), HttpMethod.POST, new HttpEntity<Request>(getRequestModel(), getHttpHeaders()), getResponseModel()).getBody();
	}

	public Response put() {
		return RestCommand.restTemplate.exchange(getPath(), HttpMethod.PUT, new HttpEntity<Request>(getRequestModel(), getHttpHeaders()), getResponseModel()).getBody();
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setRequestModel(final Request requestModel) {
		this.requestModel = requestModel;
	}

	public void setResponseModel(final Class<Response> responseModel) {
		this.responseModel = responseModel;
	}

	public HttpHeaderDelegate getHeaderDelegate() {
		return headerDelegate;
	}

	public void setHeaderDelegate(HttpHeaderDelegate headerDelegate) {
		this.headerDelegate = headerDelegate;
	}

	
}
