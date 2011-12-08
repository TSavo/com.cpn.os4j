package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Instance;

public class DescribeInstancesCommand extends AbstractOpenStackCommand<Instance> {

	public DescribeInstancesCommand(final EndPoint anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeInstances";
	}

	@Override
	public Class<Instance> getUnmarshallingClass() {
		return Instance.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//instancesSet/item";
	}

}
