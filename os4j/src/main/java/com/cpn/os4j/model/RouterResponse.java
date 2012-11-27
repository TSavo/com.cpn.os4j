package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class RouterResponse {

	Router router;
	List<Router> routers;

	
	public Router getRouter() {
		return router;
	}


	public void setRouter(final Router router) {
		this.router = router;
	}


	public List<Router> getRouters() {
		return routers;
	}


	public void setRouters(final List<Router> routers) {
		this.routers = routers;
	}


	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("router", router).append("routers", routers);
		return builder.toString();
	}
}
