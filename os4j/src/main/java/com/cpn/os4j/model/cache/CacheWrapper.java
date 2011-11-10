package com.cpn.os4j.model.cache;

import java.util.List;

public interface CacheWrapper<K, V extends Cacheable<K>> {
	V get(K key);

	CacheWrapper<K, V> put(K key, V value);

	public abstract CacheWrapper<K, V> putAll(List<V> aList);

	public abstract CacheWrapper<K, V> removeAll();
}