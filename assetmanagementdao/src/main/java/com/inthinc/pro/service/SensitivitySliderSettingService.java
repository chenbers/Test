package com.inthinc.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.settingDefinitions.SensitivitySliderValue;
import com.inthinc.pro.repository.SensitivitySliderSettingRepository;

@Service
public class SensitivitySliderSettingService {

	@Autowired
	SensitivitySliderSettingRepository sensitivitySliderSettingRepository;
	
	public List<SensitivitySliderValue> getSensitivitySliderSettings(){
		List<SensitivitySliderValue> sensitivitySliderValues = sensitivitySliderSettingRepository.findAll();
		return sensitivitySliderValues;
	}

}
