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
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.service.DeviceSettingDefinitionService;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceSettingDefinitionDAOTest {

    @Autowired
	private DeviceSettingDefinitionService deviceSettingDefinitionService;
    
    private ConfiguratorJPADAO configuratorJPADAO;
    
    
    @Before
    public void setUp() throws Exception {
    	configuratorJPADAO = new ConfiguratorJPADAO();
    	configuratorJPADAO.setDeviceSettingDefinitionService(deviceSettingDefinitionService);
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void getDeviceSettingDefinitionsTest(){
    	List<DeviceSettingDefinition> deviceSettingDefinitions = configuratorJPADAO.getDeviceSettingDefinitions();
    	for(DeviceSettingDefinition deviceSettingDefinition : deviceSettingDefinitions)
    	System.out.println(deviceSettingDefinition.toString());
    }
}
