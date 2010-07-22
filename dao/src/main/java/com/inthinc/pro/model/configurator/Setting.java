package com.inthinc.pro.model.configurator;

public class Setting implements Comparable<Setting>{
    
    private Integer settingID;
    private String value;
    
    public Setting(Integer settingID, String value) {
        super();
        this.settingID = settingID;
        this.value = value;
    }
    public Integer getSettingID() {
        return settingID;
    }
    public void setSettingID(Integer settingID) {
        this.settingID = settingID;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public int compareTo(Setting o) {
         
        return this.value.compareTo(o.value);
    }
    @Override
    public String toString() {
        return "Setting [settingID=" + settingID + ", value=" + value + "]";
    }
 }

