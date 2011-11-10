package com.cpn.os4j.model;

public interface UnmarshallerHelper<T> {
	public Class<T> getUnmarshallingClass();
	public String getUnmarshallingXPath();
}
