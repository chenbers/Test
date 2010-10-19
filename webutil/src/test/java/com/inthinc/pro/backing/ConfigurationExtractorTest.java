package com.inthinc.pro.backing;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.configurator.model.ConfigurationExtractor;
import com.inthinc.pro.configurator.model.ConfigurationSet;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitions;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.model.VehicleSettings;
import com.inthinc.pro.dao.hessian.ConfiguratorHessianDAO;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VarType;
import com.inthinc.pro.model.configurator.VehicleSetting;
@Ignore
public class ConfigurationExtractorTest {

    private static DeviceSettingDefinitions deviceSettingDefinitions;
    private static DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;

    private static ConfiguratorHessianDAO configuratorHessianDAO;
    private static VehicleSettings vehicleSettings;

    @BeforeClass
    public static void setUpBefore() throws Exception {
        
        SiloServiceCreator siloServiceCreator = new SiloServiceCreator("dev-pro.inthinc.com",8099);
        
        configuratorHessianDAO = new ConfiguratorHessianDAO();
        configuratorHessianDAO.setSiloService(siloServiceCreator.getService());
        configuratorHessianDAO.setMapper(new ConfiguratorMapper());

        deviceSettingDefinitions = new DeviceSettingDefinitions();
        deviceSettingDefinitions.setConfiguratorDAO(configuratorHessianDAO);
        deviceSettingDefinitions.init();
        deviceSettingDefinitionsByProductType = new DeviceSettingDefinitionsByProductType();
        deviceSettingDefinitionsByProductType.init();

        vehicleSettings = new VehicleSettings();
        vehicleSettings.filterSettings(configuratorHessianDAO.getVehicleSettingsByGroupIDDeep(1), ProductType.TIWIPRO_R74);

    }
    private void makeupSettingsRandom( List<DeviceSettingDefinitionBean> list, List<VehicleSetting> vehicleSettings){
        
        for(VehicleSetting vs: vehicleSettings){
            
            Map<Integer, String> settingMap = new HashMap<Integer, String>();
            
            for (DeviceSettingDefinitionBean dsdBean: list){
            	
                String value = "";
                if (dsdBean.getChoices().size()>1){
                    
                    value =  dsdBean.getChoices().get(new Double(Math.random()*dsdBean.getChoices().size()).intValue());
                }
                else if (dsdBean.getVarType().equals(VarType.VTBOOLEAN)){
                    
                    value = ""+(new Double(Math.random()*2).intValue()==0);
                }
                else if (dsdBean.getVarType().equals(VarType.VTDOUBLE) || dsdBean.getVarType().equals(VarType.VTINTEGER)){
                    
                    if ((dsdBean.getMin() != null) && (dsdBean.getMax() != null)){
                        
                        int range = Integer.parseInt(dsdBean.getMax())-(Integer.parseInt(dsdBean.getMin()));
                        int valuePlus = new Double(Math.random()*range).intValue();
                        value=""+new Integer(Integer.parseInt(dsdBean.getMin())).intValue() +valuePlus;
                    }
                    else{
                        
                        value = ""+0;
                    }
                }
                else {
                    
                   value = "made_up_value";
                }
                settingMap.put(dsdBean.getSettingID(),value);
            }
            vs.setActual(settingMap);
            
            Map<Integer, String> desiredSettingMap = new HashMap<Integer, String>();
            for (DeviceSettingDefinitionBean dsdBean: list){
            	
                  if (dsdBean.getVarType().equals(VarType.VTSTRING)){
                    
                	desiredSettingMap.put(dsdBean.getSettingID(),"desired_value");
                }
            }
            
            vs.setDesired(desiredSettingMap);
            vs.clearCombinedSettings();
            vs.combineSettings();
        }
    }
    private void makeupSettingsSame( List<DeviceSettingDefinitionBean> list, List<VehicleSetting> vehicleSettings){
        
        for(VehicleSetting vs: vehicleSettings){
            
            Map<Integer, String> actualSettingMap = new HashMap<Integer, String>();
            
            for (DeviceSettingDefinitionBean dsdBean: list){
                String value = "";
                if (dsdBean.getChoices().size()>1){
                    
                    value =  dsdBean.getChoices().get(0);
                }
                else if (dsdBean.getVarType().equals(VarType.VTBOOLEAN)){
                    
                    value = ""+0;
                }
                else if (dsdBean.getVarType().equals(VarType.VTDOUBLE) || dsdBean.getVarType().equals(VarType.VTINTEGER)){
                    
                    if ((dsdBean.getMin() != null) && (dsdBean.getMax() != null)){
                        
                        value=""+dsdBean.getMin();
                    }
                    else{
                        
                        value = ""+0;
                    }
                }
                else {
                    
                   value = "made_up_value";
                }
                actualSettingMap.put(dsdBean.getSettingID(),value);
            }
            vs.setActual(actualSettingMap);
            Map<Integer, String> desiredSettingMap = new HashMap<Integer, String>();
            for (DeviceSettingDefinitionBean dsdBean: list){
            	
                 if (dsdBean.getVarType().equals(VarType.VTSTRING)){
                    
                	desiredSettingMap.put(dsdBean.getSettingID(),"desired_value");
                }
            }
            
            vs.setDesired(desiredSettingMap);
            vs.clearCombinedSettings();
            vs.combineSettings();
        }
    }
//    @Ignore
//    @Test
//    public void configuratorExtractConfigurationsFromVehicleSettingsNullTest(){
//        ConfigurationSet configurationSet = ConfigurationExtractor.getConfigurations(null,null);
//        assertTrue("null vehicle settings didn't return null configurations",configurationSet.getConfigurations().isEmpty());
//    }
//    @Ignore
//    @Test
//    public void configuratorExtractConfigurationsFromVehicleSettingsNotSameTest(){
//        makeupSettingsRandom(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettings.getVehicleSettings());
//        ConfigurationSet configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettings.getVehicleSettings(),
//        		deviceSettingDefinitionsByProductType.getKeys(ProductType.TIWIPRO_R74));
//        
//        assertEquals("3 vehicle settings should have returned 3 configurations",3,configurationSet.getConfigurations().size());
//        assertFalse("should be some settings that aren't the same",configurationSet.getSettingIDsWithMoreThanOneValue().isEmpty());
//    }
//    @Ignore
//    @Test
//    public void configuratorExtractConfigurationsFromVehicleSettingsSameTest(){
//        makeupSettingsSame(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettings.getVehicleSettings());
//        ConfigurationSet configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettings.getVehicleSettings(),
//        		deviceSettingDefinitionsByProductType.getKeys(ProductType.TIWIPRO_R74));
//        assertEquals("3 vehicle settings should have returned 1 configuration",1,configurationSet.getConfigurations().size());
//        assertEquals("3 vehicles should be in list",3,configurationSet.getConfigurations().get(0).getVehicleIDs().size());
//        assertTrue("should not be some settings that aren't the same",configurationSet.getSettingIDsWithMoreThanOneValue().isEmpty());
//    }
//    @Ignore
//    @Test
//    public void configuratorExtractConfigurationsFromVehicleSettingsRealTest(){
//        ConfigurationSet configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettings.getVehicleSettings(),
//        		deviceSettingDefinitionsByProductType.getKeys(ProductType.TIWIPRO_R74));
//        assertEquals("3 out of 3 vehicle settings should have returned 3 configuration",3,configurationSet.getConfigurations().size());
//        assertEquals("1 vehicles should be in list",1,configurationSet.getConfigurations().get(0).getVehicleIDs().size());
//        assertFalse("should be some settings that aren't the same",configurationSet.getSettingIDsWithMoreThanOneValue().isEmpty());
//    }
}
