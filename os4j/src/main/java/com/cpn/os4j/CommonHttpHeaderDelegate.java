package com.cpn.os4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cpn.os4j.command.HttpHeaderDelegate;
import com.cpn.os4j.model.Token;

public class CommonHttpHeaderDelegate implements HttpHeaderDelegate {

	private Token token;
	
	public CommonHttpHeaderDelegate(Token token){
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
