package com.cpn.os4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cpn.os4j.command.HttpHeaderDelegate;

public class NoAuthHeaderDelegate implements HttpHeaderDelegate {
	@Override
	public HttpHeaders getHttpHeaders() {
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			return headers;
	}

}
