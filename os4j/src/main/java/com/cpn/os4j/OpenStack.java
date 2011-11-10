package com.cpn.os4j;

import java.io.IOException;
import java.net.URI;
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

import com.cpn.os4j.command.AllocateAddressCommand;
import com.cpn.os4j.command.AssociateAddressCommand;
import com.cpn.os4j.command.AttachVolumeCommand;
import com.cpn.os4j.command.CreateSnapshotCommand;
import com.cpn.os4j.command.CreateVolumeCommand;
import com.cpn.os4j.command.DeleteSnapshot;
import com.cpn.os4j.command.DeleteVolumeCommand;
import com.cpn.os4j.command.DescribeAddressesCommand;
import com.cpn.os4j.command.DescribeImagesCommand;
import com.cpn.os4j.command.DescribeInstancesCommand;
import com.cpn.os4j.command.DescribeKeyPairsCommand;
import com.cpn.os4j.command.DescribeRegionsCommand;
import com.cpn.os4j.command.DescribeSecurityGroupsCommand;
import com.cpn.os4j.command.DescribeSnapshotsCommand;
import com.cpn.os4j.command.DescribeVolumesCommand;
import com.cpn.os4j.command.DetachVolumeCommand;
import com.cpn.os4j.command.DisassociateAddressCommand;
import com.cpn.os4j.command.RebootInstancesCommand;
import com.cpn.os4j.command.ReleaseAddressCommand;
import com.cpn.os4j.command.RunInstancesCommand;
import com.cpn.os4j.command.ServerErrorExecption;
import com.cpn.os4j.command.TerminateInstancesCommand;
import com.cpn.os4j.model.IPAddress;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.Instance;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.Region;
import com.cpn.os4j.model.SecurityGroup;
import com.cpn.os4j.model.Snapshot;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.Volume.VolumeAttachment;
import com.cpn.os4j.model.cache.CacheWrapper;
import com.cpn.os4j.model.cache.EhcacheWrapper;

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
	private CacheWrapper<String, Snapshot> snapshotsCache = new EhcacheWrapper<>("snapshotsCache", cacheManager);

	public OpenStack(URI aUrl, OpenStackCredentials aCreds) throws ServerErrorExecption {
		uri = aUrl;
		credentials = aCreds;
		populateCaches();
	}

	public CacheWrapper<String, KeyPair> getKeyPairCache() {
		return keyPairsCache;
	}

	public CacheWrapper<String, SecurityGroup> getSecurityGroupCache() {
		return securityGroupCache;
	}

	public CacheWrapper<String, Image> getImagsCache() {
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

	public CacheWrapper<String, Snapshot> getSnapshotCache() {
		return snapshotsCache;
	}

	public URI getURI() {
		return uri;
	}

	public OpenStackCredentials getCredentials() {
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

	public List<Region> getRegions() throws ServerErrorExecption {
		List<Region> results = new DescribeRegionsCommand(this).execute();
		getRegionCache().removeAll().putAll(results);
		return results;
	}

	public List<Instance> getInstances() throws ServerErrorExecption {
		List<Instance> results = new DescribeInstancesCommand(this).execute();
		getInstanceCache().removeAll().putAll(results);
		return results;
	}

	public List<IPAddress> getIPAddresses() throws ServerErrorExecption {
		List<IPAddress> results = new DescribeAddressesCommand(this).execute();
		getIPAddressCache().removeAll().putAll(results);
		return results;
	}

	public List<Volume> getVolumes() throws ServerErrorExecption {
		List<Volume> results = new DescribeVolumesCommand(this).execute();
		getVolumeCache().removeAll().putAll(results);
		return results;
	}

	public List<Image> getImages() throws ServerErrorExecption {
		List<Image> results = new DescribeImagesCommand(this).execute();
		imagesCache.removeAll().putAll(results);
		return results;
	}

	public List<SecurityGroup> getSecurityGroups() throws ServerErrorExecption {
		List<SecurityGroup> results = new DescribeSecurityGroupsCommand(this).execute();
		securityGroupCache.removeAll().putAll(results);
		return results;
	}

	public List<KeyPair> getKeyPairs() throws ServerErrorExecption {
		List<KeyPair> results = new DescribeKeyPairsCommand(this).execute();
		keyPairsCache.removeAll().putAll(results);
		return results;
	}

	public List<Snapshot> getSnapshots() throws ServerErrorExecption {
		List<Snapshot> results = new DescribeSnapshotsCommand(this).execute();
		snapshotsCache.removeAll().putAll(results);
		return results;
	}

	public IPAddress allocateIPAddress() throws ServerErrorExecption {
		List<IPAddress> results = new AllocateAddressCommand(this).execute();
		ipAddessCache.put(results.get(0).getKey(), results.get(0));
		return results.get(0);
	}

	public OpenStack releaseAddress(IPAddress ipAddress) throws ServerErrorExecption {
		new ReleaseAddressCommand(this, ipAddress).execute();
		getIPAddresses();
		return this;
	}

	public Instance runInstance(Image image, KeyPair keyPair, String instanceType, String addressingType, String minCount, String maxCount, SecurityGroup... groups) throws ServerErrorExecption {
		Instance i = new RunInstancesCommand(this, image, keyPair, instanceType, addressingType, minCount, maxCount, groups).execute().get(0);
		instanceCache.put(i.getKey(), i);
		return i;
	}

	public OpenStack rebootInstance(Instance instance) throws ServerErrorExecption {
		new RebootInstancesCommand(this, instance).execute();
		getInstances();
		return this;
	}

	public OpenStack terminateInstance(Instance anInstance) throws ServerErrorExecption {
		new TerminateInstancesCommand(this, anInstance).execute();
		getInstances();
		return this;
	}

	public OpenStack associateAddress(Instance anInstance, IPAddress anIPAddress) throws ServerErrorExecption {
		new AssociateAddressCommand(this, anInstance, anIPAddress).execute();
		anInstance.setIPAddress(anIPAddress.getIpAddress());
		anIPAddress.setInstanceId(anInstance.getInstanceId());
		getInstances();
		getIPAddresses();
		return this;
	}

	public OpenStack disassociateAddress(IPAddress ipAddress) throws ServerErrorExecption  {
		Instance i = ipAddress.getInstance();
		if (i != null) {
			i.setIPAddress(null);
		}
		new DisassociateAddressCommand(this, ipAddress).execute();
		ipAddress.setInstanceId(null);
		getInstances();
		getIPAddresses();
		return this;
	}

	public Volume createVolume(String anAvailabilityZone, int aSize) throws ServerErrorExecption {
		Volume v = new CreateVolumeCommand(this, anAvailabilityZone, aSize).execute().get(0);
		getVolumes();
		return v;
	}

	public OpenStack deleteVolume(Volume aVolume) throws ServerErrorExecption {
		new DeleteVolumeCommand(this, aVolume).execute();
		return this;
	}

	public Volume createVolumeFromSnapshot(Snapshot aSnapshot, String anAvailabilityZone) throws ServerErrorExecption {
		Volume v = new CreateVolumeCommand(this, anAvailabilityZone, Integer.parseInt(aSnapshot.getVolumeSize()), aSnapshot).execute().get(0);
		getVolumes();
		return v;
	}

	public Snapshot createSnapshotFromVolume(Volume aVolume) throws ServerErrorExecption {
		Snapshot s = new CreateSnapshotCommand(this, aVolume).execute().get(0);
		getSnapshots();
		return s;
	}

	public OpenStack deleteSnapshot(Snapshot snapshot) throws ServerErrorExecption {
		new DeleteSnapshot(this, snapshot).execute();
		return this;
	}

	public VolumeAttachment attachVolumeToInstance(Volume aVolume, Instance anInstance, String aDevice) throws ServerErrorExecption {
		VolumeAttachment v = new AttachVolumeCommand(this, aVolume, anInstance, aDevice).execute().get(0).addToVolume(aVolume);
		getVolumes();
		return v;
	}

	public OpenStack detachVolume(Volume aVolume) throws ServerErrorExecption{
		new DetachVolumeCommand(this, aVolume).execute();
		getVolumes();
		return this;
	}

	public OpenStack forceDetachVolume(Volume aVolume) throws ServerErrorExecption {
		new DetachVolumeCommand(this, aVolume, true).execute();
		getVolumes();
		return this;
	}
	
	public OpenStack populateCaches() throws ServerErrorExecption {
		getInstances();
		getIPAddresses();
		getRegions();
		getVolumes();
		getSecurityGroups();
		getImages();
		getKeyPairs();
		getSnapshots();
		return this;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("uri", uri).append("credentials", credentials).append("signatureStrategy", signatureStrategy);
		return builder.toString();
	}

	

}
