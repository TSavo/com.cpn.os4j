package com.cpn.os4j.model;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.http.annotation.Immutable;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.w3c.dom.Node;

import com.cpn.cache.Cacheable;
import com.cpn.os4j.EndPoint;
import com.cpn.os4j.command.ServerErrorExeception;
import com.cpn.xml.XMLUtil;

@SuppressWarnings("serial")
@Immutable
@JsonIgnoreProperties(ignoreUnknown=true)
public class Image implements Cacheable<String> {

	public static Image unmarshall(final Node aNode, final EndPoint anEndPoint) {
		final Image i = new Image(anEndPoint);
		final XMLUtil n = new XMLUtil(aNode);
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
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return i;
	}

	private String displayName, description, imageOwnerId, imageId, imageState, architecture, imageLocation, rootDeviceType, rootDeviceName, imageType;

	private final EndPoint endPoint;

	private boolean isPublic;

	public Image(){
		endPoint = null;
	}
	private Image(final EndPoint anEndPoint) {
		endPoint = anEndPoint;
	}

	public String getArchitecture() {
		return architecture;
	}

	public String getDescription() {
		return description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getImageId() {
		return imageId;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public String getImageOwnerId() {
		return imageOwnerId;
	}

	public String getImageState() {
		return imageState;
	}

	public String getImageType() {
		return imageType;
	}

	@Override
	public String getKey() {
		return getImageId();
	}

	public String getRootDeviceName() {
		return rootDeviceName;
	}

	public String getRootDeviceType() {
		return rootDeviceType;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public Instance runInstance(final KeyPair keyPair, final String instanceType, final String addressingType, final int minCount, final int maxCount, String aUserData, String anAvailabilityZone, final SecurityGroup... groups) throws ServerErrorExeception, IOException {
		return endPoint.runInstance(this, keyPair, instanceType, addressingType, minCount, maxCount, aUserData, anAvailabilityZone, groups);
	}

	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("displayName", displayName).append("description", description).append("imageOwnerId", imageOwnerId).append("isPublic", isPublic).append("imageId", imageId).append("imageState", imageState).append("architecture", architecture)
				.append("imageLocation", imageLocation).append("rootDeviceType", rootDeviceType).append("rootDeviceName", rootDeviceName).append("imageType", imageType);
		return builder.toString();
	}
}
