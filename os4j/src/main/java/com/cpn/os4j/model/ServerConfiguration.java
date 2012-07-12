package com.cpn.os4j.model;

import java.util.List;
import java.util.Map;

public class ServerConfiguration {

	String name;
	String imageRef;
	String flavorRef;
	
	Map<String, String> metadata;
	List<SerializedFile> personality;

	public ServerConfiguration() {
	}

	public ServerConfiguration(String aName, String anImageRef, String aFlavorRef, Map<String, String> someMetadata, List<SerializedFile> aPersonality) {
		name = aName;
		imageRef = anImageRef;
		flavorRef = aFlavorRef;
		metadata = someMetadata;
		personality = aPersonality;
	}

	public ServerConfiguration(String aName, Image anImage, Flavor aFlavor, Map<String, String> someMetadata, List<SerializedFile> aPersonality) {
		this(aName, anImage.getSelfRef(), aFlavor.getSelfRef(), someMetadata, aPersonality);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}

	public String getFlavorRef() {
		return flavorRef;
	}

	public void setFlavorRef(String flavorRef) {
		this.flavorRef = flavorRef;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public List<SerializedFile> getPersonality() {
		return personality;
	}

	public void setPersonality(List<SerializedFile> personality) {
		this.personality = personality;
	}


}
