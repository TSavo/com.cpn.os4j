package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Volume;

public class DetachVolumeCommand extends AbstractOpenStackCommand<Object> {
	public DetachVolumeCommand(final EndPoint anEndPoint, final Volume aVolume) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
	}

	public DetachVolumeCommand(final EndPoint anEndPoint, final Volume aVolume, final boolean forceDetach) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
		put("Force", Boolean.toString(forceDetach));
	}

	@Override
	public String getAction() {
		return "DetachVolume";
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
