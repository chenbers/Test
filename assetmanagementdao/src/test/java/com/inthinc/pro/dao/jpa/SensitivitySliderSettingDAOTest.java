package com.inthinc.pro.dao.jpa;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.domain.settingDefinitions.SensitivitySliderValues;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class SensitivitySliderSettingDAOTest {

    @Autowired
    private DeviceSettingDefinitionJPADAO deviceSettingDefinitionJPADAO;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void getSensitivitySliderSettingsTest(){
    	List<SensitivitySliderValues> sensitivitySliderValues = deviceSettingDefinitionJPADAO.getSensitivitySliderValues();
    	for(SensitivitySliderValues sensitivitySliderValue : sensitivitySliderValues)
    	System.out.println(sensitivitySliderValue.toString());
    }

}
