package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Volume;

public class DeleteVolumeCommand extends AbstractOpenStackCommand<Object> {

	@Override
	public String getAction() {
		return "DeleteVolume";
	}

	@Override
	public Class<Object> getUnmarshallingClass() {
		return null;
	}

	@Override
	public String getUnmarshallingXPath() {
		return null;
	}

	public DeleteVolumeCommand(OpenStack anEndPoint, Volume aVolume) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
	}
}
