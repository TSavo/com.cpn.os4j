package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cpn.os4j.VolumeEndpoint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Volume implements Serializable {

	private static final long serialVersionUID = 8146035415035777380L;
	String id;
	@JsonProperty("display_name")
	String displayName;
	@JsonProperty("display_description")
	String displayDescription;
	long size;
	@JsonProperty("volume_type")
	String volumeType;
	Map<String, String> metadata = new HashMap<>();
	@JsonProperty("availability_zone")
	String availabilityZone;
	@JsonProperty("snapshot_id")
	String snapshotId;

	List<VolumeAttachment> attachments = new ArrayList<>();
	@JsonProperty("created_at")
	String createdAt;

	String status;

	@JsonIgnore
	transient VolumeEndpoint endpoint;

	public List<VolumeAttachment> getAttachments() {
		return attachments;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public String getDisplayName() {
		return displayName;
	}

	public VolumeEndpoint getEndpoint() {
		return endpoint;
	}

	public String getId() {
		return id;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public long getSize() {
		return size;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public String getStatus() {
		return status;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setAttachments(final List<VolumeAttachment> volumeAttachments) {
		this.attachments = volumeAttachments;
	}

	public void setAvailabilityZone(final String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	public void setCreatedAt(final String createdAt) {
		this.createdAt = createdAt;
	}

	public void setDisplayDescription(final String displayDescription) {
		this.displayDescription = displayDescription;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public Volume setEndpoint(final VolumeEndpoint endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public void setSize(final long size) {
		this.size = size;
	}

	public void setSnapshotId(final String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setVolumeType(final String volumeType) {
		this.volumeType = volumeType;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("displayName", displayName).append("displayDescription", displayDescription).append("size", size).append("volumeType", volumeType).append("metadata", metadata).append("availabilityZone", availabilityZone)
				.append("snapshotId", snapshotId).append("attachments", attachments).append("createdAt", createdAt).append("status", status);
		return builder.toString();
	}
}
