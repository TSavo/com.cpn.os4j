package com.cpn.os4j;

import java.util.List;

import org.junit.Test;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.Flavor;
import com.cpn.os4j.model.IPAddress;
import com.cpn.os4j.model.IPAddressPool;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.Server;

public class EndpointTest {

	@Test
	public void testGetAccess() throws InterruptedException {
		OpenStackCredentials creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("vcgAdmin", "pr0t3ctth1s"), "VCGs"));
		ServiceCatalog ep = new ServiceCatalog("http://control.dev.intercloud.net:5000", creds);
		Access access = ep.getAccess();
		access.localhostHack = true;
		ComputeEndpoint cep = access.getComputeEndpoint("RegionOne", "publicURL");
		VolumeEndpoint vep = access.getVolumeEndpoint("RegionOne", "publicURL");
		List<Image> images = cep.listImages();
		List<Flavor> flavors = cep.listFlavors();
		System.out.println(vep.listVolumes());
		System.out.println(cep.listKeyPairs());
		// System.out.println(cep.listAddresses());

		//Volume v = vep.createVolume("test", "test", 1, new HashMap<String, String>(), "nova");
		
		//vep.deleteVolume(v);
//		IPAddressPool pool = null;
//		for (IPAddressPool p : cep.listPools()) {
//			if (p.getName().equals("vcgs")) {
//				pool = p;
//			}
//		}
//		IPAddress ip = pool.getIPAddresses().get(0);
//		Server server = cep.createServer("test", ip, images.get(0), flavors.get(0), null, null).waitUntilRunning(100000);
//
//		// System.out.println(server);
//		cep.associateIp(server, ip);
//		System.out.println(cep.listVolumeAttachments(server));
//
//		
//		System.out.println(cep.attachVolume(server.getId(), vep.listVolumes().get(0).getId(), "/dev/sda"));
//		server.delete();
	}
}
