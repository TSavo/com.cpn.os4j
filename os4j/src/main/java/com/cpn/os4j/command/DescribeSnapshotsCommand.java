package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Snapshot;

public class DescribeSnapshotsCommand extends AbstractOpenStackCommand<Snapshot> {
	public DescribeSnapshotsCommand(final OpenStack anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeSnapshots";
	}

	@Override
	public Class<Snapshot> getUnmarshallingClass() {
		return Snapshot.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//snapshotSet/item";
	}
}
