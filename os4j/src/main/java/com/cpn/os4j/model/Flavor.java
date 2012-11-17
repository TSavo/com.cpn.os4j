package com.cpn.os4j.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flavor extends AbstractOpenStackModel {

	private static final long serialVersionUID = 1264918407525243722L;
	String id;
	String name;
	int ram;
	int disk;
	int vcpus;
	@JsonProperty("rxtx_factor")
	float rxtxFactor;
	@JsonProperty("OS-FLV-EXT-DATA:ephemeral")
	int ephemeral;
	String swap;

	public int getDisk() {
		return disk;
	}

	public int getEphemeral() {
		return ephemeral;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getRam() {
		return ram;
	}

	public float getRxtxFactor() {
		return rxtxFactor;
	}

	public String getSwap() {
		return swap;
	}

	public int getVcpus() {
		return vcpus;
	}

	public void setDisk(final int disk) {
		this.disk = disk;
	}

	public void setEphemeral(final int ephemeral) {
		this.ephemeral = ephemeral;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRam(final int ram) {
		this.ram = ram;
	}

	public void setRxtxFactor(final float rxtxFactor) {
		this.rxtxFactor = rxtxFactor;
	}

	public void setSwap(final String swap) {
		this.swap = swap;
	}

	public void setVcpus(final int vcpus) {
		this.vcpus = vcpus;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("links", links).append("name", name).append("ram", ram).append("disk", disk).append("vcpus", vcpus).append("rxtxFactor", rxtxFactor).append("ephemeral", ephemeral).append("swap", swap);
		return builder.toString();
	}

}
