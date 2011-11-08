package com.cpn.os4j.model;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.command.GetConsoleOutputCommand;
import com.cpn.os4j.command.RebootInstancesCommand;
import com.cpn.os4j.model.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Instance implements Cacheable<String>{

	private String displayName, rootDeviceType, keyName, instanceId, instanceState, publicDnsName, imageId, privateDnsName, launchTime, amiLaunchIndex, rootDeviceName, ramdiskId, ipAddress, instanceType, privateIpAddress;

	private OpenStack endPoint;

	private Instance(OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	@Override
	public String getKey() {
		return instanceId;
	}
	
	
	public OpenStack getEndPoint(){
		return endPoint;
	}
	
	public static Instance unmarshall(Node aNode, OpenStack anEndPoint) {
		Instance i = new Instance(anEndPoint);
		XMLUtil r = new XMLUtil(aNode);
		try {
			i.displayName = r.get("displayName");
			i.rootDeviceType = r.get("rootDeviceType");
			i.keyName = r.get("keyName");
			i.instanceId = r.get("instanceId");
			i.instanceState = r.get("instanceState/name");
			i.publicDnsName = r.get("publicDnsName");
			i.privateDnsName = r.get("privateDnsName");
			i.amiLaunchIndex = r.get("amiLaunchIndex");
			i.rootDeviceName = r.get("rootDeviceName");
			i.imageId = r.get("imageId");
			i.launchTime = r.get("launchTime");
			i.ramdiskId = r.get("ramdiskId");
			i.ipAddress = r.get("ipAddress");
			i.instanceType = r.get("instanceType");
			i.privateIpAddress = r.get("privateIpAddress");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return i;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("displayName", displayName).append("rootDeviceType", rootDeviceType).append("keyName", keyName).append("instanceId", instanceId).append("instanceState", instanceState).append("publicDnsName", publicDnsName).append("imageId", imageId)
				.append("privateDnsName", privateDnsName).append("launchTime", launchTime).append("amiLaunchIndex", amiLaunchIndex).append("rootDeviceName", rootDeviceName).append("ramdiskId", ramdiskId).append("ipAddress", ipAddress)
				.append("instanceType", instanceType).append("privateIpAddress", privateIpAddress);
		return builder.toString();
	}

	public Instance reboot(){
		new RebootInstancesCommand(endPoint, this).execute();
		endPoint.getInstances();
		return this;
	}
	
	public ConsoleOutput getConsoleOutput(){
		return new GetConsoleOutputCommand(endPoint, this).execute().get(0);
	}
	public String getDisplayName() {
		return displayName;
	}

	public String getRootDeviceType() {
		return rootDeviceType;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getInstanceState() {
		return instanceState;
	}

	public String getPublicDnsName() {
		return publicDnsName;
	}

	public String getImageId() {
		return imageId;
	}

	public String getPrivateDnsName() {
		return privateDnsName;
	}

	public String getLaunchTime() {
		return launchTime;
	}

	public String getAmiLaunchIndex() {
		return amiLaunchIndex;
	}

	public String getRootDeviceName() {
		return rootDeviceName;
	}

	public String getRamdiskId() {
		return ramdiskId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getInstanceType() {
		return instanceType;
	}

	public String getPrivateIpAddress() {
		return privateIpAddress;
	}

}
