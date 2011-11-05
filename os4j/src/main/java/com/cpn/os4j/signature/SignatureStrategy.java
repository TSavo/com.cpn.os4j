package com.cpn.os4j.signature;

import com.cpn.os4j.command.OpenStackCommand;

public interface SignatureStrategy {
	
	public String getSignatureMethod();
	
	public int getSignatureVersion();

	String getSignature(OpenStackCommand<?> aCommand);
}
