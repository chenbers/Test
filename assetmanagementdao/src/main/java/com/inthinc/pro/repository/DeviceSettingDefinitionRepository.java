package com.inthinc.pro.repository;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.DeviceSettingDefinitionJPA;

@Repository
public interface DeviceSettingDefinitionRepository extends
		GenericRepository< DeviceSettingDefinitionJPA, Integer> {
}
