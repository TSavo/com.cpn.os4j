package com.cpn.os4j.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhcacheWrapper<K, V> implements CacheWrapper<K, V> 
{
    private final String cacheName;
    private final CacheManager cacheManager;

    public EhcacheWrapper(final String cacheName)
    {
        this.cacheName = cacheName;
        this.cacheManager = CacheManager.getInstance();
        cacheManager.addCache(cacheName);
    }

    public void put(final K key, final V value)
    {
        getCache().put(new Element(key, value));
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
		public void removeAll(){
    	cacheManager.getEhcache(cacheName).removeAll();
    }
}