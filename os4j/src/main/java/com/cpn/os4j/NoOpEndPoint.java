package com.cpn.os4j;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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

public class NoOpEndPoint implements EndPoint {
	public NoOpEndPoint() {
		// TODO Auto-generated constructor stub
	}

	public NoOpEndPoint(URI uri, Credentials someCreds) {

	}

	@Override
	public List<Image> listImages() {
		return new ArrayList<Image>();
	}

	@Override
	public List<Instance> listInstances() {
		return new ArrayList<Instance>();
	}

	@Override
	public List<IPAddress> listIPAddresses() {
		return new ArrayList<IPAddress>();
	}

	@Override
	public List<Volume> listVolumes() {
		return new ArrayList<Volume>();
	}

	@Override
	public IPAddress allocateIPAddress() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint associateAddress(Instance anInstance, IPAddress anIPAddress) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VolumeAttachment attachVolumeToInstance(Volume aVolume, Instance anInstance, String aDevice) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Snapshot createSnapshotFromVolume(Volume aVolume) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volume createVolume(String anAvailabilityZone, int aSize) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volume createVolume(AvailabilityZone anAvailabilityZone, int aSize) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volume createVolumeFromSnapshot(Snapshot aSnapshot, String anAvailabilityZone) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint deleteSnapshot(Snapshot snapshot) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint deleteVolume(Volume aVolume) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint detachVolume(Volume aVolume) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint disassociateAddress(IPAddress ipAddress) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint forceDetachVolume(Volume aVolume) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Credentials getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Image> getImages() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AvailabilityZone> getAvailabilityZones() throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Instance> getInstances() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPAddress> getIPAddresses() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyPair> getKeyPairs() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Region> getRegions() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SecurityGroup> getSecurityGroups() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SignatureStrategy getSignatureStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Snapshot> getSnapshots() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Volume> getVolumes() throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint populateCaches() throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint rebootInstance(Instance instance) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint releaseAddress(IPAddress ipAddress) throws ServerErrorException, IOException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instance runInstance(Image image, KeyPair keyPair, String instanceType, String addressingType, int minCount, int maxCount, String anAvailabilityZone, String aUserData, SecurityGroup... groups) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instance runInstance(Image image, KeyPair keyPair, String instanceType, String addressingType, int minCount, int maxCount, AvailabilityZone anAvailabilityZone, String aUserData, SecurityGroup... groups) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndPoint terminateInstance(Instance anInstance) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImageByLocation(String anImageId) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volume getVolume(String key) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instance getInstance(String instanceId) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImage(String imageId) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPAddress getIPAddress(String ipAddress) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Snapshot getSnapshot(String key) throws ServerErrorException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
