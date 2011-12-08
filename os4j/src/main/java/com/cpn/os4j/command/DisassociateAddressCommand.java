package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.IPAddress;

public class DisassociateAddressCommand extends AbstractOpenStackCommand<Object> {
	public DisassociateAddressCommand(final EndPoint anEndPoint, final IPAddress anAddress) {
		super(anEndPoint);
		put("PublicIp", anAddress.getIpAddress());
	}

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
}
