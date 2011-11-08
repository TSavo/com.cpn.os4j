package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.Volume;

public class DescribeVolumesCommand extends AbstractOpenStackCommand<Volume> {

	public DescribeVolumesCommand(OpenStack anEndPoint) {
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
