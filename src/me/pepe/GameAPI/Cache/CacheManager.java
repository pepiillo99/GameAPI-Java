package me.pepe.GameAPI.Cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CacheManager implements Runnable {
	private Map<String, Cache<?>> caches = new HashMap<String, Cache<?>>();
	public void addCache(Cache<?> cache) {
		if (hasCache(cache)) {
			System.out.println("El cache '" + cache.getID() + "' ya estaba guardado");
		} else {
			caches.put(cache.getID(), cache);
		}
	}
	public boolean hasCache(Cache<?> cache) {
		return hasCache(cache.getID());
	}
	public boolean hasCache(String id) {
		return caches.containsKey(id);
	}
	public boolean hasCacheBySubID(String subID) {
		Iterator<Cache<?>> cachesIterator = caches.values().iterator();
		while (cachesIterator.hasNext()) {
			Cache<?> cache = cachesIterator.next();
			if (cache.hasSubID(subID)) {
				return true;
			}
		}
		return false;
	}
	public Cache<?> getCache(String id) {
		if (hasCache(id)) {
			return caches.get(id);
		}
		return null;
	}
	public Cache<?> getCacheBySubID(String subID) {
		Iterator<Cache<?>> cachesIterator = caches.values().iterator();
		while (cachesIterator.hasNext()) {
			Cache<?> cache = cachesIterator.next();
			if (cache.hasSubID(subID)) {
				return cache;
			}
		}
		return null;
	}
	public Set<Entry<String, Cache<?>>> getCaches() {
		return caches.entrySet();
	}
	public void removeCache(String id) {
		caches.remove(id);
	}
	public void removeCache(Cache<?> cache) { // No hace el removeCallback, se debe hacer manualmente
		removeCache(cache.getID());
	}
	public void run() {
		Iterator<Cache<?>> cachesIterator = caches.values().iterator();
		while (cachesIterator.hasNext()) {
			Cache<?> cache = cachesIterator.next();
			if (cache.isCaduqued()) {
				if (cache.getRemoveCallback() != null) {
					cache.getRemoveCallback().done(true, null);;
				}
				cachesIterator.remove();
			}
		}
	}
}
