package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Snapshot;
import com.cpn.os4j.model.Volume;

public class CreateSnapshotCommand extends AbstractOpenStackCommand<Snapshot> {
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

	public CreateSnapshotCommand(OpenStack anEndPoint, Volume aVolume) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
	}
}
