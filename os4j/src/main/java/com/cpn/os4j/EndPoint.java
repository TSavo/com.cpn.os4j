package com.cpn.os4j;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.cpn.os4j.command.ServerErrorException;
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

public interface EndPoint {

	public abstract IPAddress allocateIPAddress() throws ServerErrorException,
			IOException, IOException;

	public abstract EndPoint associateAddress(final Instance anInstance,
			final IPAddress anIPAddress) throws ServerErrorException,
			IOException;

	public abstract VolumeAttachment attachVolumeToInstance(
			final Volume aVolume, final Instance anInstance,
			final String aDevice) throws ServerErrorException, IOException;

	public abstract Snapshot createSnapshotFromVolume(final Volume aVolume)
			throws ServerErrorException, IOException;

	public abstract Volume createVolume(final String anAvailabilityZone,
			final int aSize) throws ServerErrorException, IOException;
	public abstract Volume createVolume(final AvailabilityZone anAvailabilityZone,
			final int aSize) throws ServerErrorException, IOException;

	public abstract Volume createVolumeFromSnapshot(final Snapshot aSnapshot,
			final String anAvailabilityZone) throws ServerErrorException,
			IOException;

	public abstract EndPoint deleteSnapshot(final Snapshot snapshot)
			throws ServerErrorException, IOException;

	public abstract EndPoint deleteVolume(final Volume aVolume)
			throws ServerErrorException, IOException;

	public abstract EndPoint detachVolume(final Volume aVolume)
			throws ServerErrorException, IOException;

	public abstract EndPoint disassociateAddress(final IPAddress ipAddress)
			throws ServerErrorException, IOException;

	public abstract EndPoint forceDetachVolume(final Volume aVolume)
			throws ServerErrorException, IOException;

	public abstract Credentials getCredentials();

	public abstract List<Image> getImages() throws ServerErrorException,
			IOException, IOException;
	
	public abstract List<AvailabilityZone> getAvailabilityZones() throws ServerErrorException, IOException;



	public abstract List<Instance> getInstances() throws ServerErrorException,
			IOException, IOException;



	public abstract List<IPAddress> getIPAddresses()
			throws ServerErrorException, IOException, IOException;



	public abstract List<KeyPair> getKeyPairs() throws ServerErrorException,
			IOException, IOException;



	public abstract List<Region> getRegions() throws ServerErrorException,
			IOException, IOException;



	public abstract List<SecurityGroup> getSecurityGroups()
			throws ServerErrorException, IOException, IOException;

	public abstract SignatureStrategy getSignatureStrategy();



	public abstract List<Snapshot> getSnapshots() throws ServerErrorException,
			IOException, IOException;

	public abstract URI getURI();


	public abstract List<Volume> listVolumes();
	public abstract List<IPAddress> listIPAddresses();
	public abstract List<Instance> listInstances();
	public abstract List<Image> listImages();
	public abstract List<Volume> getVolumes() throws ServerErrorException,
			IOException, IOException;

	public abstract EndPoint populateCaches() throws ServerErrorException,
			IOException;

	public abstract EndPoint rebootInstance(final Instance instance)
			throws ServerErrorException, IOException;

	public abstract EndPoint releaseAddress(final IPAddress ipAddress)
			throws ServerErrorException, IOException, IOException;

	public abstract Instance runInstance(final Image image,
			final KeyPair keyPair, final String instanceType,
			final String addressingType, final int minCount,
			final int maxCount, final String anAvailabilityZone, String aUserData, final SecurityGroup... groups)
			throws ServerErrorException, IOException;
	
	public abstract Instance runInstance(final Image image,
			final KeyPair keyPair, final String instanceType,
			final String addressingType, final int minCount,
			final int maxCount, final AvailabilityZone anAvailabilityZone, String aUserData, final SecurityGroup... groups)
			throws ServerErrorException, IOException;
	
	public abstract EndPoint terminateInstance(final Instance anInstance)
			throws ServerErrorException, IOException;

	public abstract String toString();
	
	public abstract Image getImageByLocation(String anImageId) throws ServerErrorException, IOException;

	public abstract Volume getVolume(String key) throws ServerErrorException, IOException;

	public abstract Instance getInstance(String instanceId) throws ServerErrorException, IOException;

	public abstract Image getImage(String imageId) throws ServerErrorException, IOException;

	public abstract IPAddress getIPAddress(String ipAddress) throws ServerErrorException, IOException;

	public abstract Snapshot getSnapshot(String key) throws ServerErrorException, IOException;

	
}