package com.inthinc.pro.backing.model;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.configurator.Setting;

public class ConfigurationSet {

    List<SettingOptions> configurations;
    Map<Integer, List<Setting>> differentSettings;
    
    public List<SettingOptions> getConfigurations() {
        return configurations;
    }
    public void setConfigurations(List<SettingOptions> configurations) {
        this.configurations = configurations;
    }
    public Map<Integer, List<Setting>> getDifferentSettings() {
        return differentSettings;
    }
    public void setDifferentSettings(Map<Integer, List<Setting>> differentSettings) {
        this.differentSettings = differentSettings;
    }
}
