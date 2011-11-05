package com.cpn.os4j.command;

import java.util.List;

import com.cpn.os4j.Instance;
import com.cpn.os4j.OpenStack;

public class DescribeInstancesCommand extends AbstractOpenStackCommand<Instance> {

	public DescribeInstancesCommand(OpenStack anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//instancesSet/item";
	}
	@Override
	public Class<Instance> getUnmarshallingClass() {
		return Instance.class;
	}
	
	@Override
	public String getAction() {
		return "DescribeInstances";
	}

}
