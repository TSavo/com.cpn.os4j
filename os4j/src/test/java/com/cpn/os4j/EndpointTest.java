package com.cpn.os4j;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.Server;

public class EndpointTest {

	@Test
	public void testGetAccess() throws InterruptedException {
		final OpenStackCredentials creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("vcgAdmin", "pr0t3ctth1s"), "VCGs"));
		final ServiceCatalog ep = new ServiceCatalog("http://control.dev.intercloud.net:5000", creds);
		final Access access = ep.getAccess();
		access.localhostHack = true;
		final ComputeEndpoint cep = access.getComputeEndpoint("RegionOne", "publicURL");
		final VolumeEndpoint vep = access.getVolumeEndpoint("RegionOne", "publicURL");
		/*List<Image> images = cep.listImages();
		List<Flavor> flavors = cep.listFlavors();
		System.out.println(vep.listVolumes());
		System.out.println(cep.listKeyPairs());
		System.out.println(cep.listAddresses());

		Volume v = vep.createVolume("test", "test", 1, new HashMap<String, String>(), "nova");

		vep.deleteVolume(v);		
		IPAddressPool pool = null;
		for (IPAddressPool p : cep.listPools()) {
			if (p.getName().equals("vcgs")) {
				pool = p;
			}
		}
		IPAddress ip = pool.getIPAddresses().get(0);
		Server server = cep.createServer("test", ip, images.get(0), flavors.get(0),null, null, null, null).waitUntilRunning(100000);
		System.out.println(server);
		cep.associateIp(server, ip);
		System.out.println(cep.listVolumeAttachments(server));
		System.out.println(cep.attachVolume(server.getId(),	vep.listVolumes().get(0).getId(), "/dev/sda"));
		server.delete();*/
	}
	
	@Test
	public void testCreateQuantumServer() throws InterruptedException{
		OpenStackCredentials creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("user_one", "user_one"), "project_one"));
		ServiceCatalog ep = new ServiceCatalog("http://10.1.14.33:5000", creds);
		Access access = ep.getAccess();
		access.localhostHack = true;
		/*List<NetworkInterface> networks = new ArrayList<>();
		NetworkInterface networkInterface1 = new NetworkInterface();
		networkInterface1.setPort("d8d67542-3136-42c0-909b-bbd048f63f7d");
		networks.add(networkInterface1);
		NetworkInterface networkInterface2 = new NetworkInterface();
		networkInterface2.setUuid("201b4517-61bb-4cf3-9672-3d065992b759");
		networks.add(networkInterface2);
		ComputeEndpoint cep = access.getComputeEndpoint("RegionOne", "publicURL");
		Server serverResponse = cep.createServer("Test Quantum Server", null, "e2d2d65c-f21b-4e4a-a8fb-05484d11decb", "2", null, null, null, null, 1, 1, networks);
		//Server serverResponse = cep.createServer("Test Quantum Server", null, "e2d2d65c-f21b-4e4a-a8fb-05484d11decb", "2", null, null, null, null);
		 
		System.out.println("The server created with an id:"+serverResponse.getId());
		*/
	}
}
