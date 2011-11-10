package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Region;

public class DescribeRegionsCommand extends AbstractOpenStackCommand<Region> {

	public DescribeRegionsCommand(final OpenStack anOpenStack) {
		super(anOpenStack);
	}

	@Override
	public String getAction() {
		return "DescribeRegions";
	}

	@Override
	public Class<Region> getUnmarshallingClass() {
		return Region.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//regionInfo/item";
	}

}
