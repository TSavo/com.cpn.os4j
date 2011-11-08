package com.cpn.os4j.cache;

import java.io.Serializable;

public interface Cacheable<T> extends Serializable{
	
	public T getKey();

}
