package com.cpn.os4j;

import java.io.Serializable;

public class InMemoryCredentials implements Credentials, Serializable {

	private static final long serialVersionUID = 5357883684947346046L;
	private String accessKey;
	private String secretKey;
	
	public InMemoryCredentials(String anAccessKey, String aSecretKey){
		accessKey = anAccessKey;
		secretKey = aSecretKey;
	}
	@Override
	public String getAccessKey() {
		return accessKey;
	}

	@Override
	public String getSecretKey() {
		return secretKey;
	}

}
