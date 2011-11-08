package com.cpn.os4j;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@Immutable
public class Volume implements Cacheable<String> {
	private String status, availabilityZone, displayName, volumeId, displayDescription, snapshotId, size, createTime;

	private List<VolumeAttachment> volumeAttachments = new ArrayList<>();
	private OpenStack endPoint;

	private Volume(OpenStack anEndPoint) {
		endPoint = anEndPoint;
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

	@Override
	public String getKey() {
		return volumeId;
	}

}
