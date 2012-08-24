package com.cpn.os4j.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class KeyPairResponse {

	List<KeyPairResponse> keypairs;
	KeyPair keypair;

	@JsonIgnore
	public List<KeyPair> getKeyPairList() {
		List<KeyPair> results = new ArrayList<>();
		for (KeyPairResponse r : keypairs) {
			results.add(r.getKeypair());
		}
		return results;
	}

	public List<KeyPairResponse> getKeypairs() {
		return keypairs;
	}

	public void setKeypairs(List<KeyPairResponse> keypairs) {
		this.keypairs = keypairs;
	}

	public KeyPair getKeypair() {
		return keypair;
	}

	public void setKeypair(KeyPair keypair) {
		this.keypair = keypair;
	}

}
