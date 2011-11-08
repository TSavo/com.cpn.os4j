package com.cpn.os4j.command;

import java.util.List;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.ConsoleOutput;
import com.cpn.os4j.model.Instance;

public class GetConsoleOutputCommand extends AbstractOpenStackCommand<ConsoleOutput> {
	public Instance instance; 
	

	public GetConsoleOutputCommand(OpenStack anEndPoint, Instance anInstance) {
		super(anEndPoint);
		instance = anInstance;
	}
	
	@Override
	public List<ConsoleOutput> execute() {
		queryString.put("InstanceId", instance.getInstanceId());
		return super.execute();
	}

	
	@Override
	public String getAction() {
		return "GetConsoleOutput";
	}

	@Override
	public Class<ConsoleOutput> getUnmarshallingClass() {
		return ConsoleOutput.class;
	}

	@Override
	public String getUnmarshallingXPath() {
		return "/GetConsoleOutputResponse";
	}
	
	
}
