package com.cpn.os4j.command;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.IPAddress;

public class DescribeAddressesCommand extends AbstractOpenStackCommand<IPAddress> {

	public DescribeAddressesCommand(OpenStack anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeAddresses";
	}

	@Override
	public Class<IPAddress> getUnmarshallingClass() {
		return IPAddress.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//addressesSet/item";
	}

	
}
