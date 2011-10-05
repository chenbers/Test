package com.inthinc.pro.dao.hibernate;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.configurator.model.SensitivitySliderValues;
import com.inthinc.pro.dao.jpa.ConfiguratorJPADAO;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class SensitivitySliderSettingDAOTest {

    @Autowired
    private ConfiguratorJPADAO configuratorJPADAO;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void getSensitivitySliderSettingsTest(){
    	List<SensitivitySliderValues> sensitivitySliderValues = configuratorJPADAO.getSensitivitySliderValues();
    	for(SensitivitySliderValues sensitivitySliderValue : sensitivitySliderValues)
    	System.out.println(sensitivitySliderValue.toString());
    }

}
