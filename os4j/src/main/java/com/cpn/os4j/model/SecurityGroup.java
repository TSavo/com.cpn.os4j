package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.cache.Cacheable;
import com.cpn.os4j.EndPoint;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
public class SecurityGroup implements Cacheable<String> {

	@Immutable
	public static class Permission implements Serializable {
		public static Permission unmarshall(final Node aNode, final EndPoint anEndPoint) {
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

	public static SecurityGroup unmarshall(final Node aNode, final EndPoint anEndPoint) {
		final SecurityGroup sg = new SecurityGroup();
		final XMLUtil x = new XMLUtil(aNode);
		try {
			sg.groupName = x.get("groupName");
			sg.groupDescription = x.get("groupDescription");
			sg.ownerId = x.get("ownerId");
			for (final Node n : x.getList("ipPermissions/item")) {
				sg.permissions.add(Permission.unmarshall(n, anEndPoint));
			}
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return sg;
	}

	private String groupName, groupDescription, ownerId;

	private final List<Permission> permissions = new ArrayList<>();

	public String getGroupDescription() {
		return groupDescription;
	}

	public String getGroupName() {
		return groupName;
	}

	@Override
	public String getKey() {
		return getGroupName();
	}

	public String getOwnerId() {
		return ownerId;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("groupName", groupName).append("groupDescription", groupDescription).append("ownerId", ownerId).append("permissions", permissions);
		return builder.toString();
	}
}
