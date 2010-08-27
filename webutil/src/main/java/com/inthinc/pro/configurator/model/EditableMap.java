package com.inthinc.pro.configurator.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EditableMap<K,V> {

	public Map<K,V> originalValues;
	public Map<K,ValueWrapper<V>> editableValues;
	
	
    public EditableMap(List<K> keySource, Map<K,V> originalValues){
        	
		Map<K, V> destinationMap = new HashMap<K,V>();
		// Make sure there are values for all the keys
	 	for (K key:keySource){
			
			if (originalValues.get(key) == null){
				
				destinationMap.put(key,null);
			}
			else {
				destinationMap.put(key ,originalValues.get(key));
			}
		}
		this.originalValues = destinationMap;
		createEditableValues();
    }

	public EditableMap(Map<K, V> originalValues) {

		this.originalValues = originalValues;
		createEditableValues();
	}
    
    private void createEditableValues(){
    	
    	Map<K, ValueWrapper<V>>editableValues = new HashMap<K,ValueWrapper<V>>();
    	Iterator<Entry<K,V>> it = originalValues.entrySet().iterator();
    	
    	while(it.hasNext()){
    		
    		Entry<K, V> entry = it.next();
    		ValueWrapper<V> vw = new ValueWrapper<V>();
			vw.setValue(entry.getValue());
			editableValues.put(entry.getKey(), vw);
    	}
    	this.editableValues = editableValues;
    }
    
    public Map<K,V> getDifferencesMap(){
    	
       	Map<K,V> differencesMap = new HashMap<K, V>();
       	
    	Iterator<Entry<K,V>> it = originalValues.entrySet().iterator();
    	while(it.hasNext()){
    		
    		Entry<K, V> originalValueEntry = it.next();
    		
    		V editedValue = editableValues.get(originalValueEntry.getKey()).getValue();
    		
    		if ((originalValueEntry.getValue() == null) && (editedValue == null)){
    			
    		}
    		else if((originalValueEntry.getValue() == null) && (editedValue != null)){
    			
    			differencesMap.put(originalValueEntry.getKey(), editedValue);
    		}
    		else if (!originalValueEntry.getValue().equals(editedValue)){
    			
    			differencesMap.put(originalValueEntry.getKey(), editedValue);
    		}
    	}
       	return differencesMap;
    }

    public Map<K, V> getLatestValues(){
    	
    	Map<K, V>latestValues = new HashMap<K,V>();
    	Iterator<Entry<K,ValueWrapper<V>>> it = editableValues.entrySet().iterator();
    	
    	while(it.hasNext()){
    		
    		Entry<K, ValueWrapper<V>> entry = it.next();
    		latestValues.put(entry.getKey(), entry.getValue().getValue());
    	}
    	return latestValues;
    }
	public Map<K, V> getOriginalValues() {
		return originalValues;
	}

	public Map<K,ValueWrapper<V>> getEditableValues() {
    	
		return editableValues;
	}
	public void reset(){
		
		createEditableValues();
	}
}
