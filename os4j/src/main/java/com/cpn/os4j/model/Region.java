package com.cpn.os4j.model;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.cache.Cacheable;
import com.cpn.os4j.OpenStack;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Region implements Cacheable<String> {

	public static Region unmarshall(final Node node, final OpenStack anEndPoint) {
		final Region r = new Region(anEndPoint);
		final XMLUtil u = new XMLUtil(node);
		try {
			r.regionEndpoint = u.get("regionEndpoint");
			r.regionName = u.get("regionName");
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return r;
	}

	private final OpenStack endPoint;

	private String regionEndpoint;

	private String regionName;

	private Region(final OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	public OpenStack getEndPoint() {
		return endPoint;
	}

	@Override
	public String getKey() {
		return regionName;
	}

	public String getRegionName() {
		return regionName;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("regionName", regionName).append("regionEndpoint", regionEndpoint).toString();
	}

}
