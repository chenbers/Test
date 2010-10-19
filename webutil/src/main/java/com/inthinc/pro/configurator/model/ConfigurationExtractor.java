package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
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

    public ConfigurationSet getConfigurations() {

        return new ConfigurationSet(createConfigurations());
    }

    private List<Configuration> createConfigurations() {

        List<Configuration> configurations = new ArrayList<Configuration>();

        VehicleSettingIterator vehicleSettingIterator = new VehicleSettingIterator(configurationSelectionBean);
        List<Integer> productSettings = deviceSettingDefinitionsByProductType.getKeys(configurationSelectionBean.getProductType());
        while (vehicleSettingIterator.hasNext()) {

            VehicleSetting vehicleSetting = vehicleSettingIterator.next();

            addToMatchingConfiguration(vehicleSetting, productSettings, configurations);
        }
        return configurations;
    }

    private void addToMatchingConfiguration(VehicleSetting vehicleSetting, List<Integer> productSettings, List<Configuration> configurations) {

        EditableMap<Integer, String> editedDesiredValues = new EditableMap<Integer, String>(productSettings, vehicleSetting.getCombinedSettings());
        String editedDesiredValuesMD5 = MessageDigestUtil.getMD5(concatenate(editedDesiredValues.getOriginalValues()));

        for (Configuration configuration : configurations) {

            if (editedDesiredValuesMD5.equals(configuration.getMessageDigest5())) {

                logger.debug("ConfigurationExtractor: addToMatchingConfiguration - matching found");
                configuration.addVehicleID(vehicleSetting.getVehicleID());
                return;
            }
        }
        configurations.add(new Configuration(vehicleSetting.getVehicleID(), configurations.size() + 1, editedDesiredValues, editedDesiredValuesMD5));
    }

    private String concatenate(Map<Integer, String> stringMap) {

        StringBuffer concatenatedString = new StringBuffer();
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
}
