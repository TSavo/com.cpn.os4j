package com.cpn.os4j.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.Instance;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.SecurityGroup;

public class RunInstancesCommand extends AbstractOpenStackCommand<Instance> {

	private final String addressingType;
	private String availabilityZone;

	private final String imageId;

	private final String instanceType;

	private String kernelId;

	private final String keyName;

	private final String maxCount;

	private final String minCount;

	private String ramdiskId;

	private String userData = null;
	
	private final List<SecurityGroup> securityGroups = new ArrayList<>();

	public RunInstancesCommand(final OpenStack anEndPoint, final Image anImage, final KeyPair keyPair, final String instanceType, final String addressingType, final String minCount, final String maxCount, final SecurityGroup... groups) {
		super(anEndPoint);
		imageId = anImage.getImageId();
		// this.kernelId = kernelId;
		this.instanceType = instanceType;
		this.addressingType = addressingType;
		this.minCount = minCount;
		this.maxCount = maxCount;
		// this.ramdiskId = ramdiskId;
		// this.availabilityZone = availabilityZone;
		keyName = keyPair.getName();
		for (final SecurityGroup sg : groups) {
			securityGroups.add(sg);
		}
	}

	public RunInstancesCommand addSecurityGroup(final SecurityGroup... groups) {
		for (final SecurityGroup sg : groups) {
			securityGroups.add(sg);
		}
		return this;
	}

	public RunInstancesCommand setUserData(final String aUserData){
		userData = aUserData;
		return this;
	}
	
	@Override
	public List<Instance> execute() throws ServerErrorExeception, IOException {
		queryString.put("AddressingType", addressingType);
		queryString.put("ImageId", imageId);
		queryString.put("InstanceType", instanceType);
		// queryString.put("KernelId", kernelId);
		queryString.put("KeyName", keyName);
		queryString.put("MaxCount", maxCount);
		queryString.put("MinCount", minCount);
		if(userData != null){
			queryString.put("UserData", userData);
		}
		// queryString.put("Placement.AvailabilityZone", availabilityZone);
		// queryString.put("RamdiskId", ramdiskId);
		int counter = 1;
		for (final SecurityGroup sg : securityGroups) {
			queryString.put("SecurityGroup." + (counter < 10 ? "0" : "") + counter++, sg.getKey());
		}
		return super.execute();
	}

	@Override
	public String getAction() {
		return "RunInstances";
	}

	public String getAddressingType() {
		return addressingType;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
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

	public String getRamdiskId() {
		return ramdiskId;
	}

	public List<SecurityGroup> getSecurityGroups() {
		return securityGroups;
	}

	@Override
	public Class<Instance> getUnmarshallingClass() {
		return Instance.class;
	};

	@Override
	public String getUnmarshallingXPath() {
		return "//instancesSet/item";
	}
}
