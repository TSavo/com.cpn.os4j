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
import com.cpn.os4j.model.IPAddressResponse;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.ImagesResponse;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.KeyPairResponse;
import com.cpn.os4j.model.RebootRequest;
import com.cpn.os4j.model.SerializedFile;
import com.cpn.os4j.model.Server;
import com.cpn.os4j.model.ServerIPAddressConfiguration;
import com.cpn.os4j.model.ServerNameConfiguration;
import com.cpn.os4j.model.ServerResponse;
import com.cpn.os4j.model.ServersResponse;
import com.cpn.os4j.model.Token;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.VolumeAttachment;
import com.cpn.os4j.model.VolumeAttachmentResponse;

public class ComputeEndpoint implements Serializable {

	private static final long serialVersionUID = -612911970872331536L;

	String serverUrl;
	Token token;
	HttpHeaderDelegate headerDelegate;
	public ComputeEndpoint(final String aServerUrl, final Token aToken) {
		super();
		token = aToken;
		serverUrl = aServerUrl;
		headerDelegate=new XAuthTokenHeaderDelegate(aToken);
	}

	@Logged
	public IPAddress allocateIPAddress(final IPAddressPool aPool) {
		final RestCommand<Map<String, String>, IPAddressResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/os-floating-ips");
		command.setResponseModel(IPAddressResponse.class);
		final Map<String, String> args = new HashMap<>();
		args.put("pool", aPool.getName());
		command.setRequestModel(args);
		return command.post().getIpAddress();
	}

	public Server associateIp(final Server aServer, final IPAddress anIpAddress) {
		return associateIp(aServer.getId(), anIpAddress.getIp());
	}

