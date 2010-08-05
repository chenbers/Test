package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.BaseAppEntity;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;

public class DeviceSettingDefinitions implements BaseAppEntity {

    private static Map<Integer, DeviceSettingDefinitionBean> deviceSettingDefinitions;
    private static List<DeviceSettingDefinitionBean> deviceSettingDefinitionsList;
    
    private ConfiguratorDAO configuratorDAO;

    public void init(){

        List<DeviceSettingDefinition> dsdList = configuratorDAO.getDeviceSettingDefinitions();  
        deviceSettingDefinitions = new HashMap<Integer,DeviceSettingDefinitionBean>();
        for(DeviceSettingDefinition dsd : dsdList){
            
        	DeviceSettingDefinitionBean dsdBean = new DeviceSettingDefinitionBean(dsd);
            deviceSettingDefinitions.put(dsd.getSettingID(), dsdBean);
        }
        deviceSettingDefinitionsList = new ArrayList<DeviceSettingDefinitionBean>(deviceSettingDefinitions.values());
    }

    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }

    public static DeviceSettingDefinitionBean getDeviceSettingDefinition(Integer settingID){
    	
    	return deviceSettingDefinitions.get(settingID);
    }
    
    public static List<DeviceSettingDefinitionBean> getDeviceSettingDefinitions(){
        
        return deviceSettingDefinitionsList;
    }

}
