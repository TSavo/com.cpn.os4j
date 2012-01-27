package com.cpn.os4j;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.cpn.os4j.command.ServerErrorExeception;
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

	public abstract IPAddress allocateIPAddress() throws ServerErrorExeception,
			IOException, IOException;

	public abstract EndPoint associateAddress(final Instance anInstance,
			final IPAddress anIPAddress) throws ServerErrorExeception,
			IOException;

	public abstract VolumeAttachment attachVolumeToInstance(
			final Volume aVolume, final Instance anInstance,
			final String aDevice) throws ServerErrorExeception, IOException;

	public abstract Snapshot createSnapshotFromVolume(final Volume aVolume)
			throws ServerErrorExeception, IOException;

	public abstract Volume createVolume(final String anAvailabilityZone,
			final int aSize) throws ServerErrorExeception, IOException;
	public abstract Volume createVolume(final AvailabilityZone anAvailabilityZone,
			final int aSize) throws ServerErrorExeception, IOException;

	public abstract Volume createVolumeFromSnapshot(final Snapshot aSnapshot,
			final String anAvailabilityZone) throws ServerErrorExeception,
			IOException;

	public abstract EndPoint deleteSnapshot(final Snapshot snapshot)
			throws ServerErrorExeception, IOException;

	public abstract EndPoint deleteVolume(final Volume aVolume)
			throws ServerErrorExeception, IOException;

	public abstract EndPoint detachVolume(final Volume aVolume)
			throws ServerErrorExeception, IOException;

	public abstract EndPoint disassociateAddress(final IPAddress ipAddress)
			throws ServerErrorExeception, IOException;

	public abstract EndPoint forceDetachVolume(final Volume aVolume)
			throws ServerErrorExeception, IOException;

	public abstract Credentials getCredentials();

	public abstract List<Image> getImages() throws ServerErrorExeception,
			IOException, IOException;
	
	public abstract List<AvailabilityZone> getAvailabilityZones() throws ServerErrorExeception, IOException;



	public abstract List<Instance> getInstances() throws ServerErrorExeception,
			IOException, IOException;



	public abstract List<IPAddress> getIPAddresses()
			throws ServerErrorExeception, IOException, IOException;



	public abstract List<KeyPair> getKeyPairs() throws ServerErrorExeception,
			IOException, IOException;



	public abstract List<Region> getRegions() throws ServerErrorExeception,
			IOException, IOException;



	public abstract List<SecurityGroup> getSecurityGroups()
			throws ServerErrorExeception, IOException, IOException;

	public abstract SignatureStrategy getSignatureStrategy();



	public abstract List<Snapshot> getSnapshots() throws ServerErrorExeception,
			IOException, IOException;

	public abstract URI getURI();



	public abstract List<Volume> getVolumes() throws ServerErrorExeception,
			IOException, IOException;

	public abstract EndPoint populateCaches() throws ServerErrorExeception,
			IOException;

	public abstract EndPoint rebootInstance(final Instance instance)
			throws ServerErrorExeception, IOException;

	public abstract EndPoint releaseAddress(final IPAddress ipAddress)
			throws ServerErrorExeception, IOException, IOException;

	public abstract Instance runInstance(final Image image,
			final KeyPair keyPair, final String instanceType,
			final String addressingType, final int minCount,
			final int maxCount, final String anAvailabilityZone, String aUserData, final SecurityGroup... groups)
			throws ServerErrorExeception, IOException;
	
	public abstract Instance runInstance(final Image image,
			final KeyPair keyPair, final String instanceType,
			final String addressingType, final int minCount,
			final int maxCount, final AvailabilityZone anAvailabilityZone, String aUserData, final SecurityGroup... groups)
			throws ServerErrorExeception, IOException;
	
	public abstract EndPoint terminateInstance(final Instance anInstance)
			throws ServerErrorExeception, IOException;

	public abstract String toString();
	
	public abstract Image getImageByLocation(String anImageId) throws ServerErrorExeception, IOException;

	public abstract Volume getVolume(String key) throws ServerErrorExeception, IOException;

	public abstract Instance getInstance(String instanceId) throws ServerErrorExeception, IOException;

	public abstract Image getImage(String imageId) throws ServerErrorExeception, IOException;

	public abstract IPAddress getIPAddress(String ipAddress) throws ServerErrorExeception, IOException;

	public abstract Snapshot getSnapshot(String key) throws ServerErrorExeception, IOException;

	
}