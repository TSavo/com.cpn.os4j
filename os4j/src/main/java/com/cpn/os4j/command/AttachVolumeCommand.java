package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Instance;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.Volume.VolumeAttachment;

public class AttachVolumeCommand extends AbstractOpenStackCommand<Volume.VolumeAttachment> {
	@Override
	public String getAction() {
		return "AttachVolume";
	}

	@Override
	public Class<VolumeAttachment> getUnmarshallingClass() {
		return Volume.VolumeAttachment.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//AttachVolumeResponse";
	}

	public AttachVolumeCommand(OpenStack anEndPoint, Volume aVolume, Instance anInstance, String aDevice) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
		put("InstanceId", anInstance.getInstanceId());
		put("Device", aDevice);
	}

}
