package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class IPAddresses implements Serializable {


	private static final long serialVersionUID = 1892397415639139533L;
	@JsonProperty("public")
	List<IPAddress> publicAddresses;
	@JsonProperty("private")
	List<IPAddress> privateAddresses;
	
}
