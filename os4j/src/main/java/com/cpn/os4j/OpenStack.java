package com.cpn.os4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.CacheManager;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Node;

import com.cpn.os4j.cache.CacheWrapper;
import com.cpn.os4j.cache.EhcacheWrapper;
import com.cpn.os4j.command.DescribeAddressesCommand;
import com.cpn.os4j.command.DescribeImagesCommand;
import com.cpn.os4j.command.DescribeInstancesCommand;
import com.cpn.os4j.command.DescribeKeyPairsCommand;
import com.cpn.os4j.command.DescribeRegionsCommand;
import com.cpn.os4j.command.DescribeSecurityGroupsCommand;
import com.cpn.os4j.command.DescribeVolumesCommand;
import com.cpn.os4j.signature.HmacSHA256SignatureStrategy;
import com.cpn.os4j.signature.SignatureStrategy;

public class OpenStack {

	private URI uri;

	private OpenStackCredentials credentials;

	private SignatureStrategy signatureStrategy = new HmacSHA256SignatureStrategy();

	private CacheManager cacheManager = CacheManager.create();
	private CacheWrapper<String, Instance> instanceCache = new EhcacheWrapper<>("instances", cacheManager);
	private CacheWrapper<String, IPAddress> ipAddessCache = new EhcacheWrapper<>("ipAddresses", cacheManager);
	private CacheWrapper<String, Region> regionCache = new EhcacheWrapper<>("regions", cacheManager);
	private CacheWrapper<String, Volume> volumeCache = new EhcacheWrapper<>("volumes", cacheManager);
	private CacheWrapper<String, SecurityGroup> securityGroupCache = new EhcacheWrapper<>("securityGroups", cacheManager);
	private CacheWrapper<String, Image> imagesCache = new EhcacheWrapper<>("imagesCache", cacheManager);
	private CacheWrapper<String, KeyPair> keyPairsCache = new EhcacheWrapper<>("keyPairsCache", cacheManager);
	

	public OpenStack(URI aUrl, OpenStackCredentials aCreds) {
		uri = aUrl;
		credentials = aCreds;
		populateCaches();
	}

	public CacheWrapper<String, KeyPair> getKeyPairsCache() {
		return keyPairsCache;
	}
	
	public CacheWrapper<String, SecurityGroup> getSecurityGroupCache() {
		return securityGroupCache;
	}

	public CacheWrapper<String, Image> getImagesCache() {
		return imagesCache;
	}

	public CacheWrapper<String, Region> getRegionCache() {
		return regionCache;
	}

	public CacheWrapper<String, Instance> getInstanceCache() {
		return instanceCache;
	}

	public CacheWrapper<String, IPAddress> getIPAddressCache() {
		return ipAddessCache;
	}

	public CacheWrapper<String, Volume> getVolumeCache() {
		return volumeCache;
	}
	

	public URI getURI() {
		return uri;
	}
	
	public OpenStackCredentials getCredentials(){
		return credentials;
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


	public List<Region> getRegions() {
		List<Region> results = new DescribeRegionsCommand(this).execute();
		getRegionCache().removeAll().putAll(results);
		return results;
	}

	public List<Instance> getInstances() {
		List<Instance> results = new DescribeInstancesCommand(this).execute();
		getInstanceCache().removeAll().putAll(results);
		return results;
	}

	public List<IPAddress> getIPAddresses() {
		List<IPAddress> results = new DescribeAddressesCommand(this).execute();
		getIPAddressCache().removeAll().putAll(results);
		return results;
	}

	public List<Volume> getVolumes() {
		List<Volume> results = new DescribeVolumesCommand(this).execute();
		getVolumeCache().removeAll().putAll(results);
		return results;
	}

	public List<Image> getImages() {
		List<Image> results = new DescribeImagesCommand(this).execute();
		imagesCache.removeAll().putAll(results);
		return results;
	}

	public List<SecurityGroup> getSecurityGroups() {
		List<SecurityGroup> results = new DescribeSecurityGroupsCommand(this).execute();
		securityGroupCache.removeAll().putAll(results);
		return results;
	}
	
	public List<KeyPair> getKeyPairs(){
		List<KeyPair> results = new DescribeKeyPairsCommand(this).execute();
		keyPairsCache.removeAll().putAll(results);
		return results;
	}
	
	public OpenStack populateCaches() {
		getInstances();
		getIPAddresses();
		getRegions();
		getVolumes();
		getSecurityGroups();
		getImages();
		getKeyPairs();
		return this;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("uri", uri).append("credentials", credentials).append("signatureStrategy", signatureStrategy);
		return builder.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> unmarshall(List<Node> aList, Class<T> anUnmarshaller) {
		ArrayList<T> list = new ArrayList<T>();
		for (Node n : aList) {
			try {
				list.add((T) anUnmarshaller.getDeclaredMethod("unmarshall", Node.class, OpenStack.class).invoke(null, n, this));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return list;
	}


}
