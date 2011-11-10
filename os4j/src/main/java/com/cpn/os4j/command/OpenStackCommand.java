package com.cpn.os4j.command;

import java.util.List;
import java.util.TreeMap;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.UnmarshallerHelper;

public interface OpenStackCommand<T> {

	
	public abstract String getVerb();
	public abstract TreeMap<String, String> getQueryString();
	public abstract OpenStack getEndPoint();
	public abstract String getAction();
	public abstract List<T> execute() throws ServerErrorExecption;
	public abstract UnmarshallerHelper<T> getUnmarshallerHelper();
	public abstract void setServerErrorException(ServerErrorExecption anExecption);

}