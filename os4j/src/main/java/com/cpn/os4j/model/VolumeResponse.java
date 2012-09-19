package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class VolumeResponse {

	Volume volume;
	List<Volume> volumes;

	public Volume getVolume() {
		return volume;
	}

	public List<Volume> getVolumes() {
		return volumes;
	}

	public void setVolume(final Volume volume) {
		this.volume = volume;
	}

	public void setVolumes(final List<Volume> volumes) {
		this.volumes = volumes;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("volume", volume).append("volumes", volumes);
		return builder.toString();
	}

}
