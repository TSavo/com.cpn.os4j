package com.cpn.os4j;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.util.XMLUtil;

@Immutable
public class VolumeAttachment {
	private String status, instanceId, volumeId, deleteOnTermination, device, attachTime;
	private OpenStack endPoint;


	private VolumeAttachment(OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	public static VolumeAttachment unmarshall(Node aNode, OpenStack anEndPoint) {
		VolumeAttachment v = new VolumeAttachment(anEndPoint);
		XMLUtil x = new XMLUtil(aNode);
		try {
			v.status = x.get("status");
			v.attachTime = x.get("attachTime");
			v.instanceId = x.get("instanceId");
			v.volumeId = x.get("volumeId");
			v.deleteOnTermination = x.get("deleteOnTermination");
			v.device = x.get("device");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return v;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("status", status).append("instanceId", instanceId).append("volumeId", volumeId).append("deleteOnTermination", deleteOnTermination).append("device", device).append("attachTime", attachTime);
		return builder.toString();
	}

	public String getStatus() {
		return status;
	}

	public String getAttachTime() {
		return attachTime;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public Instance getInstance() {
		return endPoint.getInstanceCache().get(instanceId);
	}

	public String getVolumeId() {
		return volumeId;
	}
	
	public Volume getVolume(){
		return endPoint.getVolumeCache().get(volumeId);
	}

	public String getDeleteOnTermination() {
		return deleteOnTermination;
	}

	public String getDevice() {
		return device;
	}

}
