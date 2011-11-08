package com.cpn.os4j.command;

import java.util.ArrayList;
import java.util.List;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.Instance;

public class RebootInstancesCommand extends AbstractOpenStackCommand<Object> {

	public List<Instance> instances = new ArrayList<>();

	public RebootInstancesCommand(OpenStack anEndPoint, Instance... i) {
		super(anEndPoint);
		for (Instance in : i) {
			instances.add(in);
		}
	}
	
	@Override
	public List<Object> execute() {
		int counter = 1;
		for(Instance i : instances){
			queryString.put("InstanceId." + (counter < 10 ? "0" : "") + counter, i.getInstanceId());
		}
		return super.execute();
	}

	public RebootInstancesCommand addInstance(Instance... i) {
		for (Instance in : i) {
			instances.add(in);
		}
		return this;
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
