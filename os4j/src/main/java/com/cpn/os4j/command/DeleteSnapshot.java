package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Snapshot;

public class DeleteSnapshot extends AbstractOpenStackCommand<Object> {

	public DeleteSnapshot(final EndPoint anEndPoint, final Snapshot aSnapshot) {
		super(anEndPoint);
		put("SnapshotId", aSnapshot.getSnapshotId());
	}

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
}
