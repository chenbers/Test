package com.inthinc.pro.dao.jpa;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.configurator.model.settingDefinitions.DeviceSettingValidation;
import com.inthinc.pro.domain.settingDefinitions.SensitivitySliderValues;
@Component
public class DeviceSettingDefinitionJPADAO{

//    private static final Logger logger = Logger.getLogger(ConfiguratorJPADAO.class);

    @Autowired
    private SettingHelper settingHelper;
    @Autowired
    private SensitivitySliderHelper sensitivitySliderHelper;

	public List<DeviceSettingValidation> getDeviceSettingDefinitions() {
		List<DeviceSettingValidation> deviceSettingDefinitions = settingHelper.getDeviceSettingValidations();
		return deviceSettingDefinitions;
	}
	public List<SensitivitySliderValues> getSensitivitySliderValues() {
		List<SensitivitySliderValues> convertedSensitivitySliderValues = sensitivitySliderHelper.getSensitivitySliderValues();
		return convertedSensitivitySliderValues;
	}


//	public DeviceSettingDefinition findByID(Integer id) {
//		// not implemented
//		return null;
//	}
//
//	public Integer create(Integer id, DeviceSettingDefinition entity) {
//		// not implemented
//		return null;
//	}
//
//	public Integer update(DeviceSettingDefinition entity) {
//		// not implemented
//		return null;
//	}
//
//	public Integer deleteByID(Integer id) {
//		// not implemented
//		return null;
//	}


	public void updateVehicleSettings(Integer vehicleID,
			Map<Integer, String> setMap, Integer userID, String reason) {
		
	}


//	public void setSettingHelper(SettingHelper settingHelper) {
//		this.settingHelper = settingHelper;
//	}

}
