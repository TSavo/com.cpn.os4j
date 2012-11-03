package com.cpn.os4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cpn.os4j.command.HttpHeaderDelegate;
import com.cpn.os4j.command.RestCommand;
import com.cpn.os4j.model.Token;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.VolumeResponse;

public class VolumeEndpoint implements Serializable {

	private static final long serialVersionUID = -1493848236703532753L;
	String serverUrl;
	Token token;
	HttpHeaderDelegate headerDelegate;

	public VolumeEndpoint(final String aServerUrl, final Token aToken) {
		super();
		token = aToken;
		serverUrl = aServerUrl;
		headerDelegate=new CommonHttpHeaderDelegate(aToken);
	}

	public Volume createVolume(final String aName, final String aDescription, final long aSize, final Map<String, String> someMetadata, final String anAvilabilityZone) {
		final Volume v = new Volume();
		v.setDisplayName(aName);
		v.setDisplayDescription(aDescription);
		v.setSize(aSize);
		v.setMetadata(someMetadata);
		v.setAvailabilityZone(anAvilabilityZone);
		return createVolume(v);

	}

	public Volume createVolume(final Volume aVolume) {
		final RestCommand<Map<String, Volume>, VolumeResponse> command = new RestCommand<>(token);
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/volumes");
		final Map<String, Volume> map = new HashMap<>();
		map.put("volume", aVolume);
		command.setRequestModel(map);
		command.setResponseModel(VolumeResponse.class);
		return command.post().getVolume().setEndpoint(this);
	}

	public void deleteVolume(final String aVolumeId) {
		final RestCommand<String, String> command = new RestCommand<>(token);
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/volumes/" + aVolumeId);
		command.delete();
	}

	public void deleteVolume(final Volume aVolume) {
		deleteVolume(aVolume.getId());
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

	public Volume getVolume(final String anId) {
		final RestCommand<String, VolumeResponse> command = new RestCommand<>(token);
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/volumes/" + anId);
		command.setResponseModel(VolumeResponse.class);
		return command.get().getVolume();
	}

	public List<Volume> listVolumes() {
		final RestCommand<String, VolumeResponse> command = new RestCommand<>(token);
		command.setHeaderDelegate(headerDelegate);
		command.setPath(getServerUrl() + "/volumes");
		command.setResponseModel(VolumeResponse.class);
		return command.get().getVolumes();
	}

}