package com.inthinc.pro.backing;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.backing.configurator.ConfiguratorBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitions;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.model.VehicleSettings;
import com.inthinc.pro.dao.hessian.ConfiguratorHessianDAO;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VarType;
import com.inthinc.pro.model.configurator.VehicleSetting;


public class ConfiguratorTest {
    
    private DeviceSettingDefinitions deviceSettingDefinitions;
    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    private static ConfiguratorHessianDAO configuratorHessianDAO;
    private VehicleSettings vehicleSettings;
    private ConfiguratorBean configuratorBean;
    
    @Before
    public void setUp() throws Exception {
       
        SiloServiceCreator siloServiceCreator = new SiloServiceCreator("dev-pro.inthinc.com",8099);
        configuratorHessianDAO = new ConfiguratorHessianDAO();
        configuratorHessianDAO.setSiloService(siloServiceCreator.getService());
        configuratorHessianDAO.setMapper(new ConfiguratorMapper());
        
        configuratorBean = new ConfiguratorBean();
        deviceSettingDefinitions = new DeviceSettingDefinitions();
        deviceSettingDefinitions.setConfiguratorDAO(configuratorHessianDAO);
        deviceSettingDefinitions.init();
        deviceSettingDefinitionsByProductType = new DeviceSettingDefinitionsByProductType();
//        deviceSettingDefinitionsByProductType.setDeviceSettingDefinitions(deviceSettingDefinitions);
        deviceSettingDefinitionsByProductType.init();
        
        configuratorBean.setDeviceSettingDefinitionsByProductType(deviceSettingDefinitionsByProductType);
//        configuratorBean.init();
        vehicleSettings = new VehicleSettings();
        vehicleSettings.filterSettings(configuratorHessianDAO.getVehicleSettingsByGroupIDDeep(1), ProductType.TIWIPRO);
//
//        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO),vehicleSettings.getVehicleSettings());
//
//        configuratorBean.setConfiguratorDAO(configuratorHessianDAO);
    }
//    private void makeupSettings( List<DeviceSettingDefinitionBean> settings, List<VehicleSetting> vehicleSettings){
//        
//        for(VehicleSetting vs: vehicleSettings){
//            
//            Map<Integer, String> settingMap = new HashMap<Integer, String>();
//            
//            for (DeviceSettingDefinitionBean dsdBean: settings){
//            	
//                String value = "";
//                if (dsdBean.getChoices().size()>1){
//                    
//                    value =  dsdBean.getChoices().get(new Double(Math.random()*dsdBean.getChoices().size()).intValue());
//                }
//                else if (dsdBean.getVarType().equals(VarType.VTBOOLEAN)){
//                    
//                    value = ""+(new Double(Math.random()*2).intValue()==0);
//                }
//                else if (dsdBean.getVarType().equals(VarType.VTDOUBLE) || dsdBean.getVarType().equals(VarType.VTINTEGER)){
//                    
//                    if ((dsdBean.getMin() != null) && (dsdBean.getMax() != null)){
//                        
//                        int range = new Integer(Integer.parseInt(dsdBean.getMax()))-new Integer(Integer.parseInt(dsdBean.getMin()));
//                        int valuePlus = new Double(Math.random()*range).intValue();
//                        value=""+new Integer(Integer.parseInt(dsdBean.getMin())).intValue() +valuePlus;
//                    }
//                    else{
//                        
//                        value = ""+0;
//                    }
//                }
//                else {
//                    
//                   value = "made_up_value";
//                }
//                settingMap.put(dsdBean.getSettingID(),value);
//            }
//            vs.setActual(settingMap);
//        }
//    }

    @Test
    @Ignore
    public void configuratorCreateConfigurationsFromVehicleSettingsTest(){
        
//        configuratorBean.buildConfigurations();
        
    }
    private void normalizeNumbers(VehicleSetting vehicleSetting){ 
    	
    	normalizeNumbers(vehicleSetting.getActual());
    	normalizeNumbers(vehicleSetting.getDesired());
    }
    private void normalizeNumbers(Map<Integer,String> settingMap){
    	for (Integer settingID : settingMap.keySet()){
    		if (settingIsDouble(settingID)){
    			String number = normalizeNumber(settingMap.get(settingID));
    			settingMap.put(settingID, number);
    		}
    	}
    }
    private boolean settingIsDouble(Integer settingID){
    	DeviceSettingDefinitionBean dsdb = DeviceSettingDefinitions.getDeviceSettingDefinition(settingID);
    	if (dsdb == null) {
    		assertNull("Setting with settingID "+settingID+" is being used, but is not in the settingDef table.",dsdb);
    		return false;
    	}
    	VarType varType = dsdb.getVarType();
    	return VarType.VTDOUBLE.equals(varType);
    }
    private String normalizeNumber(String number){
    	return ""+NumberUtil.convertStringToDouble(number);
    }
    
    @Test
    public void numberNormalizerTest(){
    	String normalizedNumber = normalizeNumber("25.500");
    	assertEquals("25.5", normalizedNumber);
    	normalizedNumber = normalizeNumber("25.506");
    	assertEquals("25.506", normalizedNumber);
    	normalizedNumber = normalizeNumber("00025.506");
    	assertEquals("25.506", normalizedNumber);
    	normalizedNumber = normalizeNumber("0.00000");
    	assertEquals("0.0", normalizedNumber);
    	normalizedNumber = normalizeNumber(null);
    	assertEquals("0.0", normalizedNumber);
    	normalizedNumber = normalizeNumber("-00025.506");
    	assertEquals("-25.506", normalizedNumber);
    }
    @Test
    public void picksSettingIsDoubleTest(){
    	VehicleSetting vehicleSetting = new VehicleSetting();
    	Map<Integer, String> actualSetting = new HashMap<Integer, String>();
    	Map<Integer, String> desiredSetting = new HashMap<Integer, String>();
    	actualSetting.put(14, "25.5000");
    	vehicleSetting.setActual(actualSetting);
    	vehicleSetting.setDesired(desiredSetting);
    	
    	assertTrue(settingIsDouble(14));
    	
    }
    @Test
    public void nonExistentSettingTest(){
    	settingIsDouble(777777);
    }
}
