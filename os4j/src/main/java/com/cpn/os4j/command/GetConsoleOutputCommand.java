package com.cpn.os4j.command;

import java.io.IOException;
import java.util.List;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.ConsoleOutput;
import com.cpn.os4j.model.Instance;

public class GetConsoleOutputCommand extends AbstractOpenStackCommand<ConsoleOutput> {
	public final Instance instance;

	public GetConsoleOutputCommand(final EndPoint anEndPoint, final Instance anInstance) {
		super(anEndPoint);
		instance = anInstance;
	}

	@Override
	public List<ConsoleOutput> execute() throws ServerErrorExeception, IOException {
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
