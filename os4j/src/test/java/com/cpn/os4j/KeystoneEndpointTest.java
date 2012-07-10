package com.cpn.os4j;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.Flavor;
import com.cpn.os4j.model.Image;

public class KeystoneEndpointTest {

	@Test
	public void testGetAccess() throws InterruptedException {
		OpenStackCredentials creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("vcgAdmin", "pr0t3ctth1s"), "VCGs"));
		creds.setAuth(new AuthenticationCredentials(new PasswordCredentials("vcgAdmin", "pr0t3ctth1s"), "VCGs"));
		IdentityEndpoint ep = new IdentityEndpoint("http://control.dev.intercloud.net:5000");
		Access access = ep.getAccess(creds);
		access.localhostHack = true;
		ComputeEndpoint cep = access.getComputeEndpoint("RegionOne", "publicURL");
		List<Image> images = cep.listImages();
		List<Flavor> flavors = cep.listFlavors();
		cep.createServer("test", images.get(0), flavors.get(0), null, null).waitUntilRunning(100000).delete();
	}
}
