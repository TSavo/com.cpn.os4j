package com.cpn.os4j;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.w3c.dom.Node;

import com.cpn.os4j.cache.Cacheable;
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

}
