package com.cpn.os4j;

import com.cpn.os4j.model.Token;

public class PlatformEndpointFactory {
	
	public static ComputeEndpoint createComputeEndpoint(final String aServerUrl, final Token aToken){
		String openstackVersion="folsom";
		switch(openstackVersion){
		case "essex":
			return new EssexComputeEndpoint(aServerUrl, aToken);
		case "folsom":
			return new FolsomComputeEndpoint(aServerUrl, aToken);
		}
		return null;
	}
}
