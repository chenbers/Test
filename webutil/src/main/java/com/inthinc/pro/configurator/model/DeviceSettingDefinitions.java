package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.BaseAppEntity;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;

public class DeviceSettingDefinitions implements BaseAppEntity {

    private static Map<String, DeviceSettingDefinitionBean> deviceSettingDefinitionsMap;
    private static List<DeviceSettingDefinitionBean> deviceSettingDefinitionsList;
    
	private ConfiguratorDAO configuratorDAO;

    public void init(){

        List<DeviceSettingDefinition> dsdList = configuratorDAO.getDeviceSettingDefinitions();  
        deviceSettingDefinitionsMap = new HashMap<String,DeviceSettingDefinitionBean>();
        
        for(DeviceSettingDefinition dsd : dsdList){
            
        	DeviceSettingDefinitionBean dsdBean = new DeviceSettingDefinitionBean(dsd);
            deviceSettingDefinitionsMap.put(dsd.getSettingID().toString(), dsdBean);
        }
        deviceSettingDefinitionsList = new ArrayList<DeviceSettingDefinitionBean>(deviceSettingDefinitionsMap.values());
    }

    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }

    public static DeviceSettingDefinitionBean getDeviceSettingDefinition(Integer settingID){
    	return deviceSettingDefinitionsMap.get(settingID.toString());
    }
    
    public static List<DeviceSettingDefinitionBean> getDeviceSettingDefinitions(){
        
        return deviceSettingDefinitionsList;
    }
    public static Map<String, DeviceSettingDefinitionBean> getDeviceSettingDefinitionsMap(){
        
        return deviceSettingDefinitionsMap;
    }
    
}
