package com.cpn.os4j.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class IPAddresses {

	@JsonProperty("public")
	List<IPAddress> publicAddresses;
	@JsonProperty("private")
	List<IPAddress> privateAddresses;
	
}
