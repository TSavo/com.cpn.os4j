package com.cpn.os4j.model;

import java.io.IOException;

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
public class Snapshot implements Cacheable<String> {
	private String status, displayName, description, volumeId, displayDescription, volumeSize, progress, startTime, ownerId, snapshotId;

	private OpenStack endPoint;

	
	public Volume createVolume(String anAvailabilityZone) throws ServerErrorExeception, IOException {
		return endPoint.createVolumeFromSnapshot(this, anAvailabilityZone);
	}
	
	public Snapshot delete() throws ServerErrorExeception, IOException {
		endPoint.deleteSnapshot(this);
		return this;
	}
	
	public String getStatus() {
		return status;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public String getVolumeSize() {
		return volumeSize;
	}

	public String getProgress() {
		return progress;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	private Snapshot(OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	@Override
	public String getKey() {
		return getSnapshotId();
	}

	public static Snapshot unmarshall(Node aNode, OpenStack anEndPoint) {
		Snapshot s = new Snapshot(anEndPoint);
		XMLUtil x = new XMLUtil(aNode);
		try {
			s.status = x.get("status");
			s.displayName = x.get("displayName");
			s.description = x.get("description");
			s.volumeId = x.get("volumeId");
			s.displayDescription = x.get("displayDescription");
			s.volumeSize = x.get("volumeSize");
			s.progress = x.get("progress");
			s.startTime = x.get("startTime");
			s.ownerId = x.get("ownerId");
			s.snapshotId = x.get("snapshotId");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return s;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("status", status).append("displayName", displayName).append("description", description).append("volumeId", volumeId).append("displayDescription", displayDescription).append("volumeSize", volumeSize).append("progress", progress)
				.append("startTime", startTime).append("ownerId", ownerId).append("snapshotId", snapshotId);
		return builder.toString();
	}

	public Snapshot waitUntilAvailable() throws InterruptedException, ServerErrorExeception, IOException {
		if(!status.contains("available")){
			Thread.sleep(1000);
			endPoint.getSnapshots();
			return endPoint.getSnapshotCache().get(getKey());
		}
		return this;
	}

}
