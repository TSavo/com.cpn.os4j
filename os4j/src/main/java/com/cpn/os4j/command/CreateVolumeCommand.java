package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Volume;

public class CreateVolumeCommand extends AbstractOpenStackCommand<Volume> {
	@Override
	public String getAction() {
		return "CreateVolume";
	}

	@Override
	public Class<Volume> getUnmarshallingClass() {
		return Volume.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//CreateVolumeResponse";
	}

	public CreateVolumeCommand(OpenStack anEndPoint, String anAvailabilityZone, int size) {
		super(anEndPoint);
		put("AvailabilityZone", anAvailabilityZone);
		put("Size", new Integer(size).toString());
	}

}
