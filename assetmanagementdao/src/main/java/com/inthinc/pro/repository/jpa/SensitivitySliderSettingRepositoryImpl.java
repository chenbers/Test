package com.inthinc.pro.repository.jpa;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.settingDefinitions.SensitivitySliderValue;
import com.inthinc.pro.repository.SensitivitySliderSettingRepository;

@Repository
public class SensitivitySliderSettingRepositoryImpl  extends
CentDBRepositoryImpl< SensitivitySliderValue, Integer> implements  SensitivitySliderSettingRepository {

}
