package com.cpn.os4j;

import com.cpn.os4j.command.OpenStackCommand;

public interface SignatureStrategy {

	String getSignature(OpenStackCommand<?> aCommand);

	public String getSignatureMethod();

	public int getSignatureVersion();
}
