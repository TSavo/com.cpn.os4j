package com.cpn.os4j.model.cache;

import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhcacheWrapper<K, V extends Cacheable<K>> implements CacheWrapper<K, V> 
{
    private final String cacheName;
    private final CacheManager cacheManager;

    public EhcacheWrapper(final String cacheName, final CacheManager aManager)
    {
        this.cacheName = cacheName;
        this.cacheManager = aManager;
        cacheManager.addCache(cacheName);
    }

    @Override
    public CacheWrapper<K, V> put(final K key, final V value)
    {
        getCache().put(new Element(key, value));
        return this;
    }

    @SuppressWarnings("unchecked")
		public V get(final K key) 
    {
        Element element = getCache().get(key);
        if (element != null) {
            return (V) element.getValue();
        }
        return null;
    }

    public Ehcache getCache() 
    {
        return cacheManager.getEhcache(cacheName);
    }
    
    @Override
		public CacheWrapper<K, V> removeAll(){
    	cacheManager.getEhcache(cacheName).removeAll();
    	return this;
    }
    
    @Override
		public CacheWrapper<K, V> putAll(final List<V> aList){
    	for(V v : aList){
    		put(v.getKey(), v);
    	}
    	return this;
    }
}