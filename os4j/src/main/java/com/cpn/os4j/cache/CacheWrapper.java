package com.cpn.os4j.cache;

public interface CacheWrapper<K, V>
{
  void put(K key, V value);

  V get(K key);

	public abstract void removeAll();
}