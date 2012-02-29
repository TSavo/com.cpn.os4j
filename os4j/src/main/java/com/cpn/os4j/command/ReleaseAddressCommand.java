package com.cpn.os4j.command;

import java.io.IOException;
import java.util.List;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.IPAddress;

public class ReleaseAddressCommand extends AbstractOpenStackCommand<Object> {

	private final IPAddress ipAddress;

	public ReleaseAddressCommand(final EndPoint anEndPoint, final IPAddress anAddress) {
		super(anEndPoint);
		ipAddress = anAddress;
	}

	@Override
	public List<Object> execute() throws ServerErrorException, IOException {
		put("PublicIp", ipAddress.getIpAddress());
		return super.execute();
	}

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
}
