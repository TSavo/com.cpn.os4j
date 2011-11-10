package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.SecurityGroup;

public class DescribeSecurityGroupsCommand extends AbstractOpenStackCommand<SecurityGroup> {

	public DescribeSecurityGroupsCommand(final OpenStack anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeSecurityGroups";
	}

	@Override
	public Class<SecurityGroup> getUnmarshallingClass() {
		return SecurityGroup.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//securityGroupInfo/item";
	}

}
