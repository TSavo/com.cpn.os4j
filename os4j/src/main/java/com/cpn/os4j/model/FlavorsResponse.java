package com.cpn.os4j.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class FlavorsResponse {
	List<Flavor> flavors;

	public List<Flavor> getFlavors() {
		return flavors;
	}

	public void setFlavors(final List<Flavor> flavors) {
		this.flavors = flavors;
	}

	
	
	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("flavors", flavors);
		return builder.toString();
	}

}
