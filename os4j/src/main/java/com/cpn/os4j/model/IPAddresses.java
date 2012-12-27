package com.cpn.os4j.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IPAddresses implements Serializable {

	private static final long serialVersionUID = 1892397415639139533L;
	@JsonProperty("public")
	List<IPAddress> publicAddresses;
	@JsonProperty("private")
	List<IPAddress> privateAddresses;

	@JsonIgnore
	Map<String, List<InterfaceInfo>> anyInterface=new LinkedHashMap<>();
	
	final static ObjectMapper objectMapper = new ObjectMapper();
	
	@JsonAnySetter
	public void anySetter(final String aKey, final Object anObject){
		try {
			List<InterfaceInfo> interfaceInfos = Arrays.asList(objectMapper.readValue(objectMapper.writeValueAsString(anObject),InterfaceInfo[].class));
			anyInterface.put(aKey, interfaceInfos);
		} catch (Exception e) {
		}
	}
	
	@JsonAnyGetter
	public Map<String,List<InterfaceInfo>> anyGetter(){
		return anyInterface;
	}
	
	@JsonIgnore
	public InterfaceInfo getInterfaceInfo(String aName){
		return anyInterface.get(aName).get(0);
	}
	
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

	


	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("publicAddresses", publicAddresses).append("privateAddresses", privateAddresses);
		return builder.toString();
	}

}
