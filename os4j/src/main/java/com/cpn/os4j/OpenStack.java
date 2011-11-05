package com.cpn.os4j;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cpn.os4j.cache.CacheWrapper;
import com.cpn.os4j.cache.EhcacheWrapper;
import com.cpn.os4j.command.DescribeInstancesCommand;
import com.cpn.os4j.command.DescribeRegionsCommand;
import com.cpn.os4j.signature.HmacSHA256SignatureStrategy;
import com.cpn.os4j.signature.SignatureStrategy;

public class OpenStack {

	private URI uri;

	private String secretKey;
	private String accessKeyId;

	private SignatureStrategy signatureStrategy = new HmacSHA256SignatureStrategy();
	
	private CacheWrapper<String, Instance> instanceCache = new EhcacheWrapper<String, Instance>("instances");

	public CacheWrapper<String, Instance> getInstanceCache() {
		return instanceCache;
	}

	public OpenStack(URI aUrl, String anAccessKeyId, String aSecretKey) {
		uri = aUrl;
		accessKeyId = anAccessKeyId;
		secretKey = aSecretKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public URI getURI() {
		return uri;
	}

	public SignatureStrategy getSignatureStrategy() {
		return signatureStrategy;
	}

	public String post(String anAction, String aMessage) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(uri);
		StringEntity entity = new StringEntity(aMessage);
		entity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		post.setEntity(entity);
		String responseBody = client.execute(post, responseHandler);
		return responseBody;

	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public List<Region> getRegions() {
		return new DescribeRegionsCommand(this).execute();

	}

	public List<Instance> getInstances() {
		List<Instance> results = new DescribeInstancesCommand(this).execute();
		getInstanceCache().removeAll();
		for(Instance i : results){
			getInstanceCache().put(i.getInstanceId(), i);
		}
		return results;
	}
}
