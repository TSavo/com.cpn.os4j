package com.cpn.os4j.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class IPAddress {
	int version;
	String addr;
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("version", version).append("addr", addr);
		return builder.toString();
	}
}
