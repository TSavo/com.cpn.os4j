package com.cpn.os4j.command;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

import com.cpn.os4j.EndPoint;
import com.cpn.os4j.model.UnmarshallerHelper;

public interface OpenStackCommand<T> extends Serializable {

	public abstract List<T> execute() throws ServerErrorException, IOException;

	public abstract String getAction();

	public abstract EndPoint getEndPoint();

	public abstract TreeMap<String, String> getQueryString();

	public abstract UnmarshallerHelper<T> getUnmarshallerHelper();

	public abstract String getVerb();

}