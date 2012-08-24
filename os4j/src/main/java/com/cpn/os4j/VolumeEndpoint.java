package com.cpn.os4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cpn.os4j.command.RestCommand;
import com.cpn.os4j.model.Token;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.VolumeResponse;

public class VolumeEndpoint implements Serializable {

	private static final long serialVersionUID = -1493848236703532753L;
	String serverUrl;
	Token token;

	public VolumeEndpoint(String aServerUrl, Token aToken) {
		super();
		token = aToken;
		serverUrl = aServerUrl;

	}

	public Token getToken() {
		return token;
	}

	public String getTenantId() {
		return token.getTenant().getId();
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public List<Volume> listVolumes() {
		RestCommand<String, VolumeResponse> command = new RestCommand<>(token);
		command.setPath(getServerUrl() + "/volumes");
		command.setResponseModel(VolumeResponse.class);
		return command.get().getVolumes();
	}

	public Volume createVolume(String aName, String aDescription, long aSize, Map<String, String> someMetadata, String anAvilabilityZone) {
		Volume v = new Volume();
		v.setDisplayName(aName);
		v.setDisplayDescription(aDescription);
		v.setSize(aSize);
		v.setMetadata(someMetadata);
		v.setAvailabilityZone(anAvilabilityZone);
		return createVolume(v);

	}

	public void deleteVolume(Volume aVolume) {
		deleteVolume(aVolume.getId());
	}

	public void deleteVolume(String aVolumeId) {
		RestCommand<String, String> command = new RestCommand<>(token);
		command.setPath(getServerUrl() + "/volumes/" + aVolumeId);
		command.delete();
	}

	public Volume createVolume(Volume aVolume) {
		RestCommand<Map<String, Volume>, VolumeResponse> command = new RestCommand<>(token);
		command.setPath(getServerUrl() + "/volumes");
		Map<String, Volume> map = new HashMap<>();
		map.put("volume", aVolume);
		command.setRequestModel(map);
		command.setResponseModel(VolumeResponse.class);
		return command.post().getVolume().setEndpoint(this);
	}

	public Volume getVolume(String anId) {
		RestCommand<String, VolumeResponse> command = new RestCommand<>(token);
		command.setPath(getServerUrl() + "/volumes/" + anId);
		command.setResponseModel(VolumeResponse.class);
		return command.get().getVolume();
	}

}