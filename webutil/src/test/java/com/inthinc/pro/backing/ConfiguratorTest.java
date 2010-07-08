package com.inthinc.pro.backing;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.VehicleSetting;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.DeviceSettingDefinitions;

public class ConfiguratorTest {
    
    private DeviceSettingDefinitions deviceSettingDefinitions;
    private ConfiguratorBean configuratorBean;
    
    @Before
    public void setUp() throws Exception {
       
        configuratorBean = new ConfiguratorBean();
        deviceSettingDefinitions = new DeviceSettingDefinitions();
        List<DeviceSettingDefinition> dsdList = new ArrayList<DeviceSettingDefinition>();
        for (int i=0;i<4;i++){
            
           DeviceSettingDefinition dsd = new DeviceSettingDefinition();
           dsd.setSettingID(i);
           dsdList.add(dsd);
        }
        deviceSettingDefinitions.setDeviceSettings(dsdList);
        configuratorBean.setDeviceSettingDefinitions(deviceSettingDefinitions);
        
        List<VehicleSetting> vehicleSettings = new ArrayList<VehicleSetting>();
        for (int i=0;i<4;i++){
            
            VehicleSetting vs = new VehicleSetting();
            vs.setVehicleID(i);
            Map<Integer,String> settings = new HashMap<Integer,String>();
            
            for(int j= 0;j<4;j++){
                
                settings.put(j, "string_"+j);
            }
            vs.setSettings(settings);
            vehicleSettings.add(vs);
         }
         configuratorBean.setVehicleSettings(vehicleSettings);
         
    }

    @Test
    public void configuratorCreateConfigurationsFromVehicleSettingsTest(){
        
        configuratorBean.createConfigurationsFromVehicleSettings();
    }
}
