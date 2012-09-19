package com.cpn.os4j.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class IPAddress implements Serializable {

	private static final long serialVersionUID = -6192279024256341201L;
	String id;
	int version;
	String addr;
	String ip;
	@JsonProperty("fixed_ip")
	String fixedIp;
	@JsonProperty("instance_id")
	String instanceId;
	String pool;

	public String getAddr() {
		return addr;
	}

	public String getFixedIp() {
		return fixedIp;
	}

	public String getId() {
		return id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getIp() {
		return ip;
	}

	public String getPool() {
		return pool;
	}

	public int getVersion() {
		return version;
	}

	public void setAddr(final String addr) {
		this.addr = addr;
	}

	public void setFixedIp(final String fixedIp) {
		this.fixedIp = fixedIp;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setInstanceId(final String instanceId) {
		this.instanceId = instanceId;
	}

	public void setIp(final String ip) {
		this.ip = ip;
	}

	public void setPool(final String pool) {
		this.pool = pool;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("id", id).append("version", version).append("addr", addr).append("ip", ip).append("fixedIp", fixedIp).append("instanceId", instanceId).append("pool", pool);
		return builder.toString();
	}
}
