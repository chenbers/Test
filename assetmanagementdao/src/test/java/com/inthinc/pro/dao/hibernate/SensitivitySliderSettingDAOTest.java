package com.inthinc.pro.dao.hibernate;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inthinc.pro.dao.jpa.ConfiguratorJPADAO;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.service.SensitivitySliderSettingService;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class SensitivitySliderSettingDAOTest {

    @Autowired
	private SensitivitySliderSettingService sensitivitySliderSettingService;
    
    private ConfiguratorJPADAO configuratorJPADAO;
    
    
    @Before
    public void setUp() throws Exception {
    	configuratorJPADAO = new ConfiguratorJPADAO();
    	configuratorJPADAO.setSensitivitySliderSettingService(sensitivitySliderSettingService);
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
