package com.cpn.os4j;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cpn.os4j.model.Access;
import com.cpn.os4j.model.ExternalGatewayInfo;
import com.cpn.os4j.model.ExternalNetwork;
import com.cpn.os4j.model.Floatingip;
import com.cpn.os4j.model.Network;
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
			for (Iterator iterator = someListNetwork.iterator(); iterator.hasNext();) {
				Network network = (Network) iterator.next();
				mapper.writeValue(System.out,network);
			}
		}catch(Exception e){
			Assert.fail("Exception occured while listing network:" + e);
		}
	}
	
	@Test
	public void testCreateNetwork() {
		try {
			String tenantId = "0d6f0bf548a645ac950bb630bd7ac17f";
			Network aNetwork = new Network();
			aNetwork.setName("net_internal_proj1");
			aNetwork.setShared(false);
			aNetwork.setPhysicalNetwork("physnet1");
			aNetwork.setAdminStateUp(true);
			aNetwork.setTenantId(tenantId);
			aNetwork.setNetworkType("vlan");
			aNetwork.setExternal(false);
			aNetwork.setSegmentationId(10);
			Network netWorkResponse = nep.createNetwork(aNetwork);
			System.out.println("Network created with an Id:"+netWorkResponse.getId());
			Subnet aSubnet = new Subnet();
			aSubnet.setIpVersion(4);
			aSubnet.setGatewayIp("10.3.19.1");
			aSubnet.setNetworkId(netWorkResponse.getId());
			aSubnet.setTenantId(tenantId);
			aSubnet.setCidr("10.3.19.0/24");
			Subnet subnetResponse = nep.createSubnet(aSubnet);
			System.out.println("Subnet response:"+subnetResponse.getId());
			Router aRouter = new Router();
			aRouter.setTenantId(tenantId);
			aRouter.setName("router_internal_proj1");
			aRouter.setAdminStateUp(true);
			Router routerResponse = nep.createRouter(aRouter);
			System.out.println("Router response:"+routerResponse.getId());
			String portId = nep.addRouterToSubnet(routerResponse.getId(), subnetResponse.getId());
			System.out.println("Add router with portId:"+portId);
		} catch (Exception e) {
			Assert.fail("Exception occured while creating a network:" + e);
		}
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
		ExternalNetwork anExtNetwork = new ExternalNetwork();
		anExtNetwork.setName("net_ext_proj1");
		anExtNetwork.setAdminStateUp(true);
		anExtNetwork.setNetworkType("vlan");
		anExtNetwork.setTenantId(tenantId);
		anExtNetwork.setExternal(true);
		Network netWorkResponse = nep.createExtNetwork(anExtNetwork);
		System.out.println("External Network created with an Id:"+netWorkResponse.getId());
		Subnet aSubnet = new Subnet();
		aSubnet.setIpVersion(4);
		aSubnet.setGatewayIp("10.3.19.1");
		aSubnet.setEnableDhcp(false);
		aSubnet.setNetworkId(netWorkResponse.getId());
		aSubnet.setTenantId(tenantId);
		aSubnet.setCidr("10.3.19.0/24");
		Subnet subnetResponse = nep.createSubnet(aSubnet);
		System.out.println("Subnet created with an Id:"+subnetResponse.getId());
		Router aRouter = new Router();
		aRouter.setTenantId(tenantId);
		aRouter.setName("router_ext_proj1");
		aRouter.setAdminStateUp(true);
		Router routerResponse = nep.createRouter(aRouter);
		System.out.println("Router created with an Id:"+routerResponse.getId());
		String portId = nep.addRouterToSubnet(routerResponse.getId(), subnetResponse.getId());
		System.out.println("Add router with portId:"+portId);
		ExternalGatewayInfo anExternalGatewayInfo = new ExternalGatewayInfo();
		anExternalGatewayInfo.setNetworkId(netWorkResponse.getId());
		Router anExtRouter = new Router();
		anExtRouter.setExternalGatewayInfo(anExternalGatewayInfo);
		Router anExtRouterResponse = nep.setRouterToExtNetwork(routerResponse.getId(), anExtRouter);
		System.out.println("Set router to external net for router Id:"+anExtRouterResponse.getId());
		Floatingip aFloatingIp = new Floatingip();
		aFloatingIp.setFloatingNetworkId(netWorkResponse.getId());
		aFloatingIp.setTenantId(tenantId);
		Floatingip aFloatingIpResponse = nep.allocateFloatingIp(aFloatingIp);
		System.out.println("Allocated floating ip:"+aFloatingIpResponse.getId());
		}catch(Exception e){
			System.out.println("Exception occured while creating an external network");
		}
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
}
