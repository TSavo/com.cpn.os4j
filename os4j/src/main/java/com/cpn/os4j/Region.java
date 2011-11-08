package com.cpn.os4j;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Region implements Cacheable<String> {

	private String regionName;
	
	private String regionEndpoint;

	private OpenStack endPoint;

	private Region(OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	@Override
	public String getKey() {
		return regionName;
	}
	
	public String getRegionName() {
		return regionName;
	}

	public OpenStack getEndPoint(){
		return endPoint;
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("regionName", regionName).append("regionEndpoint", regionEndpoint).toString();
	}

	public static Region unmarshall(Node node, OpenStack anEndPoint) {
		Region r = new Region(anEndPoint);
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
