package com.inthinc.pro.repository.jpa;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.DeviceSettingDefinitionJPA;
import com.inthinc.pro.repository.DeviceSettingDefinitionRepository;

@Repository
public class DeviceSettingDefinitionRepositoryImpl extends
		GenericRepositoryImpl< DeviceSettingDefinitionJPA, Integer> implements  DeviceSettingDefinitionRepository {

}
