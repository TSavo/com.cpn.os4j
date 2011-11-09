package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Snapshot;

public class DeleteSnapshot extends AbstractOpenStackCommand<Object> {

	@Override
	public String getAction() {
		return "DeleteSnapshot";
	}

	@Override
	public Class<Object> getUnmarshallingClass() {
		return null;
	}

	@Override
	public String getUnmarshallingXPath() {
		return null;
	}

	public DeleteSnapshot(OpenStack anEndPoint, Snapshot aSnapshot) {
		super(anEndPoint);
		put("SnapshotId", aSnapshot.getSnapshotId());
	}
}
