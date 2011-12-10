package com.cpn.os4j.command;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Instance;
import com.cpn.os4j.model.Volume;
import com.cpn.os4j.model.Volume.VolumeAttachment;

public class AttachVolumeCommand extends AbstractOpenStackCommand<Volume.VolumeAttachment> {
	public AttachVolumeCommand(final EndPoint anEndPoint, final Volume aVolume, final Instance anInstance, final String aDevice) {
		super(anEndPoint);
		put("VolumeId", aVolume.getVolumeId());
		put("InstanceId", anInstance.getInstanceId());
		try {
			put("Device", URLEncoder.encode(aDevice, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

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

}
