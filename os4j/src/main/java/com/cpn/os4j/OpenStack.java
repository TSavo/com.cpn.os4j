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
import com.cpn.os4j.command.ServerErrorExeception;
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

	private final CacheManager cacheManager = CacheManager.create();

	private final OpenStackCredentials credentials;

	private final CacheWrapper<String, Image> imagesCache = new EhcacheWrapper<>("imagesCache", cacheManager);

	private final CacheWrapper<String, Instance> instanceCache = new EhcacheWrapper<>("instances", cacheManager);
	private final CacheWrapper<String, IPAddress> ipAddessCache = new EhcacheWrapper<>("ipAddresses", cacheManager);
	private final CacheWrapper<String, KeyPair> keyPairsCache = new EhcacheWrapper<>("keyPairsCache", cacheManager);
	private final CacheWrapper<String, Region> regionCache = new EhcacheWrapper<>("regions", cacheManager);
	private final CacheWrapper<String, SecurityGroup> securityGroupCache = new EhcacheWrapper<>("securityGroups", cacheManager);
	private final SignatureStrategy signatureStrategy = new HmacSHA256SignatureStrategy();
	private final CacheWrapper<String, Snapshot> snapshotsCache = new EhcacheWrapper<>("snapshotsCache", cacheManager);
	private final URI uri;
	private final CacheWrapper<String, Volume> volumeCache = new EhcacheWrapper<>("volumes", cacheManager);

	public OpenStack(final URI aUrl, final OpenStackCredentials aCreds) throws ServerErrorExeception, IOException {
		uri = aUrl;
		credentials = aCreds;
		populateCaches();
	}

	public IPAddress allocateIPAddress() throws ServerErrorExeception, IOException, IOException {
		final List<IPAddress> results = new AllocateAddressCommand(this).execute();
		ipAddessCache.put(results.get(0).getKey(), results.get(0));
		return results.get(0);
	}

	public OpenStack associateAddress(final Instance anInstance, final IPAddress anIPAddress) throws ServerErrorExeception, IOException {
		new AssociateAddressCommand(this, anInstance, anIPAddress).execute();
		anInstance.setIPAddress(anIPAddress.getIpAddress());
		anIPAddress.setInstanceId(anInstance.getInstanceId());
		getInstances();
		getIPAddresses();
		return this;
	}

	public VolumeAttachment attachVolumeToInstance(final Volume aVolume, final Instance anInstance, final String aDevice) throws ServerErrorExeception, IOException {
		final VolumeAttachment v = new AttachVolumeCommand(this, aVolume, anInstance, aDevice).execute().get(0).addToVolume(aVolume);
		getVolumes();
		return v;
	}

	public Snapshot createSnapshotFromVolume(final Volume aVolume) throws ServerErrorExeception, IOException {
		final Snapshot s = new CreateSnapshotCommand(this, aVolume).execute().get(0);
		getSnapshots();
		return s;
	}

	public Volume createVolume(final String anAvailabilityZone, final int aSize) throws ServerErrorExeception, IOException {
		final Volume v = new CreateVolumeCommand(this, anAvailabilityZone, aSize).execute().get(0);
		getVolumes();
		return v;
	}

	public Volume createVolumeFromSnapshot(final Snapshot aSnapshot, final String anAvailabilityZone) throws ServerErrorExeception, IOException {
		final Volume v = new CreateVolumeCommand(this, anAvailabilityZone, Integer.parseInt(aSnapshot.getVolumeSize()), aSnapshot).execute().get(0);
		getVolumes();
		return v;
	}

	public OpenStack deleteSnapshot(final Snapshot snapshot) throws ServerErrorExeception, IOException {
		new DeleteSnapshot(this, snapshot).execute();
		return this;
	}

	public OpenStack deleteVolume(final Volume aVolume) throws ServerErrorExeception, IOException {
		new DeleteVolumeCommand(this, aVolume).execute();
		return this;
	}

	public OpenStack detachVolume(final Volume aVolume) throws ServerErrorExeception, IOException {
		new DetachVolumeCommand(this, aVolume).execute();
		getVolumes();
		return this;
	}

	public OpenStack disassociateAddress(final IPAddress ipAddress) throws ServerErrorExeception, IOException {
		final Instance i = ipAddress.getInstance();
		if (i != null) {
			i.setIPAddress(null);
		}
		new DisassociateAddressCommand(this, ipAddress).execute();
		ipAddress.setInstanceId(null);
		getInstances();
		getIPAddresses();
		return this;
	}

	public OpenStack forceDetachVolume(final Volume aVolume) throws ServerErrorExeception, IOException {
		new DetachVolumeCommand(this, aVolume, true).execute();
		getVolumes();
		return this;
	}

	public OpenStackCredentials getCredentials() {
		return credentials;
	}

	public List<Image> getImages() throws ServerErrorExeception, IOException, IOException {
		final List<Image> results = new DescribeImagesCommand(this).execute();
		imagesCache.removeAll().putAll(results);
		return results;
	}

	public CacheWrapper<String, Image> getImagsCache() {
		return imagesCache;
	}

	public CacheWrapper<String, Instance> getInstanceCache() {
		return instanceCache;
	}

	public List<Instance> getInstances() throws ServerErrorExeception, IOException, IOException {
		final List<Instance> results = new DescribeInstancesCommand(this).execute();
		getInstanceCache().removeAll().putAll(results);
		return results;
	}

	public CacheWrapper<String, IPAddress> getIPAddressCache() {
		return ipAddessCache;
	}

	public List<IPAddress> getIPAddresses() throws ServerErrorExeception, IOException, IOException {
		final List<IPAddress> results = new DescribeAddressesCommand(this).execute();
		getIPAddressCache().removeAll().putAll(results);
		return results;
	}

	public CacheWrapper<String, KeyPair> getKeyPairCache() {
		return keyPairsCache;
	}

	public List<KeyPair> getKeyPairs() throws ServerErrorExeception, IOException, IOException {
		final List<KeyPair> results = new DescribeKeyPairsCommand(this).execute();
		keyPairsCache.removeAll().putAll(results);
		return results;
	}

	public CacheWrapper<String, Region> getRegionCache() {
		return regionCache;
	}

	public List<Region> getRegions() throws ServerErrorExeception, IOException, IOException {
		final List<Region> results = new DescribeRegionsCommand(this).execute();
		getRegionCache().removeAll().putAll(results);
		return results;
	}

	public CacheWrapper<String, SecurityGroup> getSecurityGroupCache() {
		return securityGroupCache;
	}

	public List<SecurityGroup> getSecurityGroups() throws ServerErrorExeception, IOException, IOException {
		final List<SecurityGroup> results = new DescribeSecurityGroupsCommand(this).execute();
		securityGroupCache.removeAll().putAll(results);
		return results;
	}

	public SignatureStrategy getSignatureStrategy() {
		return signatureStrategy;
	}

	public CacheWrapper<String, Snapshot> getSnapshotCache() {
		return snapshotsCache;
	}

	public List<Snapshot> getSnapshots() throws ServerErrorExeception, IOException, IOException {
		final List<Snapshot> results = new DescribeSnapshotsCommand(this).execute();
		snapshotsCache.removeAll().putAll(results);
		return results;
	}

	public URI getURI() {
		return uri;
	}

	public CacheWrapper<String, Volume> getVolumeCache() {
		return volumeCache;
	}

	public List<Volume> getVolumes() throws ServerErrorExeception, IOException, IOException {
		final List<Volume> results = new DescribeVolumesCommand(this).execute();
		getVolumeCache().removeAll().putAll(results);
		return results;
	}

	public OpenStack populateCaches() throws ServerErrorExeception, IOException {
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

	public String post(final String anAction, final String aMessage) throws ClientProtocolException, IOException {
		final HttpClient client = new DefaultHttpClient();

		final HttpPost post = new HttpPost(uri);
		final StringEntity entity = new StringEntity(aMessage);
		entity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
		final ResponseHandler<String> responseHandler = new BasicResponseHandler();
		post.setEntity(entity);
		final String responseBody = client.execute(post, responseHandler);
		return responseBody;

	}

	public OpenStack rebootInstance(final Instance instance) throws ServerErrorExeception, IOException {
		new RebootInstancesCommand(this, instance).execute();
		getInstances();
		return this;
	}

	public OpenStack releaseAddress(final IPAddress ipAddress) throws ServerErrorExeception, IOException, IOException {
		new ReleaseAddressCommand(this, ipAddress).execute();
		getIPAddresses();
		return this;
	}

	public Instance runInstance(final Image image, final KeyPair keyPair, final String instanceType, final String addressingType, final String minCount, final String maxCount, final SecurityGroup... groups) throws ServerErrorExeception, IOException {
		final Instance i = new RunInstancesCommand(this, image, keyPair, instanceType, addressingType, minCount, maxCount, groups).execute().get(0);
		instanceCache.put(i.getKey(), i);
		return i;
	}

	public OpenStack terminateInstance(final Instance anInstance) throws ServerErrorExeception, IOException {
		new TerminateInstancesCommand(this, anInstance).execute();
		getInstances();
		return this;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("uri", uri).append("credentials", credentials).append("signatureStrategy", signatureStrategy);
		return builder.toString();
	}

}
