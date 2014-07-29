package com.inthinc.pro.model.configurator;

/**
 * Created by Infrasoft06 on 6/27/2014.
 */
public class SettingValue {

    private Integer settingID;
    private String settingValue;

    public SettingValue(Integer settingID, String settingValue) {
        this.settingID = settingID;
        this.settingValue = settingValue;
    }

    public SettingValue(){

    }

    public Integer getSettingID() {
        return settingID;
    }

    public void setSettingID(Integer settingID) {
        this.settingID = settingID;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }
}
