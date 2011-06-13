package com.inthinc.pro.backing.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.backing.WaySmartEditableVehicleSettings;
import com.inthinc.pro.backing.WaySmartSettingManager;
import com.inthinc.pro.dao.hessian.ConfiguratorHessianDAO;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class WaysmartSettingManagerTest {
    private SensitivitySlidersMockDataCreator sensitivitySlidersMockDataCreator;
    private VehicleSetting vehicleSettingCustom;
    private VehicleSetting vehicleSettingSlider;
    @Before
    public void setupSensitivitySliderValues(){
        sensitivitySlidersMockDataCreator = new SensitivitySlidersMockDataCreator();
        vehicleSettingCustom = new VehicleSetting();
        vehicleSettingCustom.setProductType(ProductType.WAYSMART);
        vehicleSettingCustom.setVehicleID(1);
        vehicleSettingCustom.setDeviceID(1);
        Map<Integer, String> desiredCustom = new HashMap<Integer,String>();
        desiredCustom.put(1231, "1000000");
        desiredCustom.put(1229, "1000000");
        
        desiredCustom.put(1228, "1000000");
        desiredCustom.put(1226, "1000000");

        desiredCustom.put(1234, "1000000");
        desiredCustom.put(1232, "1000000");

        desiredCustom.put(1225, "1000000");
        desiredCustom.put(1224, "1000000");
        desiredCustom.put(1165, "1000000");

        vehicleSettingCustom.setDesired(desiredCustom);
        
        vehicleSettingSlider = new VehicleSetting();
        vehicleSettingSlider.setProductType(ProductType.WAYSMART);
        vehicleSettingSlider.setVehicleID(1);
        vehicleSettingSlider.setDeviceID(1);
        Map<Integer, String> desiredSlider = new HashMap<Integer,String>();
        desiredSlider.put(1231, "225");
        desiredSlider.put(1229, "10");
        
        desiredSlider.put(1228, "225");
        desiredSlider.put(1226, "10");

        desiredSlider.put(1234, "300");
        desiredSlider.put(1232, "4");

        desiredSlider.put(1225, "600");
        desiredSlider.put(1224, "25");
        desiredSlider.put(1165, "750");
        vehicleSettingSlider.setDesired(desiredSlider);
    }
    @Test
    public void createDefaultValues(){
        
        WaySmartSettingManager waySmartSettingManager = new WaySmartSettingManager(new ConfiguratorHessianDAO(), 
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),
                ProductType.WAYSMART, 1,1);
        
        EditableVehicleSettings editableVehicleSettings = waySmartSettingManager.createDefaultValues(1);
        assertTrue(editableVehicleSettings instanceof WaySmartEditableVehicleSettings);
        }
    @Test
    public void createFromExistingCustomValues(){
        
        WaySmartSettingManager waySmartSettingManager = new WaySmartSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),
                ProductType.WAYSMART,vehicleSettingCustom);
       
        EditableVehicleSettings editableVehicleSettings = waySmartSettingManager.associateSettings(1);
        assertTrue(editableVehicleSettings instanceof WaySmartEditableVehicleSettings);
        WaySmartEditableVehicleSettings tevs = (WaySmartEditableVehicleSettings) editableVehicleSettings;
        assertEquals(new Integer(16),tevs.getHardAcceleration());
        assertEquals(new Integer(11),tevs.getHardBrake());
        assertEquals(new Integer(11),tevs.getHardTurn());
        assertEquals(new Integer(18),tevs.getHardVertical());
    }
    @Test
    public void createFromExistingSliderValues(){
        
        WaySmartSettingManager waySmartSettingManager = new WaySmartSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),
                ProductType.WAYSMART,vehicleSettingSlider);
        EditableVehicleSettings editableVehicleSettings = waySmartSettingManager.associateSettings(1);
        assertTrue(editableVehicleSettings instanceof WaySmartEditableVehicleSettings);
        WaySmartEditableVehicleSettings tevs = (WaySmartEditableVehicleSettings) editableVehicleSettings;
        assertEquals(new Integer(1),tevs.getHardAcceleration());
        assertEquals(new Integer(1),tevs.getHardBrake());
        assertEquals(new Integer(1),tevs.getHardTurn());
        assertEquals(new Integer(1),tevs.getHardVertical());
    }
    
}
