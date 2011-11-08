package com.cpn.os4j.command;

import java.util.ArrayList;
import java.util.List;

import com.cpn.os4j.Image;
import com.cpn.os4j.KeyPair;
import com.cpn.os4j.OpenStack;
import com.cpn.os4j.SecurityGroup;

public class RunInstancesCommand extends AbstractOpenStackCommand<Object> {

	private String addressingType;
	public String getAddressingType() {
		return addressingType;
	}

	public String getImageId() {
		return imageId;
	}

	public String getInstanceType() {
		return instanceType;
	}

	public String getKernelId() {
		return kernelId;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public String getMinCount() {
		return minCount;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public String getRamdiskId() {
		return ramdiskId;
	}

	public List<SecurityGroup> getSecurityGroups() {
		return securityGroups;
	}

	private String imageId;
	private String instanceType;
	private String kernelId;
	private String keyName;
	private String maxCount;
	private String minCount;
	private String availabilityZone;
	private String ramdiskId;
	private List<SecurityGroup> securityGroups = new ArrayList<>();

	
	public RunInstancesCommand(OpenStack anEndPoint, Image anImage, KeyPair keyPair, /*String kernelId, */String instanceType, String addressingType, String minCount, String maxCount, /*String ramdiskId, String availabilityZone, */ SecurityGroup... groups) {
		super(anEndPoint);
		this.imageId = anImage.getImageId();
		//this.kernelId = kernelId;
		this.instanceType = instanceType;
		this.addressingType = addressingType;
		this.minCount = minCount;
		this.maxCount = maxCount;
		//this.ramdiskId = ramdiskId;
		//this.availabilityZone = availabilityZone;
		this.keyName = keyPair.getName();
		for(SecurityGroup sg : groups){
			securityGroups.add(sg);
		}
	}
	
	public RunInstancesCommand addSecurityGroup(SecurityGroup... groups){
		for(SecurityGroup sg : groups){
			securityGroups.add(sg);
		}
		return this;
	}

	@Override
	public String getAction() {
		return "RunInstances";
	}
	
	@Override
	public Class<Object> getUnmarshallingClass() {
		return null;
	}
	
	public String getUnmarshallingXPath() {
		return null;
	};
	
	@Override
	public List<Object> execute() {
		queryString.put("AddressingType", addressingType);
		queryString.put("ImageId", imageId);
		queryString.put("InstanceType", instanceType);
		//queryString.put("KernelId", kernelId);
		queryString.put("KeyName", keyName);
		queryString.put("MaxCount", maxCount);
		queryString.put("MinCount", minCount);
		//queryString.put("Placement.AvailabilityZone", availabilityZone);
		//queryString.put("RamdiskId", ramdiskId);
		int counter = 1;
		for(SecurityGroup sg : securityGroups){
			queryString.put("SecurityGroup." + (counter < 10 ? "0" : "") + counter++, sg.getKey());
		}
		return super.execute();
	}
}
