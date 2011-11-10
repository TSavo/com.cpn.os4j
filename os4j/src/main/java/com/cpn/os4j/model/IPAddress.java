package com.cpn.os4j.model;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.command.ServerErrorExeception;
import com.cpn.os4j.model.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class IPAddress implements Cacheable<String> {

	private String ipAddress;
	private String instanceId;
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	private OpenStack endPoint;

	private IPAddress(OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	@Override
	public String getKey() {
		return getIpAddress();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public Instance getInstance() {
		return endPoint.getInstanceCache().get(instanceId);
	}

	public OpenStack release() throws ServerErrorExeception, IOException {
		return endPoint.releaseAddress(this);
	}

	public IPAddress associateWithInstance(Instance anInstance) throws ServerErrorExeception, IOException {
		endPoint.associateAddress(anInstance, this);
		return this;
	}
	
	public IPAddress disassociate()throws ServerErrorExeception, IOException {
		endPoint.disassociateAddress(this);
		return this;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("ipAddress", ipAddress).append("instanceId", instanceId);
		return builder.toString();
	}

	public static IPAddress unmarshall(Node aNode, OpenStack anEndPoint) {
		IPAddress ip = new IPAddress(anEndPoint);
		XMLUtil n = new XMLUtil(aNode);
		try {
			ip.ipAddress = n.get("publicIp");
			ip.instanceId = n.get("instanceId").replaceAll(" \\(vsp\\)", "");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return ip;
	}
}
