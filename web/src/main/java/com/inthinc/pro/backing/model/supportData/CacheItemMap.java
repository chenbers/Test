package com.inthinc.pro.backing.model.supportData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.dao.GenericDAO;

public abstract class CacheItemMap<D ,T > implements Map<Integer,T>{
    
    protected Map<Integer, T> itemMap;
    
    public abstract List<T> getItems();
    protected abstract T fetchItem(Integer key);
    public abstract void refreshMap();
    protected abstract Integer getId(T item);
    protected abstract List<T> fetchItems();
    protected abstract GenericDAO<D, Integer> getDAO();
    public abstract void setDAO(GenericDAO<D, Integer> dao);
        
    public void clearMap(){
        itemMap = null;
    }
    
    public void buildMap(){
        List<T> items = fetchItems();
        itemMap = new HashMap<Integer, T>();
        for(T item :items){
            itemMap.put(getId(item), item);
        }
    }
    public T getItem(Integer key){
        
        T item = itemMap.get(key);
        if (item == null){
            item = locateItem(key);
        }
        return item;
    }
    protected T locateItem(Integer key){
        
        T item = fetchItem(key);
        if(item != null){
            itemMap.put(key, item);
        }
        return item;
    }
    public void clear() {
        itemMap.clear();
    }
    public boolean containsKey(Object key) {
        return itemMap.containsKey(key);
    }
    public boolean containsValue(Object value) {
        return itemMap.containsValue(value);
    }
    public Set<java.util.Map.Entry<Integer, T>> entrySet() {
        return itemMap.entrySet();
    }
    public boolean equals(Object o) {
        return itemMap.equals(o);
    }
    public T get(Object key) {
        return getItem((Integer)key);
    }
    public int hashCode() {
        return itemMap.hashCode();
    }
    public boolean isEmpty() {
        return itemMap.isEmpty();
    }
    public Set<Integer> keySet() {
        return itemMap.keySet();
    }
    public T put(Integer key, T value) {
        return null;
    }
    public void putAll(Map<? extends Integer, ? extends T> m) {
        //Not implemented
    }
    public T remove(Object key) {
        //Not implemented
        return null;
    }
    public int size() {
        return itemMap.size();
    }
    public Collection<T> values() {
        return itemMap.values();
    }
}
