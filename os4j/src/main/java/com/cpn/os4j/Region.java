package com.cpn.os4j;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Node;

import com.cpn.os4j.util.XMLUtil;

public class Region {

	public String regionName;
	public String regionEndpoint;

	public String toString() {
		return new ToStringBuilder(this).append("regionName", regionName).append("regionEndpoint", regionEndpoint).toString();
	}

	public static List<Region> unmarshall(List<Node> aList) {
		ArrayList<Region> list = new ArrayList<Region>();
		for (Node n : aList) {
			list.add(unmarshall(n));
		}
		return list;
	}

	public static Region unmarshall(Node node) {
		Region r = new Region();
		XMLUtil u = new XMLUtil(node);
		try {
			r.regionEndpoint = u.get("regionEndpoint");
			r.regionName = u.get("regionName");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return r;
	}

}
