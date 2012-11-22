package com.cpn.os4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cpn.apiomatic.logging.Logged;
import com.cpn.apiomatic.rest.HttpHeaderDelegate;
import com.cpn.apiomatic.rest.RestCommand;
import com.cpn.os4j.model.Flavor;
import com.cpn.os4j.model.FlavorsResponse;
import com.cpn.os4j.model.FullServerConfiguration;
import com.cpn.os4j.model.IPAddress;
import com.cpn.os4j.model.IPAddressPool;
import com.cpn.os4j.model.IPAddressMessage;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.ImagesResponse;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.KeyPairResponse;
import com.cpn.os4j.model.RebootRequest;
import com.cpn.os4j.model.SerializedFile;
import com.cpn.os4j.model.Server;
import com.cpn.os4j.model.ServerIPAddressConfiguration;
import com.cpn.os4j.model.ServerMessage;
import com.cpn.os4j.model.ServerNameConfiguration;
import com.cpn.os4j.model.Token;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.VolumeAttachment;
import com.cpn.os4j.model.VolumeAttachmentMessage;

public class ComputeEndpoint implements Serializable {

	private static final long serialVersionUID = -612911970872331536L;

	String serverUrl;
	Token token;
	HttpHeaderDelegate headerDelegate;

	public ComputeEndpoint(final String aServerUrl, final Token aToken) {
		super();
		token = aToken;
		serverUrl = aServerUrl;
		headerDelegate = new XAuthTokenHeaderDelegate(aToken);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getTenantId() {
		return token.getTenant().getId();
	}

	public Token getToken() {
		return token;
	}

	@Logged
	public IPAddress allocateIPAddress(final IPAddressPool aPool) {
		final RestCommand<Map<String, String>, IPAddressMessage> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/os-floating-ips");
		command.setResponseModel(IPAddressMessage.class);
		final Map<String, String> args = new HashMap<>();
		args.put("pool", aPool.getName());
		command.setRequestModel(args);
		return command.post().getIpAddress();
	}

	public Server associateIp(final Server aServer, final IPAddress anIpAddress) {
		return associateIp(aServer.getId(), anIpAddress.getIp());
	}

	public Server associateIp(final String aServerId, final String anIpAddress) {
		final Map<String, Map<String, String>> request = new HashMap<>();
		final Map<String, String> ip = new HashMap<>();
		ip.put("address", anIpAddress);
		request.put("addFloatingIp", ip);
		new RestCommand<Map<String, Map<String, String>>, String>(getServerUrl() + "/servers/" + aServerId + "/action", request, String.class, headerDelegate).post();
		return getServerDetails(aServerId);
	}

	public VolumeAttachment attachVolume(final Server aServer, final Volume aVolume, final String aDevice) {
		return attachVolume(aServer.getId(), aVolume.getId(), aDevice);
	}

	public VolumeAttachment attachVolume(final String aServerId, final String aVolumeId, final String aDevice) {
		return new RestCommand<VolumeAttachmentMessage, VolumeAttachmentMessage>(getServerUrl() + "/servers/" + aServerId + "/os-volume_attachments", new VolumeAttachmentMessage(new VolumeAttachment(aVolumeId, aDevice)), VolumeAttachmentMessage.class,
				headerDelegate).post().getVolumeAttachment();
	}

	public Server createServer(final String aName, final IPAddress anIpAddress, final Image anImage, final Flavor aFlavor, final Map<String, String> someMetadata, final List<SerializedFile> aPersonality, final String aKeyName, final String aUserData) {
		return createServer(aName, anIpAddress.getIp(), anImage.getSelfRef(), aFlavor.getSelfRef(), someMetadata, aPersonality, aKeyName, aUserData);
	}

	public Server createServer(final String aName, final String anIpAddress, final String anImageRef, final String aFlavorRef, Map<String, String> someMetadata, List<SerializedFile> aPersonality, final String aKeyName, final String aUserData) {
		if (someMetadata == null) {
			someMetadata = new HashMap<>();
		}
		if (aPersonality == null) {
			aPersonality = new ArrayList<>();
		}

		final RestCommand<Map<String, Object>, ServerMessage> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers");
		Map<String, Object> map = new HashMap<>();
		map.put("server", new FullServerConfiguration(aName, anIpAddress, anImageRef, aFlavorRef, someMetadata, aPersonality, aKeyName, aUserData));
		command.setRequestModel(map);
		command.setResponseModel(ServerMessage.class);
		return command.post().getServer().setComputeEndpoint(this);
	}

	public void deallocateIpAddress(final int anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/os-floating-ips/" + anId);
		command.delete();
	}

	public void deleteServer(final Server aServer) {
		deleteServer(aServer.getId());
	}

	public void deleteServer(final String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + anId);
		command.delete();
	}

