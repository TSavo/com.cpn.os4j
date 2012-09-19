package com.cpn.os4j.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class EndPointDescription {
	List<Map<String, String>> endpoints;
	@JsonProperty("endpoints_links")
	List<String> endpointLinks;
	String name;
	String type;

	public List<String> getEndpointLinks() {
		return endpointLinks;
	}

	public List<Map<String, String>> getEndpoints() {
		return endpoints;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setEndpointLinks(final List<String> endpointLinks) {
		this.endpointLinks = endpointLinks;
	}

	public void setEndpoints(final List<Map<String, String>> endpoints) {
		this.endpoints = endpoints;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setType(final String type) {
		this.type = type;
	}

}
