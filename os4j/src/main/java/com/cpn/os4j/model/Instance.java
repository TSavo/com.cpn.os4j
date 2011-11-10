package com.cpn.os4j.model;

import java.io.IOException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.command.GetConsoleOutputCommand;
import com.cpn.os4j.command.ServerErrorExeception;
import com.cpn.os4j.model.Volume.VolumeAttachment;
import com.cpn.os4j.model.cache.Cacheable;
import com.cpn.os4j.util.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Instance implements Cacheable<String> {

	private String displayName, rootDeviceType, keyName, instanceId, instanceState, publicDnsName, imageId, privateDnsName, launchTime, amiLaunchIndex, rootDeviceName, ramdiskId, ipAddress, instanceType, privateIpAddress;

	private OpenStack endPoint;

	private Instance(OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	@Override
	public String getKey() {
		return instanceId;
	}

	public OpenStack getEndPoint() {
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

	public Instance reboot() throws ServerErrorExeception, IOException {
		endPoint.rebootInstance(this);
		return this;
	}

	public ConsoleOutput getConsoleOutput() throws ServerErrorExeception, IOException {
		return new GetConsoleOutputCommand(endPoint, this).execute().get(0);
	}

	public Instance terminate() throws ServerErrorExeception, IOException {
		endPoint.terminateInstance(this);
		return this;
	}

	public Instance waitUntilRunning() throws InterruptedException, ServerErrorExeception, IOException {
		if (instanceState.equals("running")) {
			return this;
		}
		Thread.sleep(1000);
		endPoint.getInstances();
		return endPoint.getInstanceCache().get(getKey()).waitUntilRunning();
	}

	public Instance waitUntilRunning(long maxTimeToWait) throws InterruptedException, ServerErrorExeception, IOException {
		if (instanceState.equals("running")) {
			return this;
		}
		if (maxTimeToWait < 0) {
			return this;
		}
		Thread.sleep(1000);
		endPoint.getInstances();
		return endPoint.getInstanceCache().get(getKey()).waitUntilRunning(maxTimeToWait - 1000);
	}

	public Instance waitUntilTerminated() throws InterruptedException, ServerErrorExeception, IOException {
		endPoint.getInstances();
		if (endPoint.getInstanceCache().get(getKey()) != null) {
			Thread.sleep(1000);
			endPoint.getInstances();
		}
		return this;
	}

	public Instance waitUntilTerminated(long maxTimeToWait) throws InterruptedException, ServerErrorExeception, IOException {
		endPoint.getInstances();
		while (endPoint.getInstanceCache().get(getKey()) != null) {
			Thread.sleep(1000);
			endPoint.getInstances();
			maxTimeToWait -= 1000;
			if (maxTimeToWait < 0) {
				return this;
			}
		}
		return this;
	}

	public Instance associateAddress(IPAddress anAddress) throws ServerErrorExeception, IOException {
		endPoint.associateAddress(this, anAddress);
		return this;
	}

	public Instance disassociateAddress() throws ServerErrorExeception, IOException {
		endPoint.disassociateAddress(getIpAddress());
		return this;
	}

	public Instance attachVolume(Volume aVolume, String aDevice) throws ServerErrorExeception, IOException {
		endPoint.attachVolumeToInstance(aVolume, this, aDevice);
		return this;
	}

	public Instance detachVolume() throws ServerErrorExeception, IOException {
		Volume v = getVolume();
		if (v != null) {
			endPoint.detachVolume(v);
		}
		return this;
	}

	public Volume getVolume() throws ServerErrorExeception, IOException {
		List<Volume> vols = endPoint.getVolumes();
		for (Volume v : vols) {
			for (VolumeAttachment a : v.getVolumeAttachments()) {
				if (getInstanceId().equals(a.getInstanceId())) {
					return v;
				}
			}
		}
		return null;
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

	public IPAddress getIpAddress() {
		return endPoint.getIPAddressCache().get(ipAddress);
	}

	public String getInstanceType() {
		return instanceType;
	}

	public String getPrivateIpAddress() {
		return privateIpAddress;
	}

	public Instance setIPAddress(String anAddress) {
		ipAddress = anAddress;
		return this;
	}
}
