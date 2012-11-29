package com.cpn.os4j.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AddRouterResponse implements Serializable{

	private static final long serialVersionUID = 1856917143970252116L;
	@JsonProperty("subnet_id")
	private String subnetId;
	@JsonProperty("port_id")
	private String portId;
	
	public AddRouterResponse(){}

	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public String getPortId() {
		return portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}
	
	@Override
    public String toString() {
        return new ToStringBuilder(this.getClass()).append("subnetId", subnetId).append("portId", portId).toString();
    }

}
