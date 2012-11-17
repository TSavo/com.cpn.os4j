package com.cpn.os4j.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cpn.os4j.ComputeEndpoint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Server extends AbstractOpenStackModel {

	private static final long serialVersionUID = 8308147727947512720L;
	String id;
	String name;
	String updated;
	String created;
	String hostId;
	String status;
	@JsonProperty("user_id")
	String userId;
	int progress;
	String accessIPv4;
	String accessIPv6;
	@JsonProperty("key_name")
	String keyName;
	@JsonProperty("tenant_id")
	String tenantId;
	Image image;
	Flavor flavor;
	String adminPass;
	IPAddresses addresses;
	@JsonProperty("config_drive")
	String configDrive;
	Map<String, String> metadata;
	@JsonProperty("OS-EXT-STS:power_state")
	String powerState;
	@JsonProperty("OS-EXT-STS:vm_state")
	String vmState;
	@JsonProperty("OS-DCF:diskConfig")
	String diskConfig;
	@JsonProperty("OS-EXT-STS:task_state")
	String taskState;

	@JsonIgnore
	ComputeEndpoint computeEndpoint;

	public Server associateIp(final IPAddress address) {
		return associateIp(address.getIp());
	}

	public Server associateIp(final String address) {
		getComputeEndpoint().setIpAddress(this.getId(), address);
		return getComputeEndpoint().associateIp(this.getId(), address);
	}

	public Server delete() {
		getComputeEndpoint().deleteServer(this);
		return this;
	}

	public void suspend() {
		getComputeEndpoint().suspendServer(this);
	}

	public void resume() {
		getComputeEndpoint().resumeServer(this);
	}

	public void pause() {
		getComputeEndpoint().pauseServer(this);
	}

	public void unpause() {
		getComputeEndpoint().unpauseServer(this);
	}

	public void reboot() {
		getComputeEndpoint().rebootServer(this, false);
	}

	public void hardReboot() {
		getComputeEndpoint().rebootServer(this, true);
	}

	public void rename(final String aName) {
		getComputeEndpoint().renameServer(this, aName);
		setName(aName);
	}

	public void terminate() {
		getComputeEndpoint().deleteServer(this);
	}

	@JsonIgnore
	public List<VolumeAttachment> getVolumeAttachments() {
		return getComputeEndpoint().listVolumeAttachments(this);
	}

	public VolumeAttachment attachVolume(final String aVolumeId, final String aDevice) {
		return getComputeEndpoint().attachVolume(getId(), aVolumeId, aDevice);
	}

	public void detachVolume(final String aVolumeAttachmentId) {
		getComputeEndpoint().detachVolume(getId(), aVolumeAttachmentId);
	}

	public String getAccessIPv4() {
		return accessIPv4;
	}

	public String getAccessIPv6() {
		return accessIPv6;
	}

	public IPAddresses getAddresses() {
		return addresses;
	}

	public String getAdminPass() {
		return adminPass;
	}

	public ComputeEndpoint getComputeEndpoint() {
		return computeEndpoint;
	}

	public String getConfigDrive() {
		return configDrive;
	}

	public String getCreated() {
		return created;
	}

	public String getDiskConfig() {
		return diskConfig;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public String getHostId() {
		return hostId;
	}

	public String getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public String getKeyName() {
		return keyName;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public String getName() {
		return name;
	}

	public String getPowerState() {
		return powerState;
	}

	public int getProgress() {
		return progress;
	}

	public String getStatus() {
		return status;
	}

	public String getTaskState() {
		return taskState;
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

	public String getVmState() {
		return vmState;
	}

	public void setAccessIPv4(final String accessIPv4) {
		this.accessIPv4 = accessIPv4;
	}

	public void setAccessIPv6(final String accessIPv6) {
		this.accessIPv6 = accessIPv6;
	}

	public void setAddresses(final IPAddresses addresses) {
		this.addresses = addresses;
	}

	public void setAdminPass(final String adminPass) {
		this.adminPass = adminPass;
	}

	public Server setComputeEndpoint(final ComputeEndpoint computeEndpoint) {
		this.computeEndpoint = computeEndpoint;
		return this;
	}

	public void setConfigDrive(final String configDrive) {
		this.configDrive = configDrive;
	}

	public void setCreated(final String created) {
		this.created = created;
	}

	public void setDiskConfig(final String diskConfig) {
		this.diskConfig = diskConfig;
	}

	public void setFlavor(final Flavor flavor) {
		this.flavor = flavor;
	}

	public void setHostId(final String hostId) {
		this.hostId = hostId;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setImage(final Image image) {
		this.image = image;
	}

	public void setKeyName(final String keyName) {
		this.keyName = keyName;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPowerState(final String powerState) {
		this.powerState = powerState;
	}

	public void setProgress(final int progress) {
		this.progress = progress;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setTaskState(final String taskState) {
		this.taskState = taskState;
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

	public void setVmState(final String vmState) {
		this.vmState = vmState;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("name", name).append("updated", updated).append("created", created).append("hostId", hostId).append("status", status).append("userId", userId).append("progress", progress).append("accessIPv4", accessIPv4)
				.append("accessIPv6", accessIPv6).append("keyName", keyName).append("tenantId", tenantId).append("image", image).append("flavor", flavor).append("adminPass", adminPass).append("addresses", addresses).append("configDrive", configDrive)
				.append("metadata", metadata).append("powerState", powerState).append("vmState", vmState).append("diskConfig", diskConfig).append("taskState", taskState);
		return builder.toString();
	}

	@JsonIgnore
	public Server waitUntilRunning(long aTimeout) throws InterruptedException {
		Server server = this;
		while (!("ACTIVE".equals(server.getStatus()))) {
			if (aTimeout < 0) {
				return server;
			}
			if ("ERROR".equals(server.getStatus())) {
				throw new RuntimeException("Error while waiting for an instance to become available. State is: " + server.getStatus());
			}
			Thread.sleep(1000);
			aTimeout -= 1000;
			server = getComputeEndpoint().getServerDetails(this);
		}
		return server;
	}

}
