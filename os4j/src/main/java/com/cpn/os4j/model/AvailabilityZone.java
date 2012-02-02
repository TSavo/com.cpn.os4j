package com.cpn.os4j.model;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.w3c.dom.Node;

import com.cpn.cache.Cacheable;
import com.cpn.os4j.EndPoint;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
public class AvailabilityZone implements Cacheable<String> {

	public AvailabilityZone(){
		
	}
	
	public AvailabilityZone(String aZoneName){
		name = aZoneName;
		status = "available";
	}
	public static AvailabilityZone unmarshall(final Node aNode, final EndPoint anOpenStack) {
		final AvailabilityZone p = new AvailabilityZone();
		final XMLUtil x = new XMLUtil(aNode);

		try {
			p.name = x.get("zoneName");
			p.status = x.get("zoneState");
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return p;

	}

	private String name, status;

	public String getStatus() {
		return status;
	}

	@Override
	@JsonIgnore
	public String getKey() {
		return getName();
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("name", name).append("status", status);
		return builder.toString();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
