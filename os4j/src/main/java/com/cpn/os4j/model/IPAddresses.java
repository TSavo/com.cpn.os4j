package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IPAddresses implements Serializable {

	private static final long serialVersionUID = 1892397415639139533L;
	@JsonProperty("public")
	List<IPAddress> publicAddresses;
	@JsonProperty("private")
	List<IPAddress> privateAddresses;

	public List<IPAddress> getPrivateAddresses() {
		return privateAddresses;
	}

	public List<IPAddress> getPublicAddresses() {
		return publicAddresses;
	}

	public void setPrivateAddresses(final List<IPAddress> privateAddresses) {
		this.privateAddresses = privateAddresses;
	}

	public void setPublicAddresses(final List<IPAddress> publicAddresses) {
		this.publicAddresses = publicAddresses;
	}

	//TODO:: Remove these
	@JsonAnySetter
	public void anySetter(final String aKey, final Object anObject){
	
	}
	
	@JsonAnyGetter
	public Map<String, Object> anyGetter(final String key){
		return null;
	}
	
	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("publicAddresses", publicAddresses).append("privateAddresses", privateAddresses);
		return builder.toString();
	}

}
