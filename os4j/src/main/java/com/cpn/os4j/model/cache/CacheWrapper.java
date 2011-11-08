package com.cpn.os4j.model.cache;

import java.util.List;

public interface CacheWrapper<K, V extends Cacheable<K>>
{
  CacheWrapper<K, V> put(K key, V value);

  V get(K key);

	public abstract CacheWrapper<K, V> removeAll();

	public abstract CacheWrapper<K, V> putAll(List<V> aList);
}