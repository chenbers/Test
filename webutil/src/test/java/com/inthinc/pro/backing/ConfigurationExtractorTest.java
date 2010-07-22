package com.inthinc.pro.backing;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.backing.model.ConfigurationExtractor;
import com.inthinc.pro.backing.model.ConfigurationSet;
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

    private DeviceSettingDefinitions deviceSettingDefinitions;
    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;

    private VehicleSettings vehicleSettings;
    private VehicleSettingsByProductType vehicleSettingsByProductType;

    @Before
    public void setUp() throws Exception {
        
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
        }
    }
    private void makeupSettingsSame( List<DeviceSettingDefinition> settings, List<VehicleSetting> vehicleSettings){
        
        for(VehicleSetting vs: vehicleSettings){
            
            Map<Integer, String> settingMap = new HashMap<Integer, String>();
            
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
                settingMap.put(dsd.getSettingID(),value);
            }
            vs.setActual(settingMap);
        }
    }
    
    @Test
    public void configuratorExtractConfigurationsFromVehicleSettingsNullTest(){
        
        assertEquals("null vehicle settings didn't return null configurations",ConfigurationExtractor.getConfigurations(null),null);
    }
    @Test
    public void configuratorExtractConfigurationsFromVehicleSettingsNotSameTest(){
        makeupSettingsRandom(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));

        assertEquals("3 vehicle settings should have returned 3 configurations",3,ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74)).getConfigurations().size());
    }
    @Test
    public void configuratorExtractConfigurationsFromVehicleSettingsSameTest(){
        makeupSettingsSame(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
        ConfigurationSet cs = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
        assertEquals("3 vehicle settings should have returned 1 configuration",1,cs.getConfigurations().size());
        assertEquals("3 vehicles should be in list",3,cs.getConfigurations().get(0).getVehicleIDs().size());
    }
    @Test
    public void configuratorExtractConfigurationsFromVehicleSettingsRealTest(){
//        makeupSettingsSame(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
        ConfigurationSet cs = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
        assertEquals("3 out of 3 vehicle settings should have returned 3 configuration",3,cs.getConfigurations().size());
        assertEquals("1 vehicles should be in list",1,cs.getConfigurations().get(0).getVehicleIDs().size());
    }

}
