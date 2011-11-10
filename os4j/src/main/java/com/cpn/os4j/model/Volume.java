package com.cpn.os4j.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.command.ServerErrorExeception;
import com.cpn.os4j.model.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Volume implements Cacheable<String> {
	private String status, availabilityZone, displayName, volumeId, displayDescription, snapshotId, size, createTime;

	private List<VolumeAttachment> volumeAttachments = new ArrayList<>();
	private OpenStack endPoint;

	private Volume(OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	public Volume delete() throws ServerErrorExeception, IOException  {
		endPoint.deleteVolume(this);
		return this;
	}

	public Volume waitUntilAvailable() throws InterruptedException, ServerErrorExeception, IOException  {
		if (status.contains("available")) {
			return this;
		}
		Thread.sleep(1000);
		endPoint.getVolumes();
		return endPoint.getVolumeCache().get(getKey()).waitUntilAvailable();
	}

	public Volume waitUntilDeleted() throws InterruptedException, ServerErrorExeception, IOException  {
		while (endPoint.getVolumeCache().get(getKey()) != null) {
			Thread.sleep(1000);
			endPoint.getVolumes();
		}
		return this;
	}

	public Snapshot createSnapshot() throws ServerErrorExeception, IOException  {
		return endPoint.createSnapshotFromVolume(this);
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("status", status).append("availabilityZone", availabilityZone).append("displayName", displayName).append("volumeId", volumeId).append("displayDescription", displayDescription).append("snapshotId", snapshotId).append("size", size)
				.append("createTime", createTime).append("volumeAttachments", volumeAttachments);
		return builder.toString();
	}

	public static Volume unmarshall(Node aNode, OpenStack anEndPoint) {
		Volume v = new Volume(anEndPoint);
		XMLUtil x = new XMLUtil(aNode);
		try {
			v.status = x.get("status");
			v.availabilityZone = x.get("availabilityZone");
			v.displayName = x.get("displayName");
			v.volumeId = x.get("volumeId");
			v.displayDescription = x.get("displayDescription");
			v.snapshotId = x.get("snapshotId");
			v.createTime = x.get("createTime");
			v.size = x.get("size");
			for (Node n : x.getList("attachmentSet/item")) {
				v.volumeAttachments.add(VolumeAttachment.unmarshall(n, anEndPoint));
			}
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return v;
	}

	public String getStatus() {
		return status;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getSize() {
		return size;
	}

	public List<VolumeAttachment> getVolumeAttachments() {
		return volumeAttachments;
	}

	public Volume attachToInstance(Instance anInstance, String aDevice) throws ServerErrorExeception, IOException  {
		endPoint.attachVolumeToInstance(this, anInstance, aDevice);
		return this;
	}

	public Volume detach() throws ServerErrorExeception, IOException  {
		endPoint.detachVolume(this);
		return this;
	}
	
	public Volume forceDetach() throws ServerErrorExeception, IOException {
		endPoint.forceDetachVolume(this);
		return this;
	}

	public Volume addVolumeAttachment(VolumeAttachment anAttachment) {
		if (anAttachment.status != null && anAttachment.status.contains("attached")) {
			volumeAttachments.add(anAttachment);
		}
		return this;
	}

	@Override
	public String getKey() {
		return volumeId;
	}

	@Immutable
	public static class VolumeAttachment {
		private String status, instanceId, volumeId, deleteOnTermination, device, attachTime;
		private OpenStack endPoint;

		private VolumeAttachment(OpenStack anEndPoint) {
			endPoint = anEndPoint;
		}

		public static VolumeAttachment unmarshall(Node aNode, OpenStack anEndPoint) {
			VolumeAttachment v = new VolumeAttachment(anEndPoint);
			XMLUtil x = new XMLUtil(aNode);
			try {
				v.status = x.get("status");
				v.attachTime = x.get("attachTime");
				v.instanceId = x.get("instanceId");
				v.volumeId = x.get("volumeId");
				v.deleteOnTermination = x.get("deleteOnTermination");
				v.device = x.get("device");
			} catch (XPathExpressionException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			return v;
		}

		@Override
		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this);
			builder.append("status", status).append("instanceId", instanceId).append("volumeId", volumeId).append("deleteOnTermination", deleteOnTermination).append("device", device).append("attachTime", attachTime);
			return builder.toString();
		}

		public VolumeAttachment addToVolume(Volume aVolume) {
			aVolume.addVolumeAttachment(this);
			return this;
		}

		public String getStatus() {
			return status;
		}

		public String getAttachTime() {
			return attachTime;
		}

		public String getInstanceId() {
			return instanceId;
		}

		public Instance getInstance() {
			return endPoint.getInstanceCache().get(instanceId);
		}

		public String getVolumeId() {
			return volumeId;
		}

		public Volume getVolume() {
			return endPoint.getVolumeCache().get(volumeId);
		}

		public String getDeleteOnTermination() {
			return deleteOnTermination;
		}

		public String getDevice() {
			return device;
		}

	}
}
