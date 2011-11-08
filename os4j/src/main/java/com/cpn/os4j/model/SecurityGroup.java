package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
public class SecurityGroup implements Cacheable<String> {

	private String groupName, groupDescription, ownerId;
	private List<Permission> permissions = new ArrayList<>();

	@Override
	public String getKey() {
		return getGroupName();
	}

	public String getGroupName() {
		return groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("groupName", groupName).append("groupDescription", groupDescription).append("ownerId", ownerId).append("permissions", permissions);
		return builder.toString();
	}

	public static SecurityGroup unmarshall(Node aNode, OpenStack anEndPoint) {
		SecurityGroup sg = new SecurityGroup();
		XMLUtil x = new XMLUtil(aNode);
		try {
			sg.groupName = x.get("groupName");
			sg.groupDescription = x.get("groupDescription");
			sg.ownerId = x.get("ownerId");
			for (Node n : x.getList("ipPermissions/item")) {
				sg.permissions.add(Permission.unmarshall(n, anEndPoint));
			}
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return sg;
	}

	
	@Immutable
	public static class Permission implements Serializable {
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
}
