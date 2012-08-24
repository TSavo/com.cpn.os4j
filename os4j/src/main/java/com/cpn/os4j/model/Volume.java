package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.cpn.os4j.VolumeEndpoint;

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
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public List<VolumeAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<VolumeAttachment> volumeAttachments) {
		this.attachments = volumeAttachments;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("displayName", displayName).append("displayDescription", displayDescription).append("size", size).append("volumeType", volumeType).append("metadata", metadata).append("availabilityZone", availabilityZone)
				.append("snapshotId", snapshotId).append("attachments", attachments).append("createdAt", createdAt).append("status", status);
		return builder.toString();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public VolumeEndpoint getEndpoint() {
		return endpoint;
	}

	public Volume setEndpoint(VolumeEndpoint endpoint) {
		this.endpoint = endpoint;
		return this;
	}
}
