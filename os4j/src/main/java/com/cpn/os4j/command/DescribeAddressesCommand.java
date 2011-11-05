package com.cpn.os4j.command;

import java.util.List;

import com.cpn.os4j.IPAddress;
import com.cpn.os4j.OpenStack;

public class DescribeAddressesCommand extends AbstractOpenStackCommand<IPAddress> {

	public DescribeAddressesCommand(OpenStack anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeAddresses";
	}

	@Override
	public Class<?> getUnmarshallingClass() {
		return IPAddress.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//addressesSet/item";
	}

	
}