	public Server associateIp(final String aServerId, final String anIpAddress) {
		final RestCommand<Map<String, Map<String, String>>, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + aServerId + "/action");
		final Map<String, Map<String, String>> request = new HashMap<>();
		final Map<String, String> ip = new HashMap<>();
		ip.put("address", anIpAddress);
		request.put("addFloatingIp", ip);
		command.setRequestModel(request);
		command.post();
		return getServerDetails(aServerId);
	}

	public VolumeAttachment attachVolume(final Server aServer, final Volume aVolume, final String aDevice) {
		return attachVolume(aServer.getId(), aVolume.getId(), aDevice);
	}

	public VolumeAttachment attachVolume(final String aServerId, final String aVolumeId, final String aDevice) {
		final RestCommand<Map<String, VolumeAttachment>, VolumeAttachmentResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + aServerId + "/os-volume_attachments");
		command.setResponseModel(VolumeAttachmentResponse.class);
		final Map<String, VolumeAttachment> args = new HashMap<>();
		final VolumeAttachment v = new VolumeAttachment();
		v.setDevice(aDevice);
		v.setVolumeId(aVolumeId);
		args.put("volumeAttachment", v);
		command.setRequestModel(args);
		return command.post().getVolumeAttachment();
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

		final RestCommand<Map<String, Object>, ServerResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers");
		Map<String, Object> map = new HashMap<>();
		map.put("server", new FullServerConfiguration(aName, anIpAddress, anImageRef, aFlavorRef, someMetadata, aPersonality, aKeyName, aUserData));
		command.setRequestModel(map);
		command.setResponseModel(ServerResponse.class);
		return command.post().getServer().setComputeEndpoint(this);
	}

	public void deallocateIpAddress(final int anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/os-floating-ips/" + anId);
		command.delete();
	}

	public void deleteServer(final Server aServer) {
		deleteServer(aServer.getId());
	}

	public void deleteServer(final String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + anId);
		command.delete();
	}

	public void suspendServer(final String anId) {
		final RestCommand<Map<String, String>, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + anId + "/action");
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
		command.setPath(getServerUrl() + "/servers/" + anId + "/action");
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
		command.setPath(getServerUrl() + "/servers/" + anId + "/action");
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
		command.setPath(getServerUrl() + "/servers/" + anId + "/action");
		Map<String, String> map = new HashMap<>();
		map.put("unpause", "true");
		command.setRequestModel(map);
		command.put();
	}

	public void unpauseServer(final Server aServer) {
		resumeServer(aServer.getId());
	}

	public IPAddress describeAddress(final int anId) {
		final RestCommand<String, IPAddressResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/os-floating-ips/" + anId);
		command.setResponseModel(IPAddressResponse.class);
		return command.get().getIpAddress();
	}

	public void detachVolume(final Server aServer, final Volume aVolume) {
		detachVolume(aServer.getId(), aVolume.getId());
	}

	public void detachVolume(final String aServerId, final String aVolumeAttachmentId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + aServerId + "/os-volume_attachments/" + aVolumeAttachmentId);
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
		final RestCommand<String, ServerResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + aServerId);
		command.setResponseModel(ServerResponse.class);
		return command.get().getServer().setComputeEndpoint(this);
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

	public List<IPAddress> listAddresses() {
		final RestCommand<String, IPAddressResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/os-floating-ips");
		command.setResponseModel(IPAddressResponse.class);
		return command.get().getIpAddresses();
	}

	public List<Flavor> listFlavors() {
		final RestCommand<String, FlavorsResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/flavors/detail");
		command.setResponseModel(FlavorsResponse.class);
		return command.get().getFlavors();
	}

	public List<Image> listImages() {
		final RestCommand<String, ImagesResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/images/detail");
		command.setResponseModel(ImagesResponse.class);
		return command.get().getImages();
	}

	public List<KeyPair> listKeyPairs() {
		final RestCommand<String, KeyPairResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/os-keypairs");
		command.setResponseModel(KeyPairResponse.class);
		return command.get().getKeyPairList();

	}

	public List<IPAddressPool> listPools() {
		final RestCommand<String, IPAddressResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/os-floating-ip-pools");
		command.setResponseModel(IPAddressResponse.class);
		final List<IPAddressPool> pools = command.get().getPools();
		for (final IPAddressPool p : pools) {
			p.setComputeEndpoint(this);
		}
		return pools;
	}

	public List<Server> listServers() {
		final RestCommand<String, ServersResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/detail");
		command.setResponseModel(ServersResponse.class);
		final List<Server> servers = command.get().getServers();
		for (final Server s : servers) {
			s.setComputeEndpoint(this);
		}
		return servers;
	}

	public List<VolumeAttachment> listVolumeAttachments(final Server aServer) {
		return listVolumeAttachments(aServer.getId());
	}

	public List<VolumeAttachment> listVolumeAttachments(final String aServerId) {
		final RestCommand<String, VolumeAttachmentResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + aServerId + "/os-volume_attachments");
		command.setResponseModel(VolumeAttachmentResponse.class);
		return command.get().getVolumeAttachments();
	}

	public Server rebootServer(final Server aServer, final boolean aHard) {
		return rebootServer(aServer.getId(), aHard);
	}

	public Server rebootServer(final String aServerId, final boolean aHard) {
		final RestCommand<RebootRequest, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + aServerId + "/action");
		command.setRequestModel(new RebootRequest(aHard ? RebootRequest.HARD : RebootRequest.SOFT));
		command.post();
		return getServerDetails(aServerId);
	}
	
	public Server renameServer(final Server aServer, final String aName){
		return renameServer(aServer.getId(), aName);
	}

	public Server renameServer(final String aServerId, final String aName) {
		final RestCommand<Map<String, Object>, ServerResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + aServerId);
		final ServerNameConfiguration config = new ServerNameConfiguration();
		config.setName(aName);
		Map<String, Object> map = new HashMap<>();
		map.put("server", config);
		command.setRequestModel(map);
		command.setResponseModel(ServerResponse.class);
		return command.put().getServer().setComputeEndpoint(this);
	}

	public Server setIpAddress(final String aServerId, final String anIPAddress) {
		final RestCommand<Map<String, Object>, ServerResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/servers/" + aServerId);
		final ServerIPAddressConfiguration config = new ServerIPAddressConfiguration();
		config.setAccessIPv4(anIPAddress);
		Map<String, Object> map = new HashMap<>();
		map.put("server", config);
		command.setRequestModel(map);
		command.setResponseModel(ServerResponse.class);
		return command.put().getServer().setComputeEndpoint(this);
	}

}
