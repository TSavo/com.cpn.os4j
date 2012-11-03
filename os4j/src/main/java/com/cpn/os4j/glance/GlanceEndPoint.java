package com.cpn.os4j.glance;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.springframework.web.client.RestTemplate;

import com.cpn.os4j.model.Token;

public class GlanceEndPoint {

	private String url = "http://localhost:9292";
	private Token token;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	static HttpClient client = new HttpClient();

	public List<GlanceImage> listImages() {
		final RestTemplate template = new RestTemplate();
		return template.getForEntity(url, GlanceImageListResponse.class).getBody().getImages();
	}

	public GlanceImage upload(final String aName, final InputStream stream, final long length) {
		// RestTemplate template = new RestTemplate();
		// template.postForEntity(url, new InputStreamEntity(stream, length),
		// GlanceImageResponse.class).getBody().getImage();
		final PostMethod method = new PostMethod(url+ "/v1/images");
		try {
			method.setRequestEntity(new InputStreamRequestEntity(stream, length, "application/octet-stream"));
			method.setContentChunked(false);
			method.addRequestHeader("x-image-meta-name", aName);
			method.addRequestHeader("X-Auth-Token", token.getId());
			method.addRequestHeader("content-type", "application/octet-stream");
			GlanceEndPoint.client.executeMethod(method);
			final ObjectMapper mapper = new ObjectMapper();
			final ObjectReader reader = mapper.reader(GlanceImageResponse.class);
			return reader.<GlanceImageResponse> readValue(new String(method.getResponseBody())).getImage();
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}
	}

	public GlanceEndPoint(final String aUrl, Token aToken) {
		url = aUrl;
		token = aToken;
	}
}
