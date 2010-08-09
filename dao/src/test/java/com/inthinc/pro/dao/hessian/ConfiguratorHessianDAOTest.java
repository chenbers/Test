package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.mock.proserver.SiloServiceCreator;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
@Ignore
public class ConfiguratorHessianDAOTest {

    ConfiguratorHessianDAO configuratorHessianDAO;
    
    @Before
    public void setUp() throws Exception {
        configuratorHessianDAO = new ConfiguratorHessianDAO();
        configuratorHessianDAO.setSiloService(new SiloServiceCreator().getService());
    }

    @After
    public void tearDown() throws Exception {}
    
    @Test
    public void deviceSettingDefinitions(){
        
       List<DeviceSettingDefinition> deviceSettingDefinitions = configuratorHessianDAO.getDeviceSettingDefinitions();
       assertTrue("No deviceSettingDefinitions were found", deviceSettingDefinitions.size() > 0);
    }

}
