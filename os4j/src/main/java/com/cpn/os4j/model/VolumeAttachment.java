package com.cpn.os4j.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class VolumeAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7786945784534758601L;
	String id;
	String volumeId;
	String device;

	public VolumeAttachment() {
	}

	public VolumeAttachment(String volumeId, String device) {
		super();
		this.volumeId = volumeId;
		this.device = device;
	}

	public String getDevice() {
		return device;
	}

	public String getId() {
		return id;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public void setDevice(final String device) {
		this.device = device;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setVolumeId(final String volumeId) {
		this.volumeId = volumeId;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("volumeId", volumeId).append("device", device);
		return builder.toString();
	}
}
