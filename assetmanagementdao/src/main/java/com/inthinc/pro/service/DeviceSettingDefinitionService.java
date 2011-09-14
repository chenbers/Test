package com.inthinc.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inthinc.pro.domain.DeviceSettingDefinitionJPA;
import com.inthinc.pro.repository.DeviceSettingDefinitionRepository;

@Service
public class DeviceSettingDefinitionService {

	@Autowired
	DeviceSettingDefinitionRepository deviceSettingDefinitionRepository;
	
	public List<DeviceSettingDefinitionJPA> getDeviceSettingDefinitions(){
		return deviceSettingDefinitionRepository.findAll();
	}

}
