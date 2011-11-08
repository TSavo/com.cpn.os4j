package com.cpn.os4j;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.w3c.dom.Node;

import com.cpn.os4j.cache.Cacheable;
import com.cpn.os4j.command.RunInstancesCommand;
import com.cpn.os4j.util.XMLUtil;

@Immutable
public class Image implements Cacheable<String> {

	private String displayName, description, imageOwnerId, imageId, imageState, architecture, imageLocation, rootDeviceType, rootDeviceName, imageType;
	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public String getImageOwnerId() {
		return imageOwnerId;
	}

	public String getImageId() {
		return imageId;
	}

	public String getImageState() {
		return imageState;
	}

	public String getArchitecture() {
		return architecture;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public String getRootDeviceType() {
		return rootDeviceType;
	}

	public String getRootDeviceName() {
		return rootDeviceName;
	}

	public String getImageType() {
		return imageType;
	}

	public boolean isPublic() {
		return isPublic;
	}


	private OpenStack endPoint;
	private boolean isPublic;

	private Image(OpenStack anEndPoint) {
		endPoint = anEndPoint;
	}

	@Override
	public String getKey() {
		return getImageId();
	}

	public Image runInstance(KeyPair keyPair, String instanceType, String addressingType, String minCount, String maxCount, SecurityGroup... groups){
		new RunInstancesCommand(endPoint, this, keyPair, instanceType, addressingType, minCount, maxCount, groups).execute();
		return this;
	}
	
	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("displayName", displayName).append("description", description).append("imageOwnerId", imageOwnerId).append("isPublic", isPublic).append("imageId", imageId).append("imageState", imageState).append("architecture", architecture)
				.append("imageLocation", imageLocation).append("rootDeviceType", rootDeviceType).append("rootDeviceName", rootDeviceName).append("imageType", imageType);
		return builder.toString();
	}
	

	public static Image unmarshall(Node aNode, OpenStack anEndPoint) {
		Image i = new Image(anEndPoint);
		XMLUtil n = new XMLUtil(aNode);
		try {
			i.displayName = n.get("displayName");
			i.description = n.get("description");
			i.imageOwnerId = n.get("imageOwnerId");
			i.isPublic = new Boolean(n.get("isPublic"));
			i.imageId = n.get("imageId");
			i.imageState = n.get("imageState");
			i.architecture = n.get("architecture");
			i.imageLocation = n.get("imageLocation");
			i.rootDeviceType = n.get("rootDeviceType");
			i.rootDeviceName = n.get("rootDeviceName");
			i.imageType = n.get("imageType");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return i;
	}
}
