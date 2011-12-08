package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.IPAddress;

public class DescribeAddressesCommand extends AbstractOpenStackCommand<IPAddress> {

	public DescribeAddressesCommand(final EndPoint anEndPoint) {
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
