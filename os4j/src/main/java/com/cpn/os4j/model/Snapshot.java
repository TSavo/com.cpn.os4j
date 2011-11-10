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
	public static Snapshot unmarshall(final Node aNode, final OpenStack anEndPoint) {
		final Snapshot s = new Snapshot(anEndPoint);
		final XMLUtil x = new XMLUtil(aNode);
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
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return s;
	}

	private final OpenStack endPoint;

	private String status, displayName, description, volumeId, displayDescription, volumeSize, progress, startTime, ownerId, snapshotId;

	private Snapshot(final OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	public Volume createVolume(final String anAvailabilityZone) throws ServerErrorExeception, IOException {
		return endPoint.createVolumeFromSnapshot(this, anAvailabilityZone);
	}

	public Snapshot delete() throws ServerErrorExeception, IOException {
		endPoint.deleteSnapshot(this);
		return this;
	}

	public String getDescription() {
		return description;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getKey() {
		return getSnapshotId();
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getProgress() {
		return progress;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getStatus() {
		return status;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public String getVolumeSize() {
		return volumeSize;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("status", status).append("displayName", displayName).append("description", description).append("volumeId", volumeId).append("displayDescription", displayDescription).append("volumeSize", volumeSize).append("progress", progress)
				.append("startTime", startTime).append("ownerId", ownerId).append("snapshotId", snapshotId);
		return builder.toString();
	}

	public Snapshot waitUntilAvailable() throws InterruptedException, ServerErrorExeception, IOException {
		if (!status.contains("available")) {
			Thread.sleep(1000);
			endPoint.getSnapshots();
			return endPoint.getSnapshotCache().get(getKey());
		}
		return this;
	}

}
