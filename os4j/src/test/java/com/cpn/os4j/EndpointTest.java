package com.cpn.os4j;

import org.junit.Test;

import com.cpn.os4j.model.Access;

public class EndpointTest {

	@Test
	public void testGetAccess() throws InterruptedException {
		final OpenStackCredentials creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("vcgAdmin", "pr0t3ctth1s"), "VCGs"));
		final ServiceCatalog ep = new ServiceCatalog("http://control.dev.intercloud.net:5000", creds);
		final Access access = ep.getAccess();
		access.localhostHack = true;
		final ComputeEndpoint cep = access.getComputeEndpoint("RegionOne", "publicURL");
		final VolumeEndpoint vep = access.getVolumeEndpoint("RegionOne", "publicURL");
		cep.listImages();
		cep.listFlavors();
		System.out.println(vep.listVolumes());
		System.out.println(cep.listKeyPairs());
		// System.out.println(cep.listAddresses());

		// Volume v = vep.createVolume("test", "test", 1, new HashMap<String,
		// String>(), "nova");

		// vep.deleteVolume(v);
		// IPAddressPool pool = null;
		// for (IPAddressPool p : cep.listPools()) {
		// if (p.getName().equals("vcgs")) {
		// pool = p;
		// }
		// }
		// IPAddress ip = pool.getIPAddresses().get(0);
		// Server server = cep.createServer("test", ip, images.get(0),
		// flavors.get(0), null, null).waitUntilRunning(100000);
		//
		// // System.out.println(server);
		// cep.associateIp(server, ip);
		// System.out.println(cep.listVolumeAttachments(server));
		//
		//
		// System.out.println(cep.attachVolume(server.getId(),
		// vep.listVolumes().get(0).getId(), "/dev/sda"));
		// server.delete();
	}
}
