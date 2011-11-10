package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.IPAddress;
import com.cpn.os4j.model.Instance;

public class AssociateAddressCommand extends AbstractOpenStackCommand<Object> {
	public AssociateAddressCommand(final OpenStack anEndPoint, final Instance anInstance, final IPAddress anIpAddress) {
		super(anEndPoint);
		put("InstanceId", anInstance.getInstanceId());
		put("PublicIp", anIpAddress.getIpAddress());
	}

	@Override
	public String getAction() {
		return "AssociateAddress";
	}

	@Override
	public Class<Object> getUnmarshallingClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUnmarshallingXPath() {
		// TODO Auto-generated method stub
		return null;
	}
}
