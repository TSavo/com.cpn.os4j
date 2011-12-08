package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.IPAddress;

public class AllocateAddressCommand extends AbstractOpenStackCommand<IPAddress> {

	public AllocateAddressCommand(final EndPoint anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "AllocateAddress";
	}

	@Override
	public Class<IPAddress> getUnmarshallingClass() {
		return IPAddress.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//AllocateAddressResponse";
	}
}
