package com.cpn.os4j.model;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.cpn.os4j.ComputeEndpoint;
import com.cpn.os4j.NetworkEndpoint;
import com.cpn.os4j.VolumeEndpoint;

public class Access {
	List<EndPointDescription> serviceCatalog;
	Token token;
	User user;

	public boolean localhostHack = false;

	@JsonIgnore
	public ComputeEndpoint getComputeEndpoint(final String aRegion, final String endPointType) {
		for (final EndPointDescription d : serviceCatalog) {
			if (d.getType().equals("compute")) {
				for (final Map<String, String> urls : d.getEndpoints()) {
					if (urls.get("region").equals(aRegion)) {
						if (localhostHack) {
							return new ComputeEndpoint(urls.get(endPointType).replaceAll("192\\.168\\.31\\.38", "control.dev.intercloud.net"), token);
						} else {
							return new ComputeEndpoint(urls.get(endPointType), token);
						}
					}
				}
			}
		}
		throw new RuntimeException("Couldn't find the ComputeEndpoint for region: " + aRegion + " and type: " + endPointType);
	}

	@JsonIgnore
	public NetworkEndpoint getNetworkEndpoint(final String aRegion, final String endPointType) {
		for (final EndPointDescription d : serviceCatalog) {
			if (d.getType().equals("network")) {
				for (final Map<String, String> urls : d.getEndpoints()) {
					if (urls.get("region").equals(aRegion)) {
						if (localhostHack) {
							return new NetworkEndpoint(urls.get(endPointType).replaceAll("192\\.168\\.31\\.38", "control.dev.intercloud.net"), token);
						} else {
							return new NetworkEndpoint(urls.get(endPointType), token);
						}
					}
				}
			}
		}
		throw new RuntimeException("Couldn't find the NetworkEndpoint for region: " + aRegion + " and type: " + endPointType);
	}

	public List<EndPointDescription> getServiceCatalog() {
		return serviceCatalog;
	}

	public Token getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	@JsonIgnore
	public VolumeEndpoint getVolumeEndpoint(final String aRegion, final String endPointType) {
		for (final EndPointDescription d : serviceCatalog) {
			if (d.getType().equals("volume")) {
				for (final Map<String, String> urls : d.getEndpoints()) {
					if (urls.get("region").equals(aRegion)) {
						if (localhostHack) {
							return new VolumeEndpoint(urls.get(endPointType).replaceAll("192\\.168\\.31\\.38", "control.dev.intercloud.net"), token);
						} else {
							return new VolumeEndpoint(urls.get(endPointType), token);
						}
					}
				}
			}
		}
		throw new RuntimeException("Couldn't find the ComputeEndpoint for region: " + aRegion + " and type: " + endPointType);
	}

	public void setServiceCatalog(final List<EndPointDescription> serviceCatalog) {
		this.serviceCatalog = serviceCatalog;
	}

	public void setToken(final Token token) {
		this.token = token;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
