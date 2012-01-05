package com.cpn.os4j.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.w3c.dom.Node;

import com.cpn.cache.Cacheable;
import com.cpn.os4j.EndPoint;
import com.cpn.os4j.command.GetConsoleOutputCommand;
import com.cpn.os4j.command.ServerErrorExeception;
import com.cpn.os4j.model.Volume.VolumeAttachment;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
@Immutable
public class Instance implements Cacheable<String> {

	public static Instance unmarshall(final Node aNode, final EndPoint anEndPoint) {
		final Instance i = new Instance(anEndPoint);
		final XMLUtil r = new XMLUtil(aNode);
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
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return i;
	}

	private String displayName, rootDeviceType, keyName, instanceId, instanceState, publicDnsName, imageId, privateDnsName, launchTime, amiLaunchIndex, rootDeviceName, ramdiskId, ipAddress, instanceType, privateIpAddress;
	@JsonIgnore
	private final EndPoint endPoint;

	private Instance(final EndPoint anEndPoint) {
		endPoint = anEndPoint;
	}

	public Instance associateAddress(final IPAddress anAddress) throws ServerErrorExeception, IOException {
		endPoint.associateAddress(this, anAddress);
		return this;
	}

	public Instance attachVolume(final Volume aVolume, final String aDevice) throws ServerErrorExeception, IOException {
		VolumeAttachment attachment = endPoint.attachVolumeToInstance(aVolume, this, aDevice);
		aVolume.addVolumeAttachment(attachment);
		return this;
	}

	public Instance detachVolume() throws ServerErrorExeception, IOException {
		final Volume v = getVolume();
		if (v != null) {
			endPoint.detachVolume(v);
		}
		return this;
	}

	public Instance disassociateAddress() throws ServerErrorExeception, IOException {
		if(getIpAddress() != null){
			endPoint.disassociateAddress(getIpAddress());
		}
		return this;
	}

	public String getAmiLaunchIndex() {
		return amiLaunchIndex;
	}

	@JsonIgnore
	public ConsoleOutput getConsoleOutput() throws ServerErrorExeception, IOException {
		return new GetConsoleOutputCommand(endPoint, this).execute().get(0);
	}

	public String getDisplayName() {
		return displayName;
	}

	@JsonIgnore
	public EndPoint getEndPoint() {
		return endPoint;
	}

	public String getImageId() {
		return imageId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getInstanceState() {
		return instanceState;
	}

	public String getInstanceType() {
		return instanceType;
	}

	public IPAddress getIpAddress() {
		return endPoint.getIPAddressCache().get(ipAddress);
	}

	@Override
	public String getKey() {
		return instanceId;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getLaunchTime() {
		return launchTime;
	}

	public String getPrivateDnsName() {
		return privateDnsName;
	}

	public String getPrivateIpAddress() {
		return privateIpAddress;
	}

	public String getPublicDnsName() {
		return publicDnsName;
	}

	public String getRamdiskId() {
		return ramdiskId;
	}

	public String getRootDeviceName() {
		return rootDeviceName;
	}

	public String getRootDeviceType() {
		return rootDeviceType;
	}

	public Volume getVolume() throws ServerErrorExeception, IOException {
		final List<Volume> vols = endPoint.getVolumes();
		for (final Volume v : vols) {
			for (final VolumeAttachment a : v.getVolumeAttachments()) {
				if (getInstanceId().equals(a.getInstanceId())) {
					return v;
				}
			}
		}
		return null;
	}
	
	public List<Volume> getVolumes() throws IOException {
		final List<Volume> myVols = new ArrayList<>();
		final List<Volume> vols = endPoint.getVolumes();
		for(final Volume v: vols){
			for(final VolumeAttachment a : v.getVolumeAttachments()) {
				if(getInstanceId().equals(a.getInstanceId())){
					myVols.add(v);
				}
			}
		}
		return myVols;
	}

	public Instance reboot() throws ServerErrorExeception, IOException {
		endPoint.rebootInstance(this);
		instanceState = "rebooting";
		return this;
	}

	public Instance setIPAddress(final String anAddress) {
		ipAddress = anAddress;
		return this;
	}

	public Instance terminate() throws ServerErrorExeception, IOException {
		endPoint.terminateInstance(this);
		instanceState = "terminating";
		return this;
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("displayName", displayName).append("rootDeviceType", rootDeviceType).append("keyName", keyName).append("instanceId", instanceId).append("instanceState", instanceState).append("publicDnsName", publicDnsName).append("imageId", imageId)
				.append("privateDnsName", privateDnsName).append("launchTime", launchTime).append("amiLaunchIndex", amiLaunchIndex).append("rootDeviceName", rootDeviceName).append("ramdiskId", ramdiskId).append("ipAddress", ipAddress)
				.append("instanceType", instanceType).append("privateIpAddress", privateIpAddress);
		return builder.toString();
	}

	public Instance waitUntilRunning() throws InterruptedException, ServerErrorExeception, IOException {
		if (instanceState.equals("running")) {
			return this;
		}
		Thread.sleep(1000);
		endPoint.getInstances();
		return endPoint.getInstanceCache().get(getKey()).waitUntilRunning();
	}

	public Instance waitUntilRunning(final long maxTimeToWait) throws InterruptedException, ServerErrorExeception, IOException {
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
		instanceState = "terminated";
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
		instanceState = "terminated";
		return this;
	}
}
