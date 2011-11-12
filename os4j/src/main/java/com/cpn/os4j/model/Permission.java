package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Permission implements Serializable {
	public static Permission unmarshall(final Node aNode, final OpenStack anEndPoint) {
		final Permission p = new Permission();
		final XMLUtil n = new XMLUtil(aNode);
		try {
			p.toPort = n.getInteger("toPort");

			p.ipProtocol = n.get("ipProtocol");
			p.fromPort = n.getInteger("fromPort");
			p.ipRanges = n.getStringList("ipRanges/item/cidrIp/text()");
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return p;
	}

	private int fromPort;
	private String ipProtocol;
	private List<String> ipRanges = new ArrayList<>();

	private int toPort;

	private Permission() {

	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("toPort", toPort).append("ipProtocol", ipProtocol).append("ipRanges", ipRanges).append("fromPort", fromPort);
		return builder.toString();
	}

}
