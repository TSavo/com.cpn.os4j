package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Permission implements Serializable {
	private int toPort;
	private String ipProtocol;
	private List<String> ipRanges = new ArrayList<>();
	private int fromPort;

	private Permission() {

	}

	public static Permission unmarshall(Node aNode, OpenStack anEndPoint) {
		Permission p = new Permission();
		XMLUtil n = new XMLUtil(aNode);
		try {
			p.toPort = n.getInteger("toPort");

			p.ipProtocol = n.get("ipProtocol");
			p.fromPort = n.getInteger("fromPort");
			p.ipRanges = n.getStringList("ipRanges/item/cidrIp/text()");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return p;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("toPort", toPort).append("ipProtocol", ipProtocol).append("ipRanges", ipRanges).append("fromPort", fromPort);
		return builder.toString();
	}

}