	public void suspendServer(final String anId) {
		final RestCommand<Map<String, String>, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + anId + "/action");
		Map<String, String> map = new HashMap<>();
		map.put("suspend", "true");
		command.setRequestModel(map);
		command.put();
	}

	public void suspendServer(final Server aServer) {
		suspendServer(aServer.getId());
	}

	public void resumeServer(String anId) {
		final RestCommand<Map<String, String>, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + anId + "/action");
		Map<String, String> map = new HashMap<>();
		map.put("resume", "true");
		command.setRequestModel(map);
		command.put();
	}

	public void resumeServer(final Server aServer) {
		resumeServer(aServer.getId());
	}

	public void pauseServer(final String anId) {
		final RestCommand<Map<String, String>, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + anId + "/action");
		Map<String, String> map = new HashMap<>();
		map.put("pause", "true");
		command.setRequestModel(map);
		command.put();
	}

	public void pauseServer(final Server aServer) {
		suspendServer(aServer.getId());
	}

	public void unpauseServer(String anId) {
		final RestCommand<Map<String, String>, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + anId + "/action");
		Map<String, String> map = new HashMap<>();
		map.put("unpause", "true");
		command.setRequestModel(map);
		command.put();
	}

	public void unpauseServer(final Server aServer) {
		resumeServer(aServer.getId());
	}

	public IPAddress describeAddress(final int anId) {
		final RestCommand<String, IPAddressMessage> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/os-floating-ips/" + anId);
		command.setResponseModel(IPAddressMessage.class);
		return command.get().getIpAddress();
	}

	public void detachVolume(final Server aServer, final Volume aVolume) {
		detachVolume(aServer.getId(), aVolume.getId());
	}

	public void detachVolume(final String aServerId, final String aVolumeAttachmentId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + aServerId + "/os-volume_attachments/" + aVolumeAttachmentId);
		command.delete();
	}

	public IPAddressPool getIPAddressPoolByName(final String aName) {
		final List<IPAddressPool> list = listPools();
		for (final IPAddressPool p : list) {
			if (p.getName().equals(aName)) {
				return p;
			}
		}
		return null;
	}

	public Server getServerDetails(final Server aServer) {
		return getServerDetails(aServer.getId());
	}

	public Server getServerDetails(final String aServerId) {
		final RestCommand<String, ServerMessage> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + aServerId);
		command.setResponseModel(ServerMessage.class);
		return command.get().getServer().setComputeEndpoint(this);
	}

	public List<IPAddress> listAddresses() {
		return new RestCommand<Object, IPAddressMessage>(getServerUrl() + "/os-floating-ips", IPAddressMessage.class, headerDelegate).get().getIpAddresses();
	}

	public List<Flavor> listFlavors() {
		return new RestCommand<Object, FlavorsResponse>(getServerUrl() + "/flavors/detail", FlavorsResponse.class, headerDelegate).get().getFlavors();
	}

	public List<Image> listImages() {
		return new RestCommand<String, ImagesResponse>(getServerUrl() + "/images/detail", ImagesResponse.class, headerDelegate).get().getImages();

	}

	public List<KeyPair> listKeyPairs() {
		return new RestCommand<String, KeyPairResponse>(getServerUrl() + "/os-keypairs", KeyPairResponse.class, headerDelegate).get().getKeyPairList();
	}

	public List<IPAddressPool> listPools() {
		final List<IPAddressPool> pools = new RestCommand<String, IPAddressMessage>(getServerUrl() + "/os-floating-ip-pools", IPAddressMessage.class, headerDelegate).get().getPools();
		for (final IPAddressPool p : pools) {
			p.setComputeEndpoint(this);
		}
		return pools;
	}

	public List<Server> listServers() {
		final List<Server> servers = new RestCommand<Object, ServerMessage>(getServerUrl() + "/servers/detail", ServerMessage.class, headerDelegate).get().getServers();
		for (final Server s : servers) {
			s.setComputeEndpoint(this);
		}
		return servers;
	}

	public List<VolumeAttachment> listVolumeAttachments(final Server aServer) {
		return listVolumeAttachments(aServer.getId());
	}

	public List<VolumeAttachment> listVolumeAttachments(final String aServerId) {
		final RestCommand<String, VolumeAttachmentMessage> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + aServerId + "/os-volume_attachments");
		command.setResponseModel(VolumeAttachmentMessage.class);
		return command.get().getVolumeAttachments();
	}

	public Server rebootServer(final Server aServer, final boolean aHard) {
		return rebootServer(aServer.getId(), aHard);
	}

	public Server rebootServer(final String aServerId, final boolean aHard) {
		final RestCommand<RebootRequest, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + aServerId + "/action");
		command.setRequestModel(new RebootRequest(aHard ? RebootRequest.HARD : RebootRequest.SOFT));
		command.post();
		return getServerDetails(aServerId);
	}

	public Server renameServer(final Server aServer, final String aName) {
		return renameServer(aServer.getId(), aName);
	}

	public Server renameServer(final String aServerId, final String aName) {
		final RestCommand<Map<String, Object>, ServerMessage> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + aServerId);
		final ServerNameConfiguration config = new ServerNameConfiguration();
		config.setName(aName);
		Map<String, Object> map = new HashMap<>();
		map.put("server", config);
		command.setRequestModel(map);
		command.setResponseModel(ServerMessage.class);
		return command.put().getServer().setComputeEndpoint(this);
	}

	public Server setIpAddress(final String aServerId, final String anIPAddress) {
		final RestCommand<Map<String, Object>, ServerMessage> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "/servers/" + aServerId);
		final ServerIPAddressConfiguration config = new ServerIPAddressConfiguration();
		config.setAccessIPv4(anIPAddress);
		Map<String, Object> map = new HashMap<>();
		map.put("server", config);
		command.setRequestModel(map);
		command.setResponseModel(ServerMessage.class);
		return command.put().getServer().setComputeEndpoint(this);
	}

}
