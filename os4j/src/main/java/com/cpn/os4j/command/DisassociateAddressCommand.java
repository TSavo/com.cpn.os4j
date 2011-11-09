package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.IPAddress;

public class DisassociateAddressCommand extends AbstractOpenStackCommand<Object> {
	@Override
	public String getAction() {
		return "DisassociateAddress";
	}

	@Override
	public Class<Object> getUnmarshallingClass() {
		return null;
	}

	@Override
	public String getUnmarshallingXPath() {
		return null;
	}

	public DisassociateAddressCommand(OpenStack anEndPoint, IPAddress anAddress) {
		super(anEndPoint);
		put("PublicIp", anAddress.getIpAddress());
	}
}
