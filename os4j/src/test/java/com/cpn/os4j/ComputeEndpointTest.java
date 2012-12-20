package com.cpn.os4j;

import org.junit.Before;
import org.junit.Test;

import com.cpn.os4j.model.Access;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ComputeEndpointTest {

	OpenStackCredentials creds = null;
	ServiceCatalog ep = null;
	Access access = null;
	ComputeEndpoint computeEndpoint = null;
	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void init(){
		creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("vscAdmin", "pr0t3ctth1s"), "VSC"));
		ep = new ServiceCatalog("http://192.168.31.38:5000", creds);
		access = ep.getAccess();
		access.localhostHack = true;
		computeEndpoint = access.getComputeEndpoint("RegionOne", "publicURL");
	}
	
	@Test
	public void testListFlavors() {
		computeEndpoint.listFlavors();
	}

}
