package com.cpn.os4j.command;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.AvailabilityZone;

public class DescribeAvailabilityZonesCommand extends AbstractOpenStackCommand<AvailabilityZone> {

	public DescribeAvailabilityZonesCommand(final EndPoint anEndPoint) {
		super(anEndPoint);
	}

	@Override
	public String getAction() {
		return "DescribeAvailabilityZones";
	}

	@Override
	public Class<AvailabilityZone> getUnmarshallingClass() {
		return AvailabilityZone.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "//availabilityZoneInfo/item";
	}
}
