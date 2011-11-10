package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Snapshot;
import com.cpn.os4j.model.Volume;

public class CreateVolumeCommand extends AbstractOpenStackCommand<Volume> {
	public CreateVolumeCommand(final OpenStack anEndPoint, final String anAvailabilityZone, final int size) {
		super(anEndPoint);
		put("AvailabilityZone", anAvailabilityZone);
		put("Size", new Integer(size).toString());
	}

	public CreateVolumeCommand(final OpenStack anEndPoint, final String anAvailabilityZone, final int size, final Snapshot aSnapshot) {
		super(anEndPoint);
		put("AvailabilityZone", anAvailabilityZone);
		put("Size", new Integer(size).toString());
		put("SnapshotId", aSnapshot.getSnapshotId());
	}

	@Override
	public String getAction() {
		return "CreateVolume";
	}

	@Override
	public Class<Volume> getUnmarshallingClass() {
		return Volume.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//CreateVolumeResponse";
	}

}
