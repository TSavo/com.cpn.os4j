package com.cpn.os4j.model;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.w3c.dom.Node;

import com.cpn.cache.Cacheable;
import com.cpn.os4j.EndPoint;
import com.cpn.os4j.command.ServerErrorException;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class IPAddress implements Cacheable<String> {

	public static IPAddress unmarshall(final Node aNode, final EndPoint anEndPoint) {
		final IPAddress ip = new IPAddress(anEndPoint);
		final XMLUtil n = new XMLUtil(aNode);
		try {
			ip.ipAddress = n.get("publicIp");
			ip.instanceId = n.get("instanceId").replaceAll(" \\(vsp\\)", "");
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return ip;
	}

	private final EndPoint endPoint;
	private String instanceId;

	private String ipAddress;

	private IPAddress(final EndPoint anEndPoint) {
		endPoint = anEndPoint;
	}

	public IPAddress associateWithInstance(final Instance anInstance) throws ServerErrorException, IOException {
		endPoint.associateAddress(anInstance, this);
		return this;
	}

	public IPAddress disassociate() throws ServerErrorException, IOException {
		endPoint.disassociateAddress(this);
		return this;
	}

	@JsonIgnore
	public Instance getInstance() throws ServerErrorException, IOException {
		return endPoint.getInstance(instanceId);
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	@Override
	public String getKey() {
		return getIpAddress();
	}

	public EndPoint release() throws ServerErrorException, IOException {
		return endPoint.releaseAddress(this);
	}

	public void setInstanceId(final String instanceId) {
		this.instanceId = instanceId;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("ipAddress", ipAddress).append("instanceId", instanceId);
		return builder.toString();
	}
}
