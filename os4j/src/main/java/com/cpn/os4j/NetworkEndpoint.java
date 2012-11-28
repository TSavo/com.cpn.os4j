package com.cpn.os4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cpn.apiomatic.rest.HttpHeaderDelegate;
import com.cpn.apiomatic.rest.RestCommand;
import com.cpn.os4j.model.AddRouterResponse;
import com.cpn.os4j.model.ExternalNetwork;
import com.cpn.os4j.model.ExternalNetworkResponse;
import com.cpn.os4j.model.FloatingIpResponse;
import com.cpn.os4j.model.Floatingip;
import com.cpn.os4j.model.Network;
import com.cpn.os4j.model.NetworkResponse;
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
	
	public List<Network> listNetworks() {
		final RestCommand<String, NetworkResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/networks");
		command.setResponseModel(NetworkResponse.class);
		return command.get().getNetworks();
	}
	
	public Network createNetwork(Network aNetwork){
		final RestCommand<Map<String, Network>, NetworkResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/networks.json");
		final Map<String, Network> args = new HashMap<>();
		args.put("network", aNetwork);
		command.setRequestModel(args);
		command.setResponseModel(NetworkResponse.class);
		return command.post().getNetwork();
	}
	
	public Network createExtNetwork(ExternalNetwork anExternalNetwork){
		final RestCommand<Map<String, ExternalNetwork>, NetworkResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/networks.json");
		final Map<String, ExternalNetwork> args = new HashMap<>();
		args.put("network", anExternalNetwork);
		command.setRequestModel(args);
		command.setResponseModel(NetworkResponse.class);
		return command.post().getNetwork();
	}
	
	public void deleteNetwork(String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/networks/" + anId + ".json");
		command.delete();
	}
	
	public Subnet createSubnet(Subnet aSubnet){
		final RestCommand<Map<String, Subnet>, SubnetResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/subnets.json");
		final Map<String, Subnet> args = new HashMap<>();
		args.put("subnet", aSubnet);
		command.setRequestModel(args);
		command.setResponseModel(SubnetResponse.class);
		return command.post().getSubnet();
	}
	
	public Subnet getSubnet(String anId){
		final RestCommand<String, SubnetResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/subnets/"+ anId +".json");
		command.setResponseModel(SubnetResponse.class);
		return command.get().getSubnet();
	}
	
	public void deleteSubnet(String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/subnets/" + anId + ".json");
		command.delete();
	}
	
	public Router createRouter(Router aRouter){
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
	
	public void deleteRouter(String anId) {
		final RestCommand<String, String> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + anId + ".json");
		command.delete();
	}
	
	public String addRouterToSubnet(String aRouterId, String aSubnetId){
		final RestCommand<Map<String, String>, AddRouterResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + aRouterId + "/add_router_interface.json");
		final Map<String, String> args = new HashMap<>();
		args.put("subnet_id", aSubnetId);
		command.setRequestModel(args);
		command.setResponseModel(AddRouterResponse.class);
		return command.put().getPortId();
	}
	
	public List<String> removeRouterToSubnet(String aRouterId, String aSubnetId){
		final RestCommand<Map<String, String>, List<String>> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + aRouterId + "/remove_router_interface.json");
		final Map<String, String> args = new HashMap<>();
		args.put("subnet_id", aSubnetId);
		command.setRequestModel(args);
		return command.put();
	}
	
	public Router setRouterToExtNetwork(String aRouterId, Router aRouter){
		final RestCommand<Map<String, Router>, RouterResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + aRouterId +".json");
		final Map<String, Router> args = new HashMap<>();
		args.put("router", aRouter);
		command.setRequestModel(args);
		command.setResponseModel(RouterResponse.class);
		return command.put().getRouter();
	}
	
	public Router deleteRouterGateway(Router aRouter){
		final RestCommand<Map<String, Router>, RouterResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/routers/" + aRouter.getId() +".json");
		final Map<String, Router> args = new HashMap<>();
		args.put("router", aRouter);
		command.setRequestModel(args);
		command.setResponseModel(RouterResponse.class);
		return command.put().getRouter();
	}
	
	public Floatingip allocateFloatingIp(Floatingip aFloatingIp){
		final RestCommand<Map<String, Floatingip>, FloatingIpResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/floatingips.json");
		final Map<String, Floatingip> args = new HashMap<>();
		args.put("floatingip", aFloatingIp);
		command.setRequestModel(args);
		command.setResponseModel(FloatingIpResponse.class);
		return command.post().getFloatingip();
	}
	
	public Floatingip associateFloatingIp(String aFloatingId, Floatingip aFloatingIp){
		final RestCommand<Map<String, Floatingip>, FloatingIpResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/floatingips/" + aFloatingId +".json");
		final Map<String, Floatingip> args = new HashMap<>();
		args.put("floatingip", aFloatingIp);
		command.setRequestModel(args);
		command.setResponseModel(FloatingIpResponse.class);
		return command.put().getFloatingip();
	}
	
	public Floatingip disAssociateFloatingIp(String aFloatingId, Floatingip aFloatingIp){
		final RestCommand<Map<String, Floatingip>, FloatingIpResponse> command = new RestCommand<>();
		command.setHeaderDelegate(headerDelegate);
		command.setUrl(getServerUrl() + "v2.0/floatingips/" + aFloatingId +".json");
		final Map<String, Floatingip> args = new HashMap<>();
		args.put("floatingip", aFloatingIp);
		command.setRequestModel(args);
		command.setResponseModel(FloatingIpResponse.class);
		return command.put().getFloatingip();
	}
	public void deleteFloatingip(String anId) {
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
	
}
