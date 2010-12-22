package com.inthinc.pro.backing;


import java.util.HashMap;
import java.util.List;
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
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VarType;
import com.inthinc.pro.model.configurator.VehicleSetting;

@Ignore
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
        deviceSettingDefinitionsByProductType.init();
        
//        configuratorBean.setDeviceSettingDefinitionsByProductType(deviceSettingDefinitionsByProductType);
        configuratorBean.init();
        vehicleSettings = new VehicleSettings();
        vehicleSettings.filterSettings(configuratorHessianDAO.getVehicleSettingsByGroupIDDeep(1), ProductType.TIWIPRO_R74);

        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74),vehicleSettings.getVehicleSettings());

        configuratorBean.setConfiguratorDAO(configuratorHessianDAO);
    }
    private void makeupSettings( List<DeviceSettingDefinitionBean> settings, List<VehicleSetting> vehicleSettings){
        
        for(VehicleSetting vs: vehicleSettings){
            
            Map<Integer, String> settingMap = new HashMap<Integer, String>();
            
            for (DeviceSettingDefinitionBean dsdBean: settings){
            	
                String value = "";
                if (dsdBean.getChoices().size()>1){
                    
                    value =  dsdBean.getChoices().get(new Double(Math.random()*dsdBean.getChoices().size()).intValue());
                }
                else if (dsdBean.getVarType().equals(VarType.VTBOOLEAN)){
                    
                    value = ""+(new Double(Math.random()*2).intValue()==0);
                }
                else if (dsdBean.getVarType().equals(VarType.VTDOUBLE) || dsdBean.getVarType().equals(VarType.VTINTEGER)){
                    
                    if ((dsdBean.getMin() != null) && (dsdBean.getMax() != null)){
                        
                        int range = new Integer(Integer.parseInt(dsdBean.getMax()))-new Integer(Integer.parseInt(dsdBean.getMin()));
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
        }
    }

    @Test
    public void configuratorCreateConfigurationsFromVehicleSettingsTest(){
        
//        configuratorBean.buildConfigurations();
        
    }
}
