package com.cpn.os4j;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.ExternalGatewayInfo;
import com.cpn.os4j.model.ExternalNetwork;
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
		//creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("admin", "admin_pass"), "admin"));
		creds = new OpenStackCredentials(new AuthenticationCredentials(new PasswordCredentials("user_one", "user_one"), "project_one"));
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
			String tenantId = "0d6f0bf548a645ac950bb630bd7ac17f";
			Network netWorkResponse = testCreateNetwork(tenantId);
			System.out.println("Network created with an Id:"+netWorkResponse.getId());
			/*Subnet subnetResponse = testCreateSubnet(tenantId, netWorkResponse);
			System.out.println("Subnet response:"+subnetResponse.getId());
			Router routerResponse = testCreateRouter(tenantId);
			String portId = testAddRouterToSubnet(subnetResponse, routerResponse);
			System.out.println("Add router with portId:"+portId);*/
		} catch (Exception e) {
			Assert.fail("Exception occured while creating a network:" + e);
		}
	}

	private String testAddRouterToSubnet(Subnet subnetResponse, Router routerResponse) {
		System.out.println("Router response:"+routerResponse.getId());
		String portId = nep.addRouterToSubnet(routerResponse.getId(), subnetResponse.getId());
		return portId;
	}

	private Router testCreateRouter(String tenantId) {
		Router aRouter = new Router();
		aRouter.setTenantId(tenantId);
		aRouter.setName("router_internal_proj1");
		aRouter.setAdminStateUp(true);
		Router routerResponse = nep.createRouter(aRouter);
		return routerResponse;
	}

	private Subnet testCreateSubnet(String tenantId, Network netWorkResponse) {
		Subnet aSubnet = new Subnet();
		aSubnet.setIpVersion(4);
		aSubnet.setGatewayIp("10.3.19.1");
		aSubnet.setNetworkId(netWorkResponse.getId());
		aSubnet.setTenantId(tenantId);
		aSubnet.setCidr("10.3.19.0/24");
		Subnet subnetResponse = nep.createSubnet(aSubnet);
		return subnetResponse;
	}

	private Network testCreateNetwork(String tenantId) {
		Network aNetwork = new Network();
		aNetwork.setName("net_internal_proj2");
		aNetwork.setShared(false);
		aNetwork.setPhysicalNetwork("physnet1");
		aNetwork.setAdminStateUp(true);
		aNetwork.setTenantId(tenantId);
		aNetwork.setNetworkType("vlan");
		aNetwork.setExternal(false);
		aNetwork.setSegmentationId(11);
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
		String tenantId = "0d6f0bf548a645ac950bb630bd7ac17f";
		Network netWorkResponse = testCreateExtNetwork(tenantId);
		System.out.println("External Network created with an Id:"+netWorkResponse.getId());
		Subnet subnetResponse = testCreateExtSubnet(tenantId, netWorkResponse);
		System.out.println("Subnet created with an Id:"+subnetResponse.getId());
		Router routerResponse = testCreateExtRouter(tenantId);
		System.out.println("Router created with an Id:"+routerResponse.getId());
		Router anExtRouterResponse = testSetExtGateway(netWorkResponse, routerResponse);
		System.out.println("Set router to external net for router Id:"+anExtRouterResponse.getId());
		Floatingip aFloatingIpResponse = testCreateFloatingip(tenantId, netWorkResponse);
		System.out.println("Allocated floating ip:"+aFloatingIpResponse.getId());
		}catch(Exception e){
			System.out.println("Exception occured while creating an external network");
		}
	}

	@Test
	public void testCreatePort() {
		String tenantId = "0d6f0bf548a645ac950bb630bd7ac17f";
		//Network networkResponse = testCreateExtNetwork(tenantId);
		Port aPort = new Port();
		aPort.setNetworkId("e1d6b1d9-f6a0-4fc7-9e86-2a3a798ba0b7");
		aPort.setAdminStateUp(true);
		//Subnet subnetResponse = testCreateExtSubnet(tenantId, networkResponse);
		Fixedips someFixedips = new Fixedips();
		someFixedips.setIpAddress("40.0.0.3");
		someFixedips.setSubnetId("a437e006-f663-4eca-8342-a9776e02701b");
		List<Fixedips> ips = new ArrayList<>();
		ips.add(someFixedips);
		aPort.setFixedIps(ips);
		Port portResponse = nep.createPort(aPort);
		System.out.println("port created with an id:"+portResponse.getId());
	}

	private Floatingip testCreateFloatingip(String tenantId, Network netWorkResponse) {
		Floatingip aFloatingIp = new Floatingip();
		aFloatingIp.setFloatingNetworkId(netWorkResponse.getId());
		aFloatingIp.setTenantId(tenantId);
		Floatingip aFloatingIpResponse = nep.allocateFloatingIp(aFloatingIp);
		return aFloatingIpResponse;
	}

	private Router testSetExtGateway(Network netWorkResponse, Router routerResponse) {
		ExternalGatewayInfo anExternalGatewayInfo = new ExternalGatewayInfo();
		anExternalGatewayInfo.setNetworkId(netWorkResponse.getId());
		Router anExtRouter = new Router();
		anExtRouter.setExternalGatewayInfo(anExternalGatewayInfo);
		Router anExtRouterResponse = nep.setRouterToExtNetwork(routerResponse.getId(), anExtRouter);
		return anExtRouterResponse;
	}

	private Router testCreateExtRouter(String tenantId) {
		Router aRouter = new Router();
		aRouter.setTenantId(tenantId);
		aRouter.setName("router_ext_proj1");
		aRouter.setAdminStateUp(true);
		Router routerResponse = nep.createRouter(aRouter);
		return routerResponse;
	}

	private Subnet testCreateExtSubnet(String tenantId, Network netWorkResponse) {
		Subnet aSubnet = new Subnet();
		aSubnet.setIpVersion(4);
		aSubnet.setGatewayIp("10.3.20.1");
		aSubnet.setEnableDhcp(false);
		aSubnet.setNetworkId(netWorkResponse.getId());
		aSubnet.setTenantId(tenantId);
		aSubnet.setCidr("10.3.20.0/24");
		Subnet subnetResponse = nep.createSubnet(aSubnet);
		return subnetResponse;
	}

	private Network testCreateExtNetwork(String tenantId) {
		Network anExtNetwork = new Network();
		anExtNetwork.setName("net_ext_proj3");
		anExtNetwork.setAdminStateUp(true);
		anExtNetwork.setNetworkType("vlan");
		anExtNetwork.setTenantId(tenantId);
		anExtNetwork.setExternal(true);
		anExtNetwork.setSegmentationId(14);
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
			nep.deleteFloatingip(floatingipId);
			nep.removeRouterToSubnet(routerId, subnetId);
			nep.deleteRouter(routerId);
			Subnet SubnetResponse = nep.getSubnet(subnetId);
			nep.deleteSubnet(subnetId);
			nep.deleteNetwork(SubnetResponse.getNetworkId());
		}catch(Exception e){
			Assert.fail("Exception occured while deleting external network details:" + e);
		}
	}
	
	//@Test 
	public void testAssociateFloatingIp() {
		try{
			Floatingip aFloatingIp = new Floatingip();
			aFloatingIp.setPortId("581a4925-b380-4f12-8086-fee9df1ebe96");
			Floatingip aFloatingIpResponse = nep.associateFloatingIp("457ba692-aa13-4d7c-b778-6c6df0116105", aFloatingIp);
			System.out.println("Allocate floating ip response:"+aFloatingIpResponse.getId());
		}catch(Exception e){
			Assert.fail("Exception occured while associating floating IP:" + e);
		}
	}

	@Test
	public void testListFloatingips() {
		try{
			List<Floatingip> someListFloatingips = nep.listFloatingips();
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
