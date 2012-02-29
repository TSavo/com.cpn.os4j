package com.cpn.os4j.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Instance;

public class RebootInstancesCommand extends AbstractOpenStackCommand<Object> {

	public List<Instance> instances = new ArrayList<>();

	public RebootInstancesCommand(final EndPoint anEndPoint, final Instance... i) {
		super(anEndPoint);
		for (final Instance in : i) {
			instances.add(in);
		}
	}

	public RebootInstancesCommand addInstance(final Instance... i) {
		for (final Instance in : i) {
			instances.add(in);
		}
		return this;
	}

	@Override
	public List<Object> execute() throws ServerErrorException, IOException {
		int counter = 1;
		for (final Instance i : instances) {
			queryString.put("InstanceId." + (counter < 10 ? "0" : "") + (counter++), i.getInstanceId());
		}
		return super.execute();
	}

	@Override
	public String getAction() {
		return "RebootInstances";
	}

	@Override
	public Class<Object> getUnmarshallingClass() {
		return null;
	}

	@Override
	public String getUnmarshallingXPath() {
		return null;
	}

}
