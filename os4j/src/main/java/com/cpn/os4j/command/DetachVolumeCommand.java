package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Volume;

public class DetachVolumeCommand extends AbstractOpenStackCommand<Object> {
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

	public DetachVolumeCommand(OpenStack anEndPoint, Volume aVolume) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
	}
	
	public DetachVolumeCommand(OpenStack anEndPoint, Volume aVolume, boolean forceDetach) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
		put("Force", Boolean.toString(forceDetach));
	}
}
