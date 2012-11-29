package com.cpn.os4j.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class FullServerConfiguration implements Serializable, ServerConfiguration {

	private static final long serialVersionUID = -5963753281735130626L;
	String name;
	String imageRef;
	String flavorRef;
	String accessIPv4;
	@JsonProperty("key_name")
	String keyName;
	@JsonProperty("user_data")
	String userData;
	@JsonProperty("max_count")
	Integer maxCount;
	@JsonProperty("min_count")
	Integer minCount;
	Map<String, String> metadata;
	List<SerializedFile> personality;
	List<NetworkInterface> networks;

	public FullServerConfiguration() {
	}

	public FullServerConfiguration(final String aName, final IPAddress anIpAddress, final Image anImage, final Flavor aFlavor, final Map<String, String> someMetadata, final List<SerializedFile> aPersonality, final String aKeyName, final String aUserData) {
		this(aName, anIpAddress.getIp(), anImage.getSelfRef(), aFlavor.getSelfRef(), someMetadata, aPersonality, aKeyName, aUserData);
	}

	public FullServerConfiguration(final String aName, final String anIpAddress, final String anImageRef, final String aFlavorRef, final Map<String, String> someMetadata, final List<SerializedFile> aPersonality, final String aKeyName, final String aUserData) {
		name = aName;
		imageRef = anImageRef;
		flavorRef = aFlavorRef;
		metadata = someMetadata;
		personality = aPersonality;
		accessIPv4 = anIpAddress;
		keyName = aKeyName;
		userData = aUserData;
	}
	
	public FullServerConfiguration(final String aName, final IPAddress anIpAddress, final Image anImage, final Flavor aFlavor, final Map<String, String> someMetadata, final List<SerializedFile> aPersonality, final String aKeyName, final String aUserData, final Integer aMaxCount, final Integer aMinCount, final List<NetworkInterface> someNetworks) {
		this(aName, anIpAddress.getIp(), anImage.getSelfRef(), aFlavor.getSelfRef(), someMetadata, aPersonality, aKeyName, aUserData, aMaxCount, aMinCount, someNetworks);
	}

	public FullServerConfiguration(final String aName, final String anIpAddress, final String anImageRef, final String aFlavorRef, final Map<String, String> someMetadata, final List<SerializedFile> aPersonality, final String aKeyName, final String aUserData, final Integer aMaxCount, final Integer aMinCount, final List<NetworkInterface> someNetworks) {
		name = aName;
		imageRef = anImageRef;
		flavorRef = aFlavorRef;
		metadata = someMetadata;
		personality = aPersonality;
		accessIPv4 = anIpAddress;
		keyName = aKeyName;
		userData = aUserData;
		maxCount = aMaxCount;
		minCount = aMinCount;
		networks = someNetworks;
	}

	public String getAccessIPv4() {
		return accessIPv4;
	}

	public String getFlavorRef() {
		return flavorRef;
	}

	public String getImageRef() {
		return imageRef;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public String getName() {
		return name;
	}

	public List<SerializedFile> getPersonality() {
		return personality;
	}

	public void setAccessIPv4(final String accessIPv4) {
		this.accessIPv4 = accessIPv4;
	}

	public void setFlavorRef(final String flavorRef) {
		this.flavorRef = flavorRef;
	}

	public void setImageRef(final String imageRef) {
		this.imageRef = imageRef;
	}

	public void setMetadata(final Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPersonality(final List<SerializedFile> personality) {
		this.personality = personality;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(final Integer maxCount) {
		this.maxCount = maxCount;
	}

	public Integer getMinCount() {
		return minCount;
	}

	public void setMinCount(final Integer minCount) {
		this.minCount = minCount;
	}

	public List<NetworkInterface> getNetworks() {
		return networks;
	}

	public void setNetworks(final List<NetworkInterface> networks) {
		this.networks = networks;
	}

}
