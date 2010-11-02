package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.configurator.ui.ConfigurationSelectionBean;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.util.MessageDigestUtil;

public class ConfigurationExtractor {

    private static final Logger logger = Logger.getLogger(ConfigurationExtractor.class);

    private ConfigurationSelectionBean configurationSelectionBean;

    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    
    private ConfigurationSet configurationSet;
    
    private Configuration selectedConfiguration;
    private Integer selectedConfigurationID;
    
    public ConfigurationSet getConfigurations() {
        
        return configurationSet;
    }
 
    public void refreshConfigurations(){
        
        configurationSet = new ConfigurationSet(createConfigurations());
    }


    private List<Configuration> createConfigurations() {

        Map<String,Configuration> configurations = new HashMap<String,Configuration>();

        VehicleSettingIterator vehicleSettingIterator = new VehicleSettingIterator(configurationSelectionBean);
        List<Integer> productSettings = deviceSettingDefinitionsByProductType.getKeys(configurationSelectionBean.getProductType());
        while (vehicleSettingIterator.hasNext()) {

            VehicleSetting vehicleSetting = vehicleSettingIterator.next();

            addToMatchingConfiguration(vehicleSetting, productSettings, configurations);
        }
        List<Configuration> configurationList = new ArrayList<Configuration>(configurations.values());
        Collections.sort(configurationList);
        return configurationList;
    }

    private void addToMatchingConfiguration(VehicleSetting vehicleSetting, List<Integer> productSettings,  Map<String,Configuration> configurations) {

        EditableMap<Integer, String> editedDesiredValues = new EditableMap<Integer, String>(productSettings, vehicleSetting.getCombinedSettings());

        String editedDesiredValuesMD5 = MessageDigestUtil.getMD5(concatenate(editedDesiredValues.getOriginalValues()));

        Configuration configuration = configurations.get(editedDesiredValuesMD5);
        if (configuration != null) {

            logger.debug("ConfigurationExtractor: addToMatchingConfiguration - matching found");
            configuration.addVehicleID(vehicleSetting.getVehicleID());
            return;
        }
        configurations.put(editedDesiredValuesMD5,new Configuration(vehicleSetting.getVehicleID(), configurations.size() + 1, editedDesiredValues, editedDesiredValuesMD5));
    }

    private String concatenate(Map<Integer, String> stringMap) {

        StringBuilder concatenatedString = new StringBuilder();
        List<Integer> keyList = new ArrayList<Integer>(stringMap.keySet());
        Collections.sort(keyList);

        for (Integer key : keyList) {

            String next = stringMap.get(key);
            if (next != null) {
                concatenatedString.append(key);
                concatenatedString.append(next);
            }
        }
        return concatenatedString.toString();
    }
    
    //setters
    
    public void setConfigurationSelectionBean(ConfigurationSelectionBean configurationSelectionBean) {
        this.configurationSelectionBean = configurationSelectionBean;
    }

    public void setDeviceSettingDefinitionsByProductType(DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType) {
        this.deviceSettingDefinitionsByProductType = deviceSettingDefinitionsByProductType;
    }

    public Configuration getSelectedConfiguration() {
        return selectedConfiguration;
    }

//    public void setSelectedConfiguration(Configuration selectedConfiguration) {
//        this.selectedConfiguration = selectedConfiguration;
//    }

    public Integer getSelectedConfigurationID() {
        return selectedConfigurationID;
    }

    public void setSelectedConfigurationID(Integer selectedConfigurationID) {
        this.selectedConfigurationID = selectedConfigurationID;
//        this.selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
    }
}
