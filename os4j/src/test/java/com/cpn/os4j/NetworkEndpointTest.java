package com.cpn.os4j;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.ExternalGatewayInfo;
import com.cpn.os4j.model.Fixedips;
import com.cpn.os4j.model.Floatingip;
import com.cpn.os4j.model.Network;
import com.cpn.os4j.model.Port;
import com.cpn.os4j.model.Router;
import com.cpn.os4j.model.Subnet;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NetworkEndpointTest {

	OpenStackCredentials creds = null;
	ServiceCatalog ep = null;
	Access access = null;
	NetworkEndpoint nep = null;
	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void init(){
		creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("admin", "admin_pass"), "admin"));
		//creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("user_one", "user_one"), "project_one"));
		//creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("user_one", "dev_user"), "dev_tenant"));
		//ep = new ServiceCatalog("http://control.dev.intercloud.net:5000", creds);
		ep = new ServiceCatalog("http://10.1.14.33:5000", creds);
		access = ep.getAccess();
		access.localhostHack = true;
		nep = access.getNetworkEndpoint("RegionOne", "publicURL");
	}

	@After
	public void close(){
		creds = null;
		ep = null;
		access = null;
		nep = null;
	}

	@Test
	public void testListNetworks() {
		try{
			List<Network> someListNetwork = nep.listNetworks();
			System.out.println("List of Networks");
			mapper.writeValue(System.out,someListNetwork);
		}catch(Exception e){
			Assert.fail("Exception occured while listing network:" + e);
		}
	}

	@Test
	public void testCreateNetwork() {
		try {
			String tenantId = access.getToken().getTenant().getId();
			Network netWorkResponse = createNetwork(tenantId);
			System.out.println("Network created with an Id:"+netWorkResponse.getId());
			Subnet subnetResponse = createSubnet(tenantId, netWorkResponse);
			System.out.println("Subnet response:"+subnetResponse.getId());
			Router routerResponse = createRouter(tenantId);
			String portId = addRouterToSubnet(subnetResponse, routerResponse);
			System.out.println("Add router with portId:"+portId);
		} catch (Exception e) {
			Assert.fail("Exception occured while creating a network:" + e);
		}
	}

	private String addRouterToSubnet(Subnet subnetResponse, Router routerResponse) {
		System.out.println("Router response:"+routerResponse.getId());
		String portId = nep.addRouterToSubnet(routerResponse.getId(), subnetResponse.getId());
		return portId;
	}

	@Test
	public void  testAddRouterToSubnet() {
		String portId = nep.addRouterToSubnet("9fa72c38-eb26-4c84-8dc8-fac0595e667b", "66d70cee-da58-476d-b0d6-6e8d4fe8e2b9");
		System.out.println("PortId:"+portId);
	}

	private Router createRouter(String tenantId) {
		Router aRouter = new Router();
		aRouter.setTenantId(tenantId);
		aRouter.setName("router_internal_proj2");
		aRouter.setAdminStateUp(true);
		Router routerResponse = nep.createRouter(aRouter);
		return routerResponse;
	}

	private Subnet createSubnet(String tenantId, Network netWorkResponse) {
		Subnet aSubnet = new Subnet();
		aSubnet.setIpVersion(4);
		aSubnet.setGatewayIp("10.3.24.1");
		aSubnet.setNetworkId(netWorkResponse.getId());
		aSubnet.setTenantId(tenantId);
		aSubnet.setCidr("10.3.24.0/24");
		aSubnet.setEnableDhcp(false);
		Subnet subnetResponse = nep.createSubnet(aSubnet);
		return subnetResponse;
	}

	private Network createNetwork(String tenantId) {
		Network aNetwork = new Network();
		aNetwork.setName("net_internal_proj1");
		aNetwork.setShared(false);
		aNetwork.setPhysicalNetwork("physnet1");
		aNetwork.setAdminStateUp(true);
		aNetwork.setTenantId(tenantId);
		aNetwork.setNetworkType("vlan");
		aNetwork.setExternal(false);
		aNetwork.setSegmentationId(1);
		Network netWorkResponse = nep.createNetwork(aNetwork);
		return netWorkResponse;
	}

	//@Test
	public void testDeleteNetwork(){
		try{
			String routerId = "df16f164-fb42-46cc-9bcf-a74657f54c1e";
			String subnetId = "f54790fc-46a1-4ed9-9147-c3744852e1a8";
			nep.removeRouterToSubnet(routerId, subnetId);
			nep.deleteRouter(routerId);
			Subnet SubnetResponse = nep.getSubnet(subnetId);
			nep.deleteSubnet(subnetId);
			nep.deleteNetwork(SubnetResponse.getNetworkId());
		}catch(Exception e){
			Assert.fail("Exception occured while network details:" + e);
		}
	}

	@Test
	public void testCreateExtNetwork() throws Exception {
		try{
			String tenantId = access.getToken().getTenant().getId();
			Network netWorkResponse = createExtNetwork(tenantId);
			System.out.println("External Network created with an Id:"+netWorkResponse.getId());
			Subnet subnetResponse = createExtSubnet(tenantId, netWorkResponse);
			System.out.println("Subnet created with an Id:"+subnetResponse.getId());
			Router routerResponse = createExtRouter(tenantId);
			System.out.println("Router created with an Id:"+routerResponse.getId());
			Router anExtRouterResponse = setExtGateway(netWorkResponse, routerResponse);
			System.out.println("Set router to external net for router Id:"+anExtRouterResponse.getId());
			Floatingip aFloatingIpResponse = createFloatingip(tenantId, netWorkResponse);
			System.out.println("Allocated floating ip:"+aFloatingIpResponse.getId());
		}catch(Exception e){
			System.out.println("Exception occured while creating an external network");
		}
	}

	@Test
	public void testCreatePort() {
		String tenantId = access.getToken().getTenant().getId();
		//Network networkResponse = testCreateExtNetwork(tenantId);
		Port aPort = new Port();
		aPort.setNetworkId("6912c85c-ccee-4872-a3ed-4143dd544ec5");
		aPort.setAdminStateUp(true);
		//Subnet subnetResponse = testCreateExtSubnet(tenantId, networkResponse);
		Fixedips someFixedips = new Fixedips();
		someFixedips.setIpAddress("20.20.3.20");
		someFixedips.setSubnetId("3fa19c27-0880-48f7-9c31-8680266cce66");
		List<Fixedips> ips = new ArrayList<>();
		ips.add(someFixedips);
		aPort.setFixedIps(ips);
		Port portResponse = nep.createPort(aPort);
		System.out.println("port created with an id:"+portResponse.getId());
	}

	private Floatingip createFloatingip(String tenantId, Network netWorkResponse) {
		Floatingip aFloatingIp = new Floatingip();
		aFloatingIp.setFloatingNetworkId(netWorkResponse.getId());
		aFloatingIp.setTenantId(tenantId);
		Floatingip aFloatingIpResponse = nep.allocateFloatingIp(aFloatingIp);
		return aFloatingIpResponse;
	}

	private Router setExtGateway(Network netWorkResponse, Router routerResponse) {
		ExternalGatewayInfo anExternalGatewayInfo = new ExternalGatewayInfo();
		anExternalGatewayInfo.setNetworkId(netWorkResponse.getId());
		Router anExtRouter = new Router();
		anExtRouter.setExternalGatewayInfo(anExternalGatewayInfo);
		Router anExtRouterResponse = nep.setRouterToExtNetwork(routerResponse.getId(), anExtRouter);
		return anExtRouterResponse;
	}

	@Test
	public void testSetExtGateway() {
		ExternalGatewayInfo anExternalGatewayInfo = new ExternalGatewayInfo();
		anExternalGatewayInfo.setNetworkId("30eb4a1f-9f50-4029-bdec-3968269595c2");
		Router anExtRouter = new Router();
		anExtRouter.setExternalGatewayInfo(anExternalGatewayInfo);
		Router anExtRouterResponse = nep.setRouterToExtNetwork("9fa72c38-eb26-4c84-8dc8-fac0595e667b", anExtRouter);
		System.out.println("The ext net added to router and generated portId:"+anExtRouterResponse.getId());
	}

	private Router createExtRouter(String tenantId) {
		Router aRouter = new Router();
		aRouter.setTenantId(tenantId);
		aRouter.setName("router_ext_proj4");
		aRouter.setAdminStateUp(true);
		Router routerResponse = nep.createRouter(aRouter);
		return routerResponse;
	}

	private Subnet createExtSubnet(String tenantId, Network netWorkResponse) {
		Subnet aSubnet = new Subnet();
		aSubnet.setIpVersion(4);
		aSubnet.setGatewayIp("10.3.22.1");
		aSubnet.setEnableDhcp(true);
		aSubnet.setNetworkId(netWorkResponse.getId());
		aSubnet.setTenantId(tenantId);
		aSubnet.setCidr("10.3.22.0/24");
		Subnet subnetResponse = nep.createSubnet(aSubnet);
		return subnetResponse;
	}

	private Network createExtNetwork(String tenantId) {
		Network anExtNetwork = new Network();
		anExtNetwork.setName("net_ext_proj1");
		anExtNetwork.setAdminStateUp(true);
		anExtNetwork.setNetworkType("vlan");
		anExtNetwork.setTenantId(tenantId);
		anExtNetwork.setExternal(true);
		anExtNetwork.setSegmentationId(16);
		anExtNetwork.setPhysicalNetwork("physnet1");
		Network netWorkResponse = nep.createNetwork(anExtNetwork);
		return netWorkResponse;
	}

	//@Test
	public void testDeleteExtNetwork(){
		try{
			String subnetId = "bc639a4e-041a-49bc-accb-a7b6cbba9170";
			String routerId = "af6ad42d-9ba4-49b1-b633-47a99c6e1a01";
			String floatingipId = "72147d3b-469b-4bde-909f-7d03031fa452";
			nep.deleteFloatingIp(floatingipId);
			nep.removeRouterToSubnet(routerId, subnetId);
			nep.deleteRouter(routerId);
			Subnet SubnetResponse = nep.getSubnet(subnetId);
			nep.deleteSubnet(subnetId);
			nep.deleteNetwork(SubnetResponse.getNetworkId());
		}catch(Exception e){
			Assert.fail("Exception occured while deleting external network details:" + e);
		}
	}

	@Test 
	public void testAssociateFloatingIp() {
		try{
			String tenantId = access.getToken().getTenant().getId();
			Floatingip aFloatingIp = new Floatingip();
			aFloatingIp.setFloatingNetworkId("e299ed53-ebfd-4d1a-9a77-18444e87b1d4");
			aFloatingIp.setTenantId(tenantId);
			Floatingip aFloatingIpResponse = nep.allocateFloatingIp(aFloatingIp);
			Floatingip anFloatingIp = new Floatingip();
			anFloatingIp.setPortId("0eb41a6c-a4b3-4414-9b69-f8ef5dbbdcfe");
			Floatingip anFloatingIpResponse = nep.associateFloatingIp(aFloatingIpResponse.getId(), anFloatingIp);
			System.out.println("associated floating ip to the port id:"+anFloatingIpResponse.getId());
		}catch(Exception e){
			Assert.fail("Exception occured while associating floating IP:" + e);
		}
	}

	@Test
	public void testListFloatingips() {
		try{
			List<Floatingip> someListFloatingips = nep.listFloatingIps();
			System.out.println("List of floatingips");
			mapper.writeValue(System.out, someListFloatingips);
		}catch(Exception e){
			Assert.fail("Exception occured while listing floatingips:" + e);
		}
	}

	@Test
	public void testListPorts() {
		try{
			List<Port> someListPorts = nep.listPorts();
			System.out.println("List of ports");
			mapper.writeValue(System.out, someListPorts);
		}catch(Exception e){
			Assert.fail("Exception occured while listing ports:" + e);
		}
	}
}
