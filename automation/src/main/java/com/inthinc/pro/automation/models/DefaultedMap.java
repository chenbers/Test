package com.inthinc.pro.automation.models;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.automation.logging.Log;

public class DefaultedMap<K, V> implements Map<K,V>{

	private Map<K,V> wrappedMap;
	private V defaultValue;
	
	public void clear() {
		wrappedMap.clear();
	}

	public boolean containsKey(Object key) {
		return wrappedMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return wrappedMap.containsValue(value);
	}

	public Set<Entry<K, V>> entrySet() {
		return wrappedMap.entrySet();
	}

	public boolean equals(Object o) {
		return wrappedMap.equals(o);
	}

	public V get(Object key) {
		
		V value = wrappedMap.get(key);
		
		if (value == null){
			
			Log.trace("Unsupported key = [" + key + "] requested, returning default value");
			
			return defaultValue;
		}
		
		return value;
	}

	public int hashCode() {
		return wrappedMap.hashCode();
	}

	public boolean isEmpty() {
		return wrappedMap.isEmpty();
	}

	public Set<K> keySet() {
		return wrappedMap.keySet();
	}

	public V put(K key, V value) {
		return wrappedMap.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		wrappedMap.putAll(m);
	}

	public V remove(Object key) {
		return wrappedMap.remove(key);
	}

	public int size() {
		return wrappedMap.size();
	}

	public Collection<V> values() {
		return wrappedMap.values();
	}

	public DefaultedMap(Map<K,V> wrappedMap, V defaultValue) {
		
		this.wrappedMap = wrappedMap;
		this.defaultValue = defaultValue;
	}


}
