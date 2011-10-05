package com.inthinc.pro.repository.jpa;

import org.springframework.stereotype.Repository;

import com.inthinc.pro.domain.settings.SensitivitySliderValue;
import com.inthinc.pro.repository.SensitivitySliderSettingRepository;

@Repository
public class SensitivitySliderSettingRepositoryImpl  extends
GenericRepositoryImpl< SensitivitySliderValue, Integer> implements  SensitivitySliderSettingRepository {

}
