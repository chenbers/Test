package com.inthinc.pro.automation.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
public class AttrMap implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1003738296542156141L;
    private List<Entry> entry;

    public AttrMap() {}

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }
    
    public static class Entry implements Serializable{
        /**
         * 
         */
        private static final long serialVersionUID = -7533004271730154272L;
        private String key;
        private Map<String, Object> mappedKey;
        private Object value;
        private Map<String, Object> mappedValue;
        
        public Map<String, Object> getMappedKey() {
            if (mappedKey==null){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", key);
                return map;
            }
            return mappedKey;
        }
        
        public String getKey(){
            return key;
        }
        
        public void setKey(String key){
            this.key = key;
        }
        @JsonProperty("key")
        public void setMappedKey(Map<String, Object>  key) {
            this.key = (String) key.get("content");
            mappedKey = key;
        }
        
        @JsonProperty("value")
        public Map<String, Object> getMappedValue() {
            if (mappedValue==null){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", value);
                return map;
            }
            return mappedValue;
        }
        
        public Object getValue(){
            return value;
        }
        public void setValue(Map<String, Object> value) {
            this.value = value.get("content");
            mappedValue = value;
        }
        
        @Override 
        public String toString(){
            return key + "=" + value;
        }
    }
}