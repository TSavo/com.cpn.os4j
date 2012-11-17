package com.cpn.os4j.model;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Image extends AbstractOpenStackModel {

	private static final long serialVersionUID = -4256144888881122608L;
	String id;
	String name;
	String updated;
	String created;
	@JsonProperty("tenant_id")
	String tenantId;
	@JsonProperty("user_id")
	String userId;
	String status;
	int progress;
	int minDisk;
	int minRam;
	Server server;
	Map<String, String> metadata;

	public String getCreated() {
		return created;
	}

	public String getId() {
		return id;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public int getMinDisk() {
		return minDisk;
	}

	public int getMinRam() {
		return minRam;
	}

	public String getName() {
		return name;
	}

	public int getProgress() {
		return progress;
	}

	public Server getServer() {
		return server;
	}

	public String getStatus() {
		return status;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getUpdated() {
		return updated;
	}

	public String getUserId() {
		return userId;
	}

	public void setCreated(final String created) {
		this.created = created;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public void setMinDisk(final int minDisk) {
		this.minDisk = minDisk;
	}

	public void setMinRam(final int minRam) {
		this.minRam = minRam;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setProgress(final int progress) {
		this.progress = progress;
	}

	public void setServer(final Server server) {
		this.server = server;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setTenantId(final String tenantId) {
		this.tenantId = tenantId;
	}

	public void setUpdated(final String updated) {
		this.updated = updated;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("links", links).append("name", name).append("updated", updated).append("created", created).append("tenantId", tenantId).append("userId", userId).append("status", status).append("progress", progress)
				.append("minDisk", minDisk).append("minRam", minRam).append("server", server).append("metadata", metadata);
		return builder.toString();
	}

}
