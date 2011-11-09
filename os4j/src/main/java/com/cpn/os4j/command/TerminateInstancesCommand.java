package com.cpn.os4j.command;

import java.util.ArrayList;
import java.util.List;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Instance;

public class TerminateInstancesCommand extends AbstractOpenStackCommand<Object> {

	private List<Instance> instances = new ArrayList<>();

	public TerminateInstancesCommand(OpenStack anEndPoint, Instance... instances) {
		super(anEndPoint);
		for (Instance i : instances) {
			this.instances.add(i);
		}
	}
	
	public TerminateInstancesCommand addInstance(Instance... someInstances){
		for(Instance i: someInstances){
			this.instances.add(i);
		}
		return this;
	}

	@Override
	public List<Object> execute() {
		int counter = 1;
		for(Instance i : instances){
			queryString.put("InstanceId." + (counter < 10 ? "0" : "") + counter++, i.getInstanceId());
		}
		return super.execute();
	}
	@Override
	public Class<Object> getUnmarshallingClass() {
		return null;
	}

	@Override
	public String getUnmarshallingXPath() {
		return null;
	}

	@Override
	public String getAction() {
		return "TerminateInstances";
	}
}
