package com.cpn.os4j.command;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import com.cpn.os4j.OpenStack;
import com.cpn.os4j.model.UnmarshallerHelper;

public interface OpenStackCommand<T> {

	public abstract List<T> execute() throws ServerErrorExeception, IOException;

	public abstract String getAction();

	public abstract OpenStack getEndPoint();

	public abstract TreeMap<String, String> getQueryString();

	public abstract UnmarshallerHelper<T> getUnmarshallerHelper();

	public abstract String getVerb();

}