package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Volume;

public class DeleteVolumeCommand extends AbstractOpenStackCommand<Object> {

	public DeleteVolumeCommand(final EndPoint anEndPoint, final Volume aVolume) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
	}

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
}
