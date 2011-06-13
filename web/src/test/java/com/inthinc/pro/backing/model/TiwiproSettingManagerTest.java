package com.inthinc.pro.backing.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.backing.TiwiproEditableVehicleSettings;
import com.inthinc.pro.backing.TiwiproSettingManager;
import com.inthinc.pro.dao.hessian.ConfiguratorHessianDAO;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.backing.model.SensitivitySlidersMockDataCreator;

public class TiwiproSettingManagerTest {
    private SensitivitySlidersMockDataCreator sensitivitySlidersMockDataCreator;
    private VehicleSetting vehicleSettingCustom;
    private VehicleSetting vehicleSettingSlider;
    @Before
    public void setupSensitivitySliderValues(){
        
        sensitivitySlidersMockDataCreator = new SensitivitySlidersMockDataCreator();
        vehicleSettingCustom = new VehicleSetting();
        vehicleSettingCustom.setProductType(ProductType.TIWIPRO_R74);
        vehicleSettingCustom.setVehicleID(1);
        vehicleSettingCustom.setDeviceID(1);
        Map<Integer, String> desiredCustom = new HashMap<Integer,String>();
        desiredCustom.put(157, "1 2 3");
        desiredCustom.put(158, "1 2 3");
        desiredCustom.put(159, "1 2 3");
        desiredCustom.put(160, "1 2 3 4");
        vehicleSettingCustom.setDesired(desiredCustom);
        
        vehicleSettingSlider = new VehicleSetting();
        vehicleSettingSlider.setProductType(ProductType.TIWIPRO_R74);
        vehicleSettingSlider.setVehicleID(1);
        vehicleSettingSlider.setDeviceID(1);
        Map<Integer, String> desiredSlider = new HashMap<Integer,String>();
        desiredSlider.put(157, "3000 40 1");
        desiredSlider.put(158, "2250 100 1");
        desiredSlider.put(159, "2250 100 1");
        desiredSlider.put(160, "3000 900 1 300");
        vehicleSettingSlider.setDesired(desiredSlider);
    }
    
    @Test
    public void createDefaultValues(){
        
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(), 
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),
                ProductType.TIWIPRO_R74, 1,1);
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.createDefaultValues(1);
        assertTrue(editableVehicleSettings instanceof TiwiproEditableVehicleSettings);
        }
    @Test
    public void createFromExistingCustomValues(){
        
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),
                ProductType.TIWIPRO_R74,vehicleSettingCustom);
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.associateSettings(1);
        assertTrue(editableVehicleSettings instanceof TiwiproEditableVehicleSettings);
        TiwiproEditableVehicleSettings tevs = (TiwiproEditableVehicleSettings) editableVehicleSettings;
        assertEquals(new Integer(16),tevs.getHardAcceleration());
        assertEquals(new Integer(11),tevs.getHardBrake());
        assertEquals(new Integer(11),tevs.getHardTurn());
        assertEquals(new Integer(16),tevs.getHardVertical());
    }
    @Test
    public void createFromExistingSliderValues(){
        
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),
                ProductType.TIWIPRO_R74, vehicleSettingSlider);
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.associateSettings(1);
        assertTrue(editableVehicleSettings instanceof TiwiproEditableVehicleSettings);
        TiwiproEditableVehicleSettings tevs = (TiwiproEditableVehicleSettings) editableVehicleSettings;
        assertEquals(new Integer(1),tevs.getHardAcceleration());
        assertEquals(new Integer(1),tevs.getHardBrake());
        assertEquals(new Integer(1),tevs.getHardTurn());
        assertEquals(new Integer(1),tevs.getHardVertical());
    }

}
