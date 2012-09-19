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

	public String getFingerprint() {
		return fingerprint;
	}

	public String getName() {
		return name;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setFingerprint(final String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPrivateKey(final String privateKey) {
		this.privateKey = privateKey;
	}

	public void setPublicKey(final String publicKey) {
		this.publicKey = publicKey;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("publicKey", publicKey).append("privateKey", privateKey).append("name", name).append("userId", userId).append("fingerprint", fingerprint);
		return builder.toString();
	}

}
