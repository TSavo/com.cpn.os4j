package com.cpn.os4j.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.Instance;

public class TerminateInstancesCommand extends AbstractOpenStackCommand<Object> {

	private final List<Instance> instances = new ArrayList<>();

	public TerminateInstancesCommand(final EndPoint anEndPoint, final Instance... instances) {
		super(anEndPoint);
		for (final Instance i : instances) {
			this.instances.add(i);
		}
	}

	public TerminateInstancesCommand addInstance(final Instance... someInstances) {
		for (final Instance i : someInstances) {
			instances.add(i);
		}
		return this;
	}

	@Override
	public List<Object> execute() throws ServerErrorException, IOException {
		int counter = 1;
		for (final Instance i : instances) {
			queryString.put("InstanceId." + (counter < 10 ? "0" : "") + counter++, i.getInstanceId());
		}
		return super.execute();
	}

	@Override
	public String getAction() {
		return "TerminateInstances";
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
