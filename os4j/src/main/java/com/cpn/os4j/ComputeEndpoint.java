package com.cpn.os4j;

import java.util.List;
import java.util.Map;

import com.cpn.os4j.model.Flavor;
import com.cpn.os4j.model.IPAddress;
import com.cpn.os4j.model.IPAddressPool;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.NetworkInterface;
import com.cpn.os4j.model.SerializedFile;
import com.cpn.os4j.model.Server;
import com.cpn.os4j.model.Token;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.VolumeAttachment;

public interface ComputeEndpoint {

	public abstract String getServerUrl();

	public abstract String getTenantId();

	public abstract Token getToken();

	public abstract IPAddress allocateIPAddress(IPAddressPool aPool);

	public abstract Server associateIp(Server aServer, IPAddress anIpAddress);

	public abstract Server associateIp(String aServerId, String anIpAddress);

	public abstract VolumeAttachment attachVolume(Server aServer, Volume aVolume, String aDevice);

	public abstract VolumeAttachment attachVolume(String aServerId, String aVolumeId, String aDevice);

	public abstract Server createServer(String aName, IPAddress anIpAddress, Image anImage, Flavor aFlavor, Map<String, String> someMetadata, List<SerializedFile> aPersonality, String aKeyName,
			String aUserData);

	public abstract Server createServer(String aName, String anIpAddress, String anImageRef, String aFlavorRef, Map<String, String> someMetadata, List<SerializedFile> aPersonality, String aKeyName,
			String aUserData);

	public abstract void deallocateIpAddress(int anId);

	public abstract void deleteServer(Server aServer);

	public abstract void deleteServer(String anId);

	public abstract void suspendServer(String anId);

	public abstract void suspendServer(Server aServer);

	public abstract void resumeServer(String anId);

	public abstract void resumeServer(Server aServer);

	public abstract void pauseServer(String anId);

	public abstract void pauseServer(Server aServer);

	public abstract void unpauseServer(String anId);

	public abstract void unpauseServer(Server aServer);

	public abstract IPAddress describeAddress(int anId);

	public abstract void detachVolume(Server aServer, Volume aVolume);

	public abstract void detachVolume(String aServerId, String aVolumeAttachmentId);

	public abstract IPAddressPool getIPAddressPoolByName(String aName);

	public abstract Server getServerDetails(Server aServer);

	public abstract Server getServerDetails(String aServerId);

	public abstract List<IPAddress> listAddresses();

	public abstract List<Flavor> listFlavors();

	public abstract List<Image> listImages();

	public abstract List<KeyPair> listKeyPairs();

	public abstract List<IPAddressPool> listPools();

	public abstract List<Server> listServers();

	public abstract List<VolumeAttachment> listVolumeAttachments(Server aServer);

	public abstract List<VolumeAttachment> listVolumeAttachments(String aServerId);

	public abstract Server rebootServer(Server aServer, boolean aHard);

	public abstract Server rebootServer(String aServerId, boolean aHard);

	public abstract Server renameServer(Server aServer, String aName);

	public abstract Server renameServer(String aServerId, String aName);

	public abstract Server setIpAddress(String aServerId, String anIPAddress);

	public abstract Server createServer(String aName, IPAddress anIpAddress, Image anImage, Flavor aFlavor, Map<String, String> someMetadata, List<SerializedFile> aPersonality, String aKeyName,
			String aUserData, Integer aMaxCount, Integer aMinCount, List<NetworkInterface> someNetworks);

	public abstract Server createServer(String aName, String anIpAddress, String anImageRef, String aFlavorRef, Map<String, String> someMetadata, List<SerializedFile> aPersonality, String aKeyName,
			String aUserData, Integer aMaxCount, Integer aMinCount, List<NetworkInterface> aNetworks);

}