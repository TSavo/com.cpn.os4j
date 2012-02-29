package com.cpn.os4j.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.w3c.dom.Node;

import com.cpn.cache.Cacheable;
import com.cpn.os4j.EndPoint;
import com.cpn.os4j.command.ServerErrorException;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Volume implements Cacheable<String> {
	@Immutable
	public static class VolumeAttachment {
		public static VolumeAttachment unmarshall(final Node aNode, final EndPoint anEndPoint) {
			final VolumeAttachment v = new VolumeAttachment(anEndPoint);
			final XMLUtil x = new XMLUtil(aNode);
			try {
				v.status = x.get("status");
				v.attachTime = x.get("attachTime");
				v.instanceId = x.get("instanceId");
				v.volumeId = x.get("volumeId");
				v.deleteOnTermination = x.get("deleteOnTermination");
				v.device = x.get("device");
			} catch (final XPathExpressionException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			return v;
		}

		@JsonIgnore
		private final EndPoint endPoint;

		private String status, instanceId, volumeId, deleteOnTermination, device, attachTime;

		private VolumeAttachment(final EndPoint anEndPoint) {
			endPoint = anEndPoint;
		}

		public VolumeAttachment addToVolume(final Volume aVolume) {
			aVolume.addVolumeAttachment(this);
			return this;
		}

		public String getAttachTime() {
			return attachTime;
		}

		public String getDeleteOnTermination() {
			return deleteOnTermination;
		}

		public String getDevice() {
			return device;
		}

		@JsonIgnore
		public Instance getInstance() throws ServerErrorException, IOException {
			return endPoint.getInstance(instanceId);
		}

		public String getInstanceId() {
			return instanceId;
		}

		public String getStatus() {
			return status;
		}

		public String getVolumeId() {
			return volumeId;
		}

		@Override
		public String toString() {
			final ToStringBuilder builder = new ToStringBuilder(this);
			builder.append("status", status).append("instanceId", instanceId).append("volumeId", volumeId).append("deleteOnTermination", deleteOnTermination).append("device", device).append("attachTime", attachTime);
			return builder.toString();
		}

	}

	public static Volume unmarshall(final Node aNode, final EndPoint anEndPoint) {
		final Volume v = new Volume(anEndPoint);
		final XMLUtil x = new XMLUtil(aNode);
		try {
			v.status = x.get("status");
			v.availabilityZone = x.get("availabilityZone");
			v.displayName = x.get("displayName");
			v.volumeId = x.get("volumeId");
			v.displayDescription = x.get("displayDescription");
			v.snapshotId = x.get("snapshotId");
			v.createTime = x.get("createTime");
			v.size = x.get("size");
			for (final Node n : x.getList("attachmentSet/item")) {
				v.volumeAttachments.add(VolumeAttachment.unmarshall(n, anEndPoint));
			}
			for(Iterator<VolumeAttachment> i = v.volumeAttachments.iterator(); i.hasNext();){
				VolumeAttachment va = i.next();
				if(va.instanceId == null || "".equals(va.instanceId)){
					i.remove();
				}
			}
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return v;
	}

	@JsonIgnore
	private final EndPoint endPoint;

	private String status, availabilityZone, displayName, volumeId, displayDescription, snapshotId, size, createTime;

	private List<VolumeAttachment> volumeAttachments = new ArrayList<>();

	private Volume(final EndPoint anEndPoint) {
		endPoint = anEndPoint;
	}

	public Volume() {
		endPoint = null;
	}

	public Volume addVolumeAttachment(final VolumeAttachment anAttachment) {
		if ((anAttachment.status != null) && anAttachment.status.contains("attached")) {
			volumeAttachments.add(anAttachment);
		}
		return this;
	}

	public Volume attachToInstance(final Instance anInstance, final String aDevice) throws ServerErrorException, IOException {
		endPoint.attachVolumeToInstance(this, anInstance, aDevice);
		return this;
	}

	public Snapshot createSnapshot() throws ServerErrorException, IOException {
		return endPoint.createSnapshotFromVolume(this);
	}

	public Volume delete() throws ServerErrorException, IOException {
		endPoint.deleteVolume(this);
		return this;
	}

	public Volume detach() throws ServerErrorException, IOException {
		endPoint.detachVolume(this);
		return this;
	}

	public Volume forceDetach() throws ServerErrorException, IOException {
		endPoint.forceDetachVolume(this);
		return this;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Override
	@JsonIgnore
	public String getKey() {
		return volumeId;
	}

	public String getSize() {
		return size;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public String getStatus() {
		return status;
	}

	public List<VolumeAttachment> getVolumeAttachments() {
		return volumeAttachments;
	}

	public String getVolumeId() {
		return volumeId;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("status", status).append("availabilityZone", availabilityZone).append("displayName", displayName).append("volumeId", volumeId).append("displayDescription", displayDescription).append("snapshotId", snapshotId).append("size", size)
				.append("createTime", createTime).append("volumeAttachments", volumeAttachments);
		return builder.toString();
	}

	
	public Volume waitUntilAvailable(final long maxTimeToWait) throws InterruptedException, ServerErrorException, IOException {
		if (status.contains("available")) {
			return this;
		}
		if (status.contains("error")) {
			throw new RuntimeException("While waiting for the volume " + volumeId + ", we got a status of " + status);
		}
		if (maxTimeToWait < 0) {
			return this;
		}
		Thread.sleep(1000);
		endPoint.getVolumes();
		return endPoint.getVolume(getKey()).waitUntilAvailable(maxTimeToWait - 1000);
	}


	public Volume waitUntilDeleted(final long maxTimeToWait) throws InterruptedException, ServerErrorException, IOException {
		if (endPoint.getVolume(getKey()) != null) {
			Thread.sleep(1000);
			endPoint.getVolumes();
		} else {
			return this;
		}
		if (maxTimeToWait < 0) {
			return this;
		}
		return waitUntilDeleted(maxTimeToWait - 1000);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setVolumeAttachments(List<VolumeAttachment> volumeAttachments) {
		this.volumeAttachments = volumeAttachments;
	}
}
