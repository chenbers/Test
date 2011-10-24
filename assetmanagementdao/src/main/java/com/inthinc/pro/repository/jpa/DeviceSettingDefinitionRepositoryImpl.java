package com.inthinc.pro.repository.jpa;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.settingDefinitions.DeviceSettingDefinition;
import com.inthinc.pro.repository.DeviceSettingDefinitionRepository;

@Repository
public class DeviceSettingDefinitionRepositoryImpl extends
		CentDBRepositoryImpl< DeviceSettingDefinition, Integer> implements  DeviceSettingDefinitionRepository {

}
