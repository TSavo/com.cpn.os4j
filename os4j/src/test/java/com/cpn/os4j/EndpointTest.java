package com.cpn.os4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.Flavor;
import com.cpn.os4j.model.IPAddress;
import com.cpn.os4j.model.IPAddressPool;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.NetworkInterface;
import com.cpn.os4j.model.Server;
import com.cpn.os4j.model.Volume;

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
		OpenStackCredentials creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("admin", "admin_pass"), "admin"));
		ServiceCatalog ep = new ServiceCatalog("http://10.1.14.33:5000", creds);
		Access access = ep.getAccess();
		access.localhostHack = true;
		List<NetworkInterface> networks = new ArrayList<>();
		NetworkInterface networkInterface1 = new NetworkInterface();
		networkInterface1.setPort("01f6aae3-4a55-468d-a7e4-34df505609f4");
		networks.add(networkInterface1);
		NetworkInterface networkInterface2 = new NetworkInterface();
		networkInterface2.setUuid("6912c85c-ccee-4872-a3ed-4143dd544ec5");
		networks.add(networkInterface2);
		ComputeEndpoint cep = access.getQuantumComputeEndpoint("RegionOne", "publicURL");
		Server serverResponse = cep.createQuantumServer("Test Quantum Server", null, "e2d2d65c-f21b-4e4a-a8fb-05484d11decb", "2", null, null, null, null, 1, 1, networks);
		//Server serverResponse = cep.createServer("Test Quantum Server", null, "e2d2d65c-f21b-4e4a-a8fb-05484d11decb", "2", null, null, null, null);
		System.out.println("The server created with an id:"+serverResponse.getId());
	}
}
