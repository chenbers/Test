package com.inthinc.pro.backing;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.backing.configurator.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.backing.configurator.VehicleSettingsByProductType;
import com.inthinc.pro.backing.configurator.model.ConfigurationExtractor;
import com.inthinc.pro.backing.configurator.model.ConfigurationSet;
import com.inthinc.pro.dao.hessian.ConfiguratorHessianDAO;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.DeviceSettingDefinitions;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.VehicleSettings;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.VarType;

public class ConfigurationExtractorTest {

    private static DeviceSettingDefinitions deviceSettingDefinitions;
    private static DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;

    private static VehicleSettings vehicleSettings;
    private static VehicleSettingsByProductType vehicleSettingsByProductType;

    @BeforeClass
    public static void setUpBefore() throws Exception {
        
        SiloServiceCreator siloServiceCreator = new SiloServiceCreator("dev-pro.inthinc.com",8099);
        ConfiguratorHessianDAO configuratorHessianDAO = new ConfiguratorHessianDAO();
        configuratorHessianDAO.setSiloService(siloServiceCreator.getService());
        configuratorHessianDAO.setMapper(new ConfiguratorMapper());

        deviceSettingDefinitions = new DeviceSettingDefinitions();
        deviceSettingDefinitions.setConfiguratorDAO(configuratorHessianDAO);
        deviceSettingDefinitions.init();
        deviceSettingDefinitionsByProductType = new DeviceSettingDefinitionsByProductType();
        deviceSettingDefinitionsByProductType.setDeviceSettingDefinitions(deviceSettingDefinitions);
        deviceSettingDefinitionsByProductType.init();

        vehicleSettings = new VehicleSettings();
        vehicleSettings.setConfiguratorDAO(configuratorHessianDAO);
        vehicleSettingsByProductType = new VehicleSettingsByProductType();
        vehicleSettingsByProductType.initializeSettings(vehicleSettings.getVehicleSettings(1));

    }
    private void makeupSettingsRandom( List<DeviceSettingDefinition> settings, List<VehicleSetting> vehicleSettings){
        
        for(VehicleSetting vs: vehicleSettings){
            
            Map<Integer, String> settingMap = new HashMap<Integer, String>();
            
            for (DeviceSettingDefinition dsd: settings){
                String value = "";
                if (dsd.getChoices().size()>1){
                    
                    value =  dsd.getChoices().get(new Double(Math.random()*dsd.getChoices().size()).intValue());
                }
                else if (dsd.getVarType().equals(VarType.VTBOOLEAN)){
                    
                    value = ""+(new Double(Math.random()*2).intValue()==0);
                }
                else if (dsd.getVarType().equals(VarType.VTDOUBLE) || dsd.getVarType().equals(VarType.VTINTEGER)){
                    
                    if ((dsd.getMin() != null) && (dsd.getMax() != null)){
                        
                        int range = new Integer(Integer.parseInt(dsd.getMax()))-new Integer(Integer.parseInt(dsd.getMin()));
                        int valuePlus = new Double(Math.random()*range).intValue();
                        value=""+new Integer(Integer.parseInt(dsd.getMin())).intValue() +valuePlus;
                    }
                    else{
                        
                        value = ""+0;
                    }
                }
                else {
                    
                   value = "made_up_value";
                }
                settingMap.put(dsd.getSettingID(),value);
            }
            vs.setActual(settingMap);
            
            Map<Integer, String> desiredSettingMap = new HashMap<Integer, String>();
            for (DeviceSettingDefinition dsd: settings){
            	
                 if (dsd.getVarType().equals(VarType.VTSTRING)){
                    
                	desiredSettingMap.put(dsd.getSettingID(),"desired_value");
                }
            }
            
            vs.setDesired(desiredSettingMap);
        }
    }
    private void makeupSettingsSame( List<DeviceSettingDefinition> settings, List<VehicleSetting> vehicleSettings){
        
        for(VehicleSetting vs: vehicleSettings){
            
            Map<Integer, String> actualSettingMap = new HashMap<Integer, String>();
            
            for (DeviceSettingDefinition dsd: settings){
                String value = "";
                if (dsd.getChoices().size()>1){
                    
                    value =  dsd.getChoices().get(0);
                }
                else if (dsd.getVarType().equals(VarType.VTBOOLEAN)){
                    
                    value = ""+0;
                }
                else if (dsd.getVarType().equals(VarType.VTDOUBLE) || dsd.getVarType().equals(VarType.VTINTEGER)){
                    
                    if ((dsd.getMin() != null) && (dsd.getMax() != null)){
                        
                        value=""+dsd.getMin();
                    }
                    else{
                        
                        value = ""+0;
                    }
                }
                else {
                    
                   value = "made_up_value";
                }
                actualSettingMap.put(dsd.getSettingID(),value);
            }
            vs.setActual(actualSettingMap);
            Map<Integer, String> desiredSettingMap = new HashMap<Integer, String>();
            for (DeviceSettingDefinition dsd: settings){
            	
                 if (dsd.getVarType().equals(VarType.VTSTRING)){
                    
                	desiredSettingMap.put(dsd.getSettingID(),"desired_value");
                }
            }
            
            vs.setDesired(desiredSettingMap);
        }
    }
    
    @Test
    public void configuratorExtractConfigurationsFromVehicleSettingsNullTest(){
        ConfigurationSet configurationSet = ConfigurationExtractor.getConfigurations(null);
        assertTrue("null vehicle settings didn't return null configurations",configurationSet.getConfigurations().isEmpty());
    }
    @Test
    public void configuratorExtractConfigurationsFromVehicleSettingsNotSameTest(){
        makeupSettingsRandom(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
        ConfigurationSet configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
        
        assertEquals("3 vehicle settings should have returned 3 configurations",3,configurationSet.getConfigurations().size());
        assertFalse("should be some settings that aren't the same",configurationSet.getSettingIDsWithMoreThanOneValue().isEmpty());
    }
    @Test
    public void configuratorExtractConfigurationsFromVehicleSettingsSameTest(){
        makeupSettingsSame(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
        ConfigurationSet configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
        assertEquals("3 vehicle settings should have returned 1 configuration",1,configurationSet.getConfigurations().size());
        assertEquals("3 vehicles should be in list",3,configurationSet.getConfigurations().get(0).getVehicleIDs().size());
        assertTrue("should not be some settings that aren't the same",configurationSet.getSettingIDsWithMoreThanOneValue().isEmpty());
    }
    @Test
    public void configuratorExtractConfigurationsFromVehicleSettingsRealTest(){
        ConfigurationSet configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
//        assertEquals("3 out of 3 vehicle settings should have returned 3 configuration",3,configurationSet.getConfigurations().size());
//        assertEquals("1 vehicles should be in list",1,configurationSet.getConfigurations().get(0).getVehicleIDs().size());
//        Assert.notEmpty(configurationSet.getSettingIDsWithMoreThanOneValue(),"should be some settings that aren't the same");
    }
}
