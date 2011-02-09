package com.inthinc.pro.dao.hessian;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.SensitivitySliderValues;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.ProductType;


public class ConfiguratorHessianDAOTest {

    ConfiguratorHessianDAO configuratorHessianDAO;
    
    @Before
    public void setUp() throws Exception {
        configuratorHessianDAO = new ConfiguratorHessianDAO();
        configuratorHessianDAO.setSiloService(new SiloServiceCreator("dev-pro.inthinc.com", 8099).getService());
    }

    @After
    public void tearDown() throws Exception {}
    
    @Ignore
    @Test
    public void deviceSettingDefinitions(){
        
       List<DeviceSettingDefinition> deviceSettingDefinitions = configuratorHessianDAO.getDeviceSettingDefinitions();
       assertTrue("No deviceSettingDefinitions were found", deviceSettingDefinitions.size() > 0);
    }
    
    @Ignore
    @Test
    public void createSliderCorrectionQueries(){
        
        List<SensitivitySliderValues> sensitivitySliderValues = configuratorHessianDAO.getSensitivitySliderValues();
        for (SensitivitySliderValues ssv :sensitivitySliderValues){
            
            if(ssv.getProductType().equals(ProductType.TIWIPRO_R74) && ssv.getSettingID() != 1225){
                for (String sliderValue : ssv.getValues()){
                    
                    String[] tokens = sliderValue.split(" ");
                    tokens[2]="99";
                    StringBuilder sb = new StringBuilder();
                    for(String token:tokens){
                        sb.append(token);
                        sb.append(" ");
                    }
                    sb.setLength(sb.length()-1);
                    System.out.println("update desiredVSet set value=\""+sliderValue+"\", modified=utc_timestamp(), reason=\"correction\" where value=\""+sb.toString()+"\" and settingID="+ssv.getSettingID()+";");
                }
            }
        }
    }
}
