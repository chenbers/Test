package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SettingOptions{
    
    private List<Integer> vehicleIDs;
    private Map<Integer, String> values;
    private List<String> colors;
    private List<Integer> keys;
    private List<Entry<Integer,String>> entries;
    
    
    public SettingOptions() {
        super();
        values = new HashMap<Integer,String>();
        vehicleIDs = new ArrayList<Integer>();
    }
    public SettingOptions(SettingOptions settingOptions){
        
        vehicleIDs = new ArrayList<Integer>(settingOptions.vehicleIDs);
        values = new HashMap<Integer, String>(settingOptions.values);
        colors = new ArrayList<String>();
    }
    public void addValue(Integer settingID, String value){
        
        values.put(settingID,value);
    }
    public List<Integer> getVehicleIDs() {
        return vehicleIDs;
    }
    public void setVehicleIDs(List<Integer> vehicleIDs) {
        this.vehicleIDs = vehicleIDs;
    }
    public void addVehicleID(Integer vehicleID){
        vehicleIDs.add(vehicleID);
    }

    public Integer getCount(){
        return vehicleIDs.size();
    }
    public List<String> getValuesList(){

        return new ArrayList<String>(values.values());
    }
    public List<Integer> getKeys(){
        
        if(keys == null){
            
            keys = new ArrayList<Integer>(values.keySet());
            Collections.sort(keys);
        }
        return keys;
    }
    public List<Entry<Integer,String>> getEntries(){
        
       if(entries == null){
            
            entries = new ArrayList<Entry<Integer,String>>(values.entrySet());
        }
        return entries;
    }
    public List<String> getColors(){
        
        for(int i = 0;i<values.size();i++){
            colors.add(new Double(Math.random()*2).intValue()==0?"red":"white");
        }
        return colors;
    }
    public Map<Integer, String> getValues() {
        return values;
    }
    public void setValues(Map<Integer, String> values) {
        this.values = values;
    }
}
