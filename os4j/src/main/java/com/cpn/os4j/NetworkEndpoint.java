package com.cpn.os4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cpn.apiomatic.annotation.Documentation;
import com.cpn.apiomatic.rest.HttpHeaderDelegate;
import com.cpn.apiomatic.rest.RestCommand;
import com.cpn.os4j.model.AddRouterResponse;
import com.cpn.os4j.model.ExternalNetwork;
import com.cpn.os4j.model.ExternalNetworkResponse;
import com.cpn.os4j.model.FloatingIpResponse;
import com.cpn.os4j.model.Floatingip;
import com.cpn.os4j.model.Network;
import com.cpn.os4j.model.NetworkResponse;
import com.cpn.os4j.model.Port;
import com.cpn.os4j.model.PortResponse;
import com.cpn.os4j.model.Router;
import com.cpn.os4j.model.RouterResponse;
import com.cpn.os4j.model.Subnet;
import com.cpn.os4j.model.SubnetResponse;
import com.cpn.os4j.model.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NetworkEndpoint {

	String serverUrl;
	Token token;
	HttpHeaderDelegate headerDelegate;
	public NetworkEndpoint(final String aServerUrl, final Token aToken) {
		super();
		token = aToken;
		serverUrl = aServerUrl;
		headerDelegate=new XAuthTokenHeaderDelegate(aToken);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getTenantId() {
		return token.getTenant().getId();
	}
	
	@Documentation("retrieves the network lists")
	public List<Network> listNetworks() {
		final RestCommand<String, NetworkResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/networks");
		command.setResponseModel(NetworkResponse.class);
		return command.get().getNetworks();
	}
	
	@Documentation("creates the internal network. see the testCreateNetwork junit for data setup")
	public Network createNetwork(final Network aNetwork){
		final RestCommand<Map<String, Network>, NetworkResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/networks.json");
		final Map<String, Network> args = new HashMap<>();
		args.put("network", aNetwork);
		command.setRequestModel(args);
		command.setResponseModel(NetworkResponse.class);
		return command.post().getNetwork();
	}
	
	@Documentation("creates the internal network. see the testExtCreateNetwork junit for data setup")
	public Network createExtNetwork(final ExternalNetwork anExternalNetwork){
		final RestCommand<Map<String, ExternalNetwork>, NetworkResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/networks.json");
		final Map<String, ExternalNetwork> args = new HashMap<>();
		args.put("network", anExternalNetwork);
		command.setRequestModel(args);
		command.setResponseModel(NetworkResponse.class);
		return command.post().getNetwork();
	}
	
	public void deleteNetwork(final String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/networks/" + anId + ".json");
		command.delete();
	}
	
	@Documentation("creates the subnet. see the testCreateSubnet junit for data setup")
	public Subnet createSubnet(final Subnet aSubnet){
		final RestCommand<Map<String, Subnet>, SubnetResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/subnets.json");
		final Map<String, Subnet> args = new HashMap<>();
		args.put("subnet", aSubnet);
		command.setRequestModel(args);
		command.setResponseModel(SubnetResponse.class);
		return command.post().getSubnet();
	}
	
	public Subnet getSubnet(final String anId){
		final RestCommand<String, SubnetResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/subnets/"+ anId +".json");
		command.setResponseModel(SubnetResponse.class);
		return command.get().getSubnet();
	}
	
	public void deleteSubnet(final String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/subnets/" + anId + ".json");
		command.delete();
	}
	
	@Documentation("creates the router. see the testCreateSubnet junit for data setup")
	public Router createRouter(final Router aRouter){
		final RestCommand<Map<String, Router>, RouterResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers.json");
		final Map<String, Router> args = new HashMap<>();
		args.put("router", aRouter);
		command.setRequestModel(args);
		command.setResponseModel(RouterResponse.class);
		return command.post().getRouter();
	}
	
	public List<Router> listRouters() {
		final RestCommand<String, RouterResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers.json");
		command.setResponseModel(RouterResponse.class);
		return command.get().getRouters();
	}
	
	public void deleteRouter(final String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + anId + ".json");
		command.delete();
	}
	
	@Documentation("adds the router to the subnet. see the testAddRouterToSubnet for data setup")
	public String addRouterToSubnet(final String aRouterId, final String aSubnetId){
		final RestCommand<Map<String, String>, AddRouterResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + aRouterId + "/add_router_interface.json");
		final Map<String, String> args = new HashMap<>();
		args.put("subnet_id", aSubnetId);
		command.setRequestModel(args);
		command.setResponseModel(AddRouterResponse.class);
		return command.put().getPortId();
	}
	
	public List<String> removeRouterToSubnet(final String aRouterId, final String aSubnetId){
		final RestCommand<Map<String, String>, List<String>> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + aRouterId + "/remove_router_interface.json");
		final Map<String, String> args = new HashMap<>();
		args.put("subnet_id", aSubnetId);
		command.setRequestModel(args);
		return command.put();
	}
	
	@Documentation("sets the external gateway to the router. see the testSetExtGateway for data setup")
	public Router setRouterToExtNetwork(final String aRouterId,final Router aRouter){
		final RestCommand<Map<String, Router>, RouterResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + aRouterId +".json");
		final Map<String, Router> args = new HashMap<>();
		args.put("router", aRouter);
		command.setRequestModel(args);
		command.setResponseModel(RouterResponse.class);
		return command.put().getRouter();
	}
	
	public Router deleteRouterGateway(final Router aRouter){
		final RestCommand<Map<String, Router>, RouterResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + aRouter.getId() +".json");
		final Map<String, Router> args = new HashMap<>();
		args.put("router", aRouter);
		command.setRequestModel(args);
		command.setResponseModel(RouterResponse.class);
		return command.put().getRouter();
	}
	
	@Documentation("allocates the floating ip to the network. see the testCreateFloatingip for data setup")
	public Floatingip allocateFloatingIp(final Floatingip aFloatingIp){
		final RestCommand<Map<String, Floatingip>, FloatingIpResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/floatingips.json");
		final Map<String, Floatingip> args = new HashMap<>();
		args.put("floatingip", aFloatingIp);
		command.setRequestModel(args);
		command.setResponseModel(FloatingIpResponse.class);
		return command.post().getFloatingip();
	}
	
	@Documentation("associates the floating ip with port. see the testAssociateFloatingIp for data setup")
	public Floatingip associateFloatingIp(final String aFloatingId, final Floatingip aFloatingIp){
		final RestCommand<Map<String, Floatingip>, FloatingIpResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/floatingips/" + aFloatingId +".json");
		final Map<String, Floatingip> args = new HashMap<>();
		args.put("floatingip", aFloatingIp);
		command.setRequestModel(args);
		command.setResponseModel(FloatingIpResponse.class);
		return command.put().getFloatingip();
	}
	
	@Documentation("removes the port from the floating ip.")
	public Floatingip disAssociateFloatingIp(final String aFloatingId,final Floatingip aFloatingIp){
		final RestCommand<Map<String, Floatingip>, FloatingIpResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/floatingips/" + aFloatingId +".json");
		final Map<String, Floatingip> args = new HashMap<>();
		args.put("floatingip", aFloatingIp);
		command.setRequestModel(args);
		command.setResponseModel(FloatingIpResponse.class);
		return command.put().getFloatingip();
	}
	public void deleteFloatingip(final String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/floatingips/" + anId + ".json");
		command.delete();
	}
	public List<Floatingip> listFloatingips() {
		final RestCommand<String, FloatingIpResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/floatingips.json");
		command.setResponseModel(FloatingIpResponse.class);
		return command.get().getFloatingips();
	}
	
	@Documentation("create  the port. see the testCreatePort for data setup")
	public Port createPort(final Port aPort){
		final RestCommand<Map<String, Port>, PortResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/ports.json");
		final Map<String, Port> args = new HashMap<>();
		args.put("port", aPort);
		command.setRequestModel(args);
		command.setResponseModel(PortResponse.class);
		return command.post().getPort();
	}
	
	public List<Port> listPorts() {
		final RestCommand<String, PortResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/ports.json");
		command.setResponseModel(PortResponse.class);
		return command.get().getSomePort();
	}
}
