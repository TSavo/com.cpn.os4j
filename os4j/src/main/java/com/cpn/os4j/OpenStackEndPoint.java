package com.cpn.os4j;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import net.sf.ehcache.CacheManager;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cpn.cache.CacheWrapper;
import com.cpn.cache.EhcacheWrapper;
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

public class OpenStackEndPoint implements EndPoint {

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

	public OpenStackEndPoint(final URI aUrl, final OpenStackCredentials aCreds) throws ServerErrorExeception, IOException {
		uri = aUrl;
		credentials = aCreds;
		populateCaches();
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#allocateIPAddress()
	 */
	@Override
	public IPAddress allocateIPAddress() throws ServerErrorExeception, IOException, IOException {
		final List<IPAddress> results = new AllocateAddressCommand(this).execute();
		ipAddessCache.put(results.get(0).getKey(), results.get(0));
		return results.get(0);
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#associateAddress(com.cpn.os4j.model.Instance, com.cpn.os4j.model.IPAddress)
	 */
	@Override
	public EndPoint associateAddress(final Instance anInstance, final IPAddress anIPAddress) throws ServerErrorExeception, IOException {
		new AssociateAddressCommand(this, anInstance, anIPAddress).execute();
		anInstance.setIPAddress(anIPAddress.getIpAddress());
		anIPAddress.setInstanceId(anInstance.getInstanceId());
		getInstances();
		getIPAddresses();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#attachVolumeToInstance(com.cpn.os4j.model.Volume, com.cpn.os4j.model.Instance, java.lang.String)
	 */
	@Override
	public VolumeAttachment attachVolumeToInstance(final Volume aVolume, final Instance anInstance, final String aDevice) throws ServerErrorExeception, IOException {
		final VolumeAttachment v = new AttachVolumeCommand(this, aVolume, anInstance, aDevice).execute().get(0).addToVolume(aVolume);
		getVolumes();
		return v;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#createSnapshotFromVolume(com.cpn.os4j.model.Volume)
	 */
	@Override
	public Snapshot createSnapshotFromVolume(final Volume aVolume) throws ServerErrorExeception, IOException {
		final Snapshot s = new CreateSnapshotCommand(this, aVolume).execute().get(0);
		getSnapshots();
		return s;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#createVolume(java.lang.String, int)
	 */
	@Override
	public Volume createVolume(final String anAvailabilityZone, final int aSize) throws ServerErrorExeception, IOException {
		final Volume v = new CreateVolumeCommand(this, anAvailabilityZone, aSize).execute().get(0);
		getVolumes();
		return v;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#createVolumeFromSnapshot(com.cpn.os4j.model.Snapshot, java.lang.String)
	 */
	@Override
	public Volume createVolumeFromSnapshot(final Snapshot aSnapshot, final String anAvailabilityZone) throws ServerErrorExeception, IOException {
		final Volume v = new CreateVolumeCommand(this, anAvailabilityZone, Integer.parseInt(aSnapshot.getVolumeSize()), aSnapshot).execute().get(0);
		getVolumes();
		return v;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#deleteSnapshot(com.cpn.os4j.model.Snapshot)
	 */
	@Override
	public EndPoint deleteSnapshot(final Snapshot snapshot) throws ServerErrorExeception, IOException {
		new DeleteSnapshot(this, snapshot).execute();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#deleteVolume(com.cpn.os4j.model.Volume)
	 */
	@Override
	public EndPoint deleteVolume(final Volume aVolume) throws ServerErrorExeception, IOException {
		new DeleteVolumeCommand(this, aVolume).execute();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#detachVolume(com.cpn.os4j.model.Volume)
	 */
	@Override
	public EndPoint detachVolume(final Volume aVolume) throws ServerErrorExeception, IOException {
		new DetachVolumeCommand(this, aVolume).execute();
		getVolumes();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#disassociateAddress(com.cpn.os4j.model.IPAddress)
	 */
	@Override
	public EndPoint disassociateAddress(final IPAddress ipAddress) throws ServerErrorExeception, IOException {
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

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#forceDetachVolume(com.cpn.os4j.model.Volume)
	 */
	@Override
	public EndPoint forceDetachVolume(final Volume aVolume) throws ServerErrorExeception, IOException {
		new DetachVolumeCommand(this, aVolume, true).execute();
		getVolumes();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getCredentials()
	 */
	@Override
	public OpenStackCredentials getCredentials() {
		return credentials;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getImages()
	 */
	@Override
	public List<Image> getImages() throws ServerErrorExeception, IOException, IOException {
		final List<Image> results = new DescribeImagesCommand(this).execute();
		imagesCache.removeAll().putAll(results);
		return results;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getImagsCache()
	 */
	@Override
	public CacheWrapper<String, Image> getImagsCache() {
		return imagesCache;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getInstanceCache()
	 */
	@Override
	public CacheWrapper<String, Instance> getInstanceCache() {
		return instanceCache;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getInstances()
	 */
	@Override
	public List<Instance> getInstances() throws ServerErrorExeception, IOException, IOException {
		final List<Instance> results = new DescribeInstancesCommand(this).execute();
		getInstanceCache().removeAll().putAll(results);
		return results;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getIPAddressCache()
	 */
	@Override
	public CacheWrapper<String, IPAddress> getIPAddressCache() {
		return ipAddessCache;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getIPAddresses()
	 */
	@Override
	public List<IPAddress> getIPAddresses() throws ServerErrorExeception, IOException, IOException {
		final List<IPAddress> results = new DescribeAddressesCommand(this).execute();
		getIPAddressCache().removeAll().putAll(results);
		return results;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getKeyPairCache()
	 */
	@Override
	public CacheWrapper<String, KeyPair> getKeyPairCache() {
		return keyPairsCache;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getKeyPairs()
	 */
	@Override
	public List<KeyPair> getKeyPairs() throws ServerErrorExeception, IOException, IOException {
		final List<KeyPair> results = new DescribeKeyPairsCommand(this).execute();
		keyPairsCache.removeAll().putAll(results);
		return results;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getRegionCache()
	 */
	@Override
	public CacheWrapper<String, Region> getRegionCache() {
		return regionCache;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getRegions()
	 */
	@Override
	public List<Region> getRegions() throws ServerErrorExeception, IOException, IOException {
		final List<Region> results = new DescribeRegionsCommand(this).execute();
		getRegionCache().removeAll().putAll(results);
		return results;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getSecurityGroupCache()
	 */
	@Override
	public CacheWrapper<String, SecurityGroup> getSecurityGroupCache() {
		return securityGroupCache;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getSecurityGroups()
	 */
	@Override
	public List<SecurityGroup> getSecurityGroups() throws ServerErrorExeception, IOException, IOException {
		final List<SecurityGroup> results = new DescribeSecurityGroupsCommand(this).execute();
		securityGroupCache.removeAll().putAll(results);
		return results;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getSignatureStrategy()
	 */
	@Override
	public SignatureStrategy getSignatureStrategy() {
		return signatureStrategy;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getSnapshotCache()
	 */
	@Override
	public CacheWrapper<String, Snapshot> getSnapshotCache() {
		return snapshotsCache;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getSnapshots()
	 */
	@Override
	public List<Snapshot> getSnapshots() throws ServerErrorExeception, IOException, IOException {
		final List<Snapshot> results = new DescribeSnapshotsCommand(this).execute();
		snapshotsCache.removeAll().putAll(results);
		return results;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getURI()
	 */
	@Override
	public URI getURI() {
		return uri;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getVolumeCache()
	 */
	@Override
	public CacheWrapper<String, Volume> getVolumeCache() {
		return volumeCache;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#getVolumes()
	 */
	@Override
	public List<Volume> getVolumes() throws ServerErrorExeception, IOException, IOException {
		final List<Volume> results = new DescribeVolumesCommand(this).execute();
		getVolumeCache().removeAll().putAll(results);
		return results;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#populateCaches()
	 */
	@Override
	public EndPoint populateCaches() throws ServerErrorExeception, IOException {
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

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#rebootInstance(com.cpn.os4j.model.Instance)
	 */
	@Override
	public EndPoint rebootInstance(final Instance instance) throws ServerErrorExeception, IOException {
		new RebootInstancesCommand(this, instance).execute();
		getInstances();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#releaseAddress(com.cpn.os4j.model.IPAddress)
	 */
	@Override
	public EndPoint releaseAddress(final IPAddress ipAddress) throws ServerErrorExeception, IOException, IOException {
		new ReleaseAddressCommand(this, ipAddress).execute();
		getIPAddresses();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#runInstance(com.cpn.os4j.model.Image, com.cpn.os4j.model.KeyPair, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.cpn.os4j.model.SecurityGroup)
	 */
	@Override
	public Instance runInstance(final Image image, final KeyPair keyPair, final String instanceType, final String addressingType, final String minCount, final String maxCount, final SecurityGroup... groups) throws ServerErrorExeception, IOException {
		final Instance i = new RunInstancesCommand(this, image, keyPair, instanceType, addressingType, minCount, maxCount, groups).execute().get(0);
		instanceCache.put(i.getKey(), i);
		return i;
	}
	
	@Override
	public Instance runInstance(final Image image, final KeyPair keyPair, final String instanceType, final String addressingType, final String minCount, final String maxCount, final String aUserData, final SecurityGroup... groups) throws ServerErrorExeception, IOException {
		final RunInstancesCommand i = new RunInstancesCommand(this, image, keyPair, instanceType, addressingType, minCount, maxCount, groups);
		i.setUserData(aUserData);
		Instance instance = i.execute().get(0);

		instanceCache.put(instance.getKey(), instance);
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#terminateInstance(com.cpn.os4j.model.Instance)
	 */
	@Override
	public EndPoint terminateInstance(final Instance anInstance) throws ServerErrorExeception, IOException {
		new TerminateInstancesCommand(this, anInstance).execute();
		getInstances();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.cpn.os4j.EndPoint#toString()
	 */
	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("uri", uri).append("credentials", credentials).append("signatureStrategy", signatureStrategy);
		return builder.toString();
	}

}
