package com.cpn.os4j.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class KeyPairResponse {

	List<KeyPairResponse> keypairs;
	KeyPair keypair;

	public KeyPair getKeypair() {
		return keypair;
	}

	@JsonIgnore
	public List<KeyPair> getKeyPairList() {
		final List<KeyPair> results = new ArrayList<>();
		for (final KeyPairResponse r : keypairs) {
			results.add(r.getKeypair());
		}
		return results;
	}

	public List<KeyPairResponse> getKeypairs() {
		return keypairs;
	}

	public void setKeypair(final KeyPair keypair) {
		this.keypair = keypair;
	}

	public void setKeypairs(final List<KeyPairResponse> keypairs) {
		this.keypairs = keypairs;
	}

}
