package com.cpn.os4j;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.ehcache.CacheManager;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.LoggerFactory;

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
import com.cpn.os4j.command.DescribeAvailabilityZonesCommand;
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
import com.cpn.os4j.command.ServerErrorException;
import com.cpn.os4j.command.TerminateInstancesCommand;
import com.cpn.os4j.model.AvailabilityZone;
import com.cpn.os4j.model.IPAddress;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.Instance;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.Region;
import com.cpn.os4j.model.SecurityGroup;
import com.cpn.os4j.model.Snapshot;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.Volume.VolumeAttachment;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class OpenStackEndPoint implements EndPoint {

	private final CacheManager cacheManager = new CacheManager();

	private final Credentials credentials;

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

	private CacheWrapper<String, AvailabilityZone> availabilityZonesCache = new EhcacheWrapper<>("availabilityZones", cacheManager);

	public OpenStackEndPoint(final URI aUrl, final Credentials aCreds) throws ServerErrorException, IOException {
		uri = aUrl;
		credentials = aCreds;
		try{
			populateCaches();
		}catch(HttpHostConnectException e){
			LoggerFactory.getLogger(OpenStackEndPoint.class).error(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#allocateIPAddress()
	 */
	@Override
	public IPAddress allocateIPAddress() throws ServerErrorException, IOException, IOException {
		final List<IPAddress> results = new AllocateAddressCommand(this).execute();
		ipAddessCache.put(results.get(0).getKey(), results.get(0));
		return results.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#associateAddress(com.cpn.os4j.model.Instance,
	 * com.cpn.os4j.model.IPAddress)
	 */
	@Override
	public EndPoint associateAddress(final Instance anInstance, final IPAddress anIPAddress) throws ServerErrorException, IOException {
		new AssociateAddressCommand(this, anInstance, anIPAddress).execute();
		anInstance.setIPAddress(anIPAddress.getIpAddress());
		anIPAddress.setInstanceId(anInstance.getInstanceId());
		getInstances();
		getIPAddresses();
		return this;
	}
	
	@Override
	public EndPoint associateAddress(final Instance anInstance, final String anIPAddress) throws ServerErrorException, IOException {
		new AssociateAddressCommand(this, anInstance, anIPAddress).execute();
		anInstance.setIPAddress(anIPAddress);
		//anIPAddress.setInstanceId(anInstance.getInstanceId());
		getInstances();
		getIPAddresses();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cpn.os4j.EndPoint#attachVolumeToInstance(com.cpn.os4j.model.Volume,
	 * com.cpn.os4j.model.Instance, java.lang.String)
	 */
	@Override
	public VolumeAttachment attachVolumeToInstance(final Volume aVolume, final Instance anInstance, final String aDevice) throws ServerErrorException, IOException {
		final VolumeAttachment v = new AttachVolumeCommand(this, aVolume, anInstance, aDevice).execute().get(0).addToVolume(aVolume);
		getVolumes();
		getInstances();
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cpn.os4j.EndPoint#createSnapshotFromVolume(com.cpn.os4j.model.Volume)
	 */
	@Override
	public Snapshot createSnapshotFromVolume(final Volume aVolume) throws ServerErrorException, IOException {
		final Snapshot s = new CreateSnapshotCommand(this, aVolume).execute().get(0);
		getSnapshots();
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#createVolume(java.lang.String, int)
	 */
	@Override
	public Volume createVolume(final String anAvailabilityZone, final int aSize) throws ServerErrorException, IOException {
		final Volume v = new CreateVolumeCommand(this, anAvailabilityZone, aSize).execute().get(0);
		getVolumes();
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cpn.os4j.EndPoint#createVolumeFromSnapshot(com.cpn.os4j.model.Snapshot,
	 * java.lang.String)
	 */
	@Override
	public Volume createVolumeFromSnapshot(final Snapshot aSnapshot, final String anAvailabilityZone) throws ServerErrorException, IOException {
		final Volume v = new CreateVolumeCommand(this, anAvailabilityZone, Integer.parseInt(aSnapshot.getVolumeSize()), aSnapshot).execute().get(0);
		getVolumes();
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#deleteSnapshot(com.cpn.os4j.model.Snapshot)
	 */
	@Override
	public EndPoint deleteSnapshot(final Snapshot snapshot) throws ServerErrorException, IOException {
		new DeleteSnapshot(this, snapshot).execute();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#deleteVolume(com.cpn.os4j.model.Volume)
	 */
	@Override
	public EndPoint deleteVolume(final Volume aVolume) throws ServerErrorException, IOException {
		new DeleteVolumeCommand(this, aVolume).execute();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#detachVolume(com.cpn.os4j.model.Volume)
	 */
	@Override
	public EndPoint detachVolume(final Volume aVolume) throws ServerErrorException, IOException {
		new DetachVolumeCommand(this, aVolume).execute();
		getVolumes();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cpn.os4j.EndPoint#disassociateAddress(com.cpn.os4j.model.IPAddress)
	 */
	@Override
	public EndPoint disassociateAddress(final String ipAddress) throws ServerErrorException, IOException {
		new DisassociateAddressCommand(this, ipAddress).execute();
		getInstances();
		getIPAddresses();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#forceDetachVolume(com.cpn.os4j.model.Volume)
	 */
	@Override
	public EndPoint forceDetachVolume(final Volume aVolume) throws ServerErrorException, IOException {
		new DetachVolumeCommand(this, aVolume, true).execute();
		getVolumes();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getCredentials()
	 */
	@Override
	public Credentials getCredentials() {
		return credentials;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getImages()
	 */
	@Override
	public List<Image> getImages() throws ServerErrorException, IOException, IOException {
		final List<Image> results = new DescribeImagesCommand(this).execute();
		imagesCache.removeAll().putAll(results);
		return results;
	}

	@Override
	public List<AvailabilityZone> getAvailabilityZones() throws IOException {
		final List<AvailabilityZone> results = new DescribeAvailabilityZonesCommand(this).execute();
		availabilityZonesCache.removeAll().putAll(results);
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getInstances()
	 */
	@Override
	public List<Instance> getInstances() throws ServerErrorException, IOException, IOException {
		final List<Instance> results = new DescribeInstancesCommand(this).execute();
		instanceCache.removeAll().putAll(results);
		return results;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getIPAddresses()
	 */
	@Override
	public List<IPAddress> getIPAddresses() throws ServerErrorException, IOException, IOException {
		final List<IPAddress> results = new DescribeAddressesCommand(this).execute();
		ipAddessCache.removeAll().putAll(results);
		return results;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getKeyPairs()
	 */
	@Override
	public List<KeyPair> getKeyPairs() throws ServerErrorException, IOException, IOException {
		final List<KeyPair> results = new DescribeKeyPairsCommand(this).execute();
		keyPairsCache.removeAll().putAll(results);
		return results;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getRegions()
	 */
	@Override
	public List<Region> getRegions() throws ServerErrorException, IOException, IOException {
		final List<Region> results = new DescribeRegionsCommand(this).execute();
		regionCache.removeAll().putAll(results);
		return results;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getSecurityGroups()
	 */
	@Override
	public List<SecurityGroup> getSecurityGroups() throws ServerErrorException, IOException, IOException {
		final List<SecurityGroup> results = new DescribeSecurityGroupsCommand(this).execute();
		securityGroupCache.removeAll().putAll(results);
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getSignatureStrategy()
	 */
	@Override
	public SignatureStrategy getSignatureStrategy() {
		return signatureStrategy;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getSnapshots()
	 */
	@Override
	public List<Snapshot> getSnapshots() throws ServerErrorException, IOException, IOException {
		final List<Snapshot> results = new DescribeSnapshotsCommand(this).execute();
		snapshotsCache.removeAll().putAll(results);
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getURI()
	 */
	@Override
	public URI getURI() {
		return uri;
	}

	private CacheWrapper<String, Volume> getVolumeCache() {
		return volumeCache;
	}

	@Override
	public List<IPAddress> listIPAddresses() {
		List<IPAddress> addresses = new ArrayList<>();
		for(String key : ipAddessCache.getKeys()){
			addresses.add(ipAddessCache.get(key));
		}
		return addresses;
	}
	
	@Override
	public List<Volume> listVolumes() {
		List<Volume> volumes = new ArrayList<>();
		for(String key : getVolumeCache().getKeys()){
			volumes.add(getVolumeCache().get(key));
		}
		return volumes;
	}
	
	@Override
	public List<Instance> listInstances() {
		List<Instance> instances = new ArrayList<>();
		for(String key : instanceCache.getKeys()){
			instances.add(instanceCache.get(key));
		}
		return instances;
	}
	
	@Override
	public List<Image> listImages() {
		List<Image> images = new ArrayList<>();
		for(String key : imagesCache.getKeys()){
			images.add(imagesCache.get(key));
		}
		return images;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#getVolumes()
	 */
	@Override
	public List<Volume> getVolumes() throws ServerErrorException, IOException, IOException {
		final List<Volume> results = new DescribeVolumesCommand(this).execute();
		getVolumeCache().removeAll().putAll(results);
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#populateCaches()
	 */
	@Override
	public EndPoint populateCaches() throws ServerErrorException, IOException {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#rebootInstance(com.cpn.os4j.model.Instance)
	 */
	@Override
	public EndPoint rebootInstance(final Instance instance) throws ServerErrorException, IOException {
		new RebootInstancesCommand(this, instance).execute();
		getInstances();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#releaseAddress(com.cpn.os4j.model.IPAddress)
	 */
	@Override
	public EndPoint releaseAddress(final IPAddress ipAddress) throws ServerErrorException, IOException, IOException {
		new ReleaseAddressCommand(this, ipAddress).execute();
		getIPAddresses();
		return this;
	}

	@Override
	public Instance runInstance(Image image, KeyPair keyPair, String instanceType, String addressingType, int minCount, int maxCount, AvailabilityZone anAvailabilityZone, String aUserData, SecurityGroup... groups) throws ServerErrorException, IOException {
		return runInstance(image, keyPair, instanceType, addressingType, minCount, maxCount, anAvailabilityZone.getName(), aUserData, groups);
	}

	@Override
	public Instance runInstance(final Image image, final KeyPair keyPair, final String instanceType, final String addressingType, final int minCount, final int maxCount, final String anAvailabilityZone, final String aUserData, final SecurityGroup... groups)
			throws ServerErrorException, IOException {
		final RunInstancesCommand i = new RunInstancesCommand(this, image, keyPair, instanceType, addressingType, minCount, maxCount, anAvailabilityZone, groups);
		i.setUserData(aUserData);
		Instance instance = i.execute().get(0);

		instanceCache.put(instance.getKey(), instance);
		return instance;
	}

	@Override
	public Volume createVolume(AvailabilityZone anAvailabilityZone, int aSize) throws ServerErrorException, IOException {
		return createVolume(anAvailabilityZone.getName(), aSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#terminateInstance(com.cpn.os4j.model.Instance)
	 */
	@Override
	public EndPoint terminateInstance(final Instance anInstance) throws ServerErrorException, IOException {
		new TerminateInstancesCommand(this, anInstance).execute();
		getInstances();
		return this;
	}

	@Override
	public Image getImageByLocation(String anImageId) throws ServerErrorException, IOException {
		for (Image i : listImages()) {
			if (anImageId.equalsIgnoreCase(i.getImageLocation())) {
				return i;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cpn.os4j.EndPoint#toString()
	 */
	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("uri", uri).append("credentials", credentials).append("signatureStrategy", signatureStrategy);
		return builder.toString();
	}

	@Override
	public Volume getVolume(final String aKey) throws ServerErrorException, IOException {
		try {
			return Iterables.find(listVolumes(), new com.google.common.base.Predicate<Volume>() {

				@Override
				public boolean apply(Volume aVolume) {
					return aVolume.getKey().equals(aKey);
				}

			});
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public Instance getInstance(final String instanceId) throws ServerErrorException, IOException {
		try {
			return Iterables.find(listInstances(), new Predicate<Instance>() {
				@Override
				public boolean apply(Instance anInstance) {
					return anInstance.getKey().equals(instanceId);
				}
			});
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public Image getImage(final String imageId) throws ServerErrorException, IOException {
		try {
			return Iterables.find(listImages(), new Predicate<Image>() {
				@Override
				public boolean apply(Image anImage) {
					return anImage.getKey().equals(imageId);
				}
			});
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public IPAddress getIPAddress(final String ipAddress) throws ServerErrorException, IOException {
		try {
			return Iterables.find(listIPAddresses(), new Predicate<IPAddress>() {
				@Override
				public boolean apply(IPAddress anIpAddress) {
					return anIpAddress.getKey().equals(ipAddress);
				}
			});
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public Snapshot getSnapshot(final String key) throws ServerErrorException, IOException {
		try {
			return Iterables.find(getSnapshots(), new Predicate<Snapshot>() {
				@Override
				public boolean apply(Snapshot anIpAddress) {
					return anIpAddress.getKey().equals(key);
				}
			});
		} catch (NoSuchElementException e) {
			return null;
		}
	}

}
