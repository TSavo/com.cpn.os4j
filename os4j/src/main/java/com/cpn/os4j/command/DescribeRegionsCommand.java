package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Region;

public class DescribeRegionsCommand extends AbstractOpenStackCommand<Region> {

	public DescribeRegionsCommand(OpenStack anOpenStack) {
		super(anOpenStack);
	}

	@Override
	public Class<Region> getUnmarshallingClass() {
		return Region.class;
	}
	
	@Override
	public String getUnmarshallingXPath() {
		return "//regionInfo/item";
	}
	@Override
	public String getAction() {
		return "DescribeRegions";
	}

}
