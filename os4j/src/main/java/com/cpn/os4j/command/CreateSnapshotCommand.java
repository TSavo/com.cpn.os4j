package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Snapshot;
import com.cpn.os4j.model.Volume;

public class CreateSnapshotCommand extends AbstractOpenStackCommand<Snapshot> {
	public CreateSnapshotCommand(final OpenStack anEndPoint, final Volume aVolume) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
	}

	@Override
	public String getAction() {
		return "CreateSnapshot";
	}

	@Override
	public Class<Snapshot> getUnmarshallingClass() {
		return Snapshot.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//CreateSnapshotResponse";
	}
}
