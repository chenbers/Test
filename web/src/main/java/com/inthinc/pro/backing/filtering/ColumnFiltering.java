package com.inthinc.pro.backing.filtering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.util.BeanUtil;

public class ColumnFiltering<T> implements Map<String,Object> {

    private Map<String,Object> map;

    public List<T> getInViewItems(List<T> filteredItems){
        List<T> viewedItems = new ArrayList<T>(filteredItems);
        Iterator<T> it = viewedItems.iterator();
        while(it.hasNext()){
            T itemView = it.next();
            for (String filterField :keySet()){
                Object filterValue = get(filterField);
                if (filterValue != null){
                    //check if the value for that column matches the selection
                    Object field = BeanUtil.getFieldValue(itemView,filterField);
                    if (field != null && !field.equals(filterValue)){
                       it.remove();
                       break;
                    }
                }
            }
        }
        return viewedItems;
    }

    public void clear() {
        map.clear();
    }

    public Set<java.util.Map.Entry<String,Object>> entrySet() {
        return map.entrySet();
    }

    public int hashCode() {
        return map.hashCode();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        map.putAll(m);
    }

    public int size() {
        return map.size();
    }

    @Override
    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public ColumnFiltering() {
        super();
        map = new HashMap<String,Object>();
    }

    @Override
    public boolean containsKey(java.lang.Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(java.lang.Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(java.lang.Object key) {
        return map.get(key);
    }

    @Override
    public Object remove(java.lang.Object key) {
        return map.remove(key);
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

}
