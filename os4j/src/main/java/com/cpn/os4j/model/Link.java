package com.cpn.os4j.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Link implements Serializable {

	private static final long serialVersionUID = -1886401695620567182L;
	String href;
	String rel;
	String type;

	public String getHref() {
		return href;
	}

	public String getRel() {
		return rel;
	}

	public String getType() {
		return type;
	}

	public void setHref(final String href) {
		this.href = href;
	}

	public void setRel(final String rel) {
		this.rel = rel;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("href", href).append("rel", rel).append("type", type);
		return builder.toString();
	}

}
