package com.cpn.os4j.command;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Image;
import com.cpn.os4j.model.Instance;
import com.cpn.os4j.model.KeyPair;
import com.cpn.os4j.model.SecurityGroup;

public class RunInstancesCommand extends AbstractOpenStackCommand<Instance> {

	private final String addressingType;
	private String availabilityZone = null;

	private final String imageId;

	private final String instanceType;

	private String kernelId;

	private final String keyName;

	private final int maxCount;

	private final int minCount;

	private String ramdiskId;

	private String userData = null;
	
	private final List<SecurityGroup> securityGroups = new ArrayList<>();


	public RunInstancesCommand(final EndPoint anEndPoint, final Image anImage, final KeyPair keyPair, final String instanceType, final String addressingType, final int minCount, final int maxCount, final String anAvailabilityZone, final SecurityGroup... groups) {
		super(anEndPoint);
		imageId = anImage.getImageId();
		// this.kernelId = kernelId;
		this.instanceType = instanceType;
		this.addressingType = addressingType;
		this.minCount = minCount;
		this.maxCount = maxCount;
		// this.ramdiskId = ramdiskId;
		this.availabilityZone = anAvailabilityZone;
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
		queryString.put("MaxCount", new Integer(maxCount).toString());
		queryString.put("MinCount", new Integer(minCount).toString());
		if(userData != null){
			queryString.put("UserData", URLEncoder.encode(userData, "utf-8"));
		}
		if(availabilityZone != null){
			queryString.put("Placement.AvailabilityZone", availabilityZone);
		}
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

	public int getMaxCount() {
		return maxCount;
	}

	public int getMinCount() {
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
