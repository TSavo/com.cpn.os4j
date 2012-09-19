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

public class GlanceEndPoint {

	private static String url = "http://archive:9292/v1/images";

	static HttpClient client = new HttpClient();

	public static List<GlanceImage> listImages() {
		final RestTemplate template = new RestTemplate();
		return template.getForEntity(GlanceEndPoint.url, GlanceImageListResponse.class).getBody().getImages();
	}

	public static GlanceImage upload(final String aName, final InputStream stream, final long length) {
		// RestTemplate template = new RestTemplate();
		// template.postForEntity(url, new InputStreamEntity(stream, length),
		// GlanceImageResponse.class).getBody().getImage();
		final PostMethod method = new PostMethod(GlanceEndPoint.url);
		try {
			method.setRequestEntity(new InputStreamRequestEntity(stream, length, "application/octet-stream"));
			method.setContentChunked(false);
			method.addRequestHeader("x-image-meta-name", aName);
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

	public GlanceEndPoint(final String aUrl) {
		GlanceEndPoint.url = aUrl;
	}
}
