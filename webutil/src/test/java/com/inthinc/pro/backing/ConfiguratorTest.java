package com.inthinc.pro.backing;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.backing.configurator.ConfiguratorBean;
import com.inthinc.pro.backing.configurator.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.backing.configurator.VehicleSettingsByProductType;
import com.inthinc.pro.dao.hessian.ConfiguratorHessianDAO;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.DeviceSettingDefinitions;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.VehicleSettings;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.VarType;

public class ConfiguratorTest {
    
    private DeviceSettingDefinitions deviceSettingDefinitions;
    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    private VehicleSettings vehicleSettings;
    private VehicleSettingsByProductType vehicleSettingsByProductType;
    private ConfiguratorBean configuratorBean;
    
    @Before
    public void setUp() throws Exception {
       
        SiloServiceCreator siloServiceCreator = new SiloServiceCreator("dev-pro.inthinc.com",8099);
        ConfiguratorHessianDAO configuratorHessianDAO = new ConfiguratorHessianDAO();
        configuratorHessianDAO.setSiloService(siloServiceCreator.getService());
        configuratorHessianDAO.setMapper(new ConfiguratorMapper());
        
        configuratorBean = new ConfiguratorBean();
        deviceSettingDefinitions = new DeviceSettingDefinitions();
        deviceSettingDefinitions.setConfiguratorDAO(configuratorHessianDAO);
        deviceSettingDefinitions.init();
        deviceSettingDefinitionsByProductType = new DeviceSettingDefinitionsByProductType();
        deviceSettingDefinitionsByProductType.setDeviceSettingDefinitions(deviceSettingDefinitions);
        deviceSettingDefinitionsByProductType.init();
        
        configuratorBean.setupConfigurator();
        configuratorBean.setDeviceSettingDefinitionsByProductType(deviceSettingDefinitionsByProductType);
        vehicleSettings = new VehicleSettings();
        vehicleSettings.setConfiguratorDAO(configuratorHessianDAO);
        vehicleSettingsByProductType = new VehicleSettingsByProductType();
        vehicleSettingsByProductType.initializeSettings(vehicleSettings.getVehicleSettings(1));
        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));

        configuratorBean.setVehicleSettingsByProductType(vehicleSettingsByProductType);
    }
    private void makeupSettings( List<DeviceSettingDefinition> settings, List<VehicleSetting> vehicleSettings){
        
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

    @Test
    public void configuratorCreateConfigurationsFromVehicleSettingsTest(){
        
        configuratorBean.buildSettings();
        
//        System.out.println(configuratorBean.getConfigurationsTree().postOrderTraverse(configuratorBean.getConfigurationsTree(), null));
//        
//        for(SettingOptions settingOption :configuratorBean.getSettingsConfigurations()){
// 
//            for(String value:settingOption.getValuesList()){
//                
//                System.out.print(value+" ");
//            }
//            System.out.println(settingOption.getCount());
//        }
//        System.out.println("");
    }
}
