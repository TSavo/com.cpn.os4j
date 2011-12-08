package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Volume;

public class DescribeVolumesCommand extends AbstractOpenStackCommand<Volume> {

	public DescribeVolumesCommand(final EndPoint anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeVolumes";
	}

	@Override
	public Class<Volume> getUnmarshallingClass() {
		return Volume.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//volumeSet/item";
	}

}
