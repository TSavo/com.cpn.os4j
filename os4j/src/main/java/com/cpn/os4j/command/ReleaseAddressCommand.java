package com.cpn.os4j.command;

import java.util.List;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.IPAddress;

public class ReleaseAddressCommand extends AbstractOpenStackCommand<Object> {

	private IPAddress ipAddress;
	
	@Override
	public String getAction() {
		return "ReleaseAddress";
	}

	@Override
	public Class<Object> getUnmarshallingClass() {
		return null;
	}

	@Override
	public String getUnmarshallingXPath() {
		return null;
	}
	
	@Override
	public List<Object> execute() {
		put("PublicIp", ipAddress.getIpAddress());
		return super.execute();
	}

	public ReleaseAddressCommand(OpenStack anEndPoint, IPAddress anAddress) {
		super(anEndPoint);
		ipAddress = anAddress;
	}
}
