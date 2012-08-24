package com.cpn.os4j.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class KeyPair implements Serializable {

	private static final long serialVersionUID = 6453277373276696459L;
	@JsonProperty("public_key")
	String publicKey;
	@JsonProperty("private_key")
	String privateKey;
	String name;
	@JsonProperty("user_id")
	String userId;
	String fingerprint;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("publicKey", publicKey).append("privateKey", privateKey).append("name", name).append("userId", userId).append("fingerprint", fingerprint);
		return builder.toString();
	}

}
