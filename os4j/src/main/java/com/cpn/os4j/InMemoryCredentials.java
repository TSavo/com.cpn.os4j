package com.cpn.os4j;

public class InMemoryCredentials implements Credentials {

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
