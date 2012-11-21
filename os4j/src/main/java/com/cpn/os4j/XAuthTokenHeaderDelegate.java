package com.cpn.os4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cpn.apiomatic.rest.HttpHeaderDelegate;
import com.cpn.os4j.model.Token;

public class XAuthTokenHeaderDelegate implements HttpHeaderDelegate {

	private Token token;
	
	public XAuthTokenHeaderDelegate(Token token){
		this.token=token;
	}
	
	@Override
	public HttpHeaders getHttpHeaders() {
			final HttpHeaders headers = new HttpHeaders();
			headers.set("X-Auth-Token", token.getId());
			headers.setContentType(MediaType.APPLICATION_JSON);
			return headers;
	}

}
