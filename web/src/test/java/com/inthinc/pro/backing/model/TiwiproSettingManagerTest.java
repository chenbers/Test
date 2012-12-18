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
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.backing.model.SensitivitySlidersMockDataCreator;
import com.inthinc.pro.backing.ui.AutologoffSetting;
import com.inthinc.pro.backing.ui.IdlingSetting;

public class TiwiproSettingManagerTest {
    private SensitivitySlidersMockDataCreator sensitivitySlidersMockDataCreator;
    private VehicleSetting vehicleSettingCustom;
    private VehicleSetting vehicleSettingSlider;
    private Integer VEHICLE_ID = 1;
    private Integer DEVICE_ID = 1;
    
    @Before
    public void setupSensitivitySliderValues(){
        
        sensitivitySlidersMockDataCreator = new SensitivitySlidersMockDataCreator();
        vehicleSettingCustom = new VehicleSetting();
        vehicleSettingCustom.setProductType(ProductType.TIWIPRO);
        vehicleSettingCustom.setVehicleID(VEHICLE_ID);
        vehicleSettingCustom.setDeviceID(DEVICE_ID);
        Map<Integer, String> desiredCustom = new HashMap<Integer,String>();
        desiredCustom.put(SettingType.HARD_ACCEL_SETTING.getSettingID(), "1 2 3");
        desiredCustom.put(SettingType.HARD_BRAKE_SETTING.getSettingID(), "1 2 3");
        desiredCustom.put(SettingType.HARD_TURN_SETTING.getSettingID(), "1 2 3");
        desiredCustom.put(SettingType.HARD_VERT_SETTING.getSettingID(), "1 2 3 4");
        desiredCustom.put(SettingType.IDLING_TIMEOUT.getSettingID(), "3600");
        vehicleSettingCustom.setDesired(desiredCustom);
        
        vehicleSettingSlider = new VehicleSetting();
        vehicleSettingSlider.setProductType(ProductType.TIWIPRO);
        vehicleSettingSlider.setVehicleID(VEHICLE_ID);
        vehicleSettingSlider.setDeviceID(DEVICE_ID);
        Map<Integer, String> desiredSlider = new HashMap<Integer,String>();
        desiredSlider.put(SettingType.HARD_ACCEL_SETTING.getSettingID(), "3000 40 1");
        desiredSlider.put(SettingType.HARD_BRAKE_SETTING.getSettingID(), "2250 100 1");
        desiredSlider.put(SettingType.HARD_TURN_SETTING.getSettingID(), "2250 100 1");
        desiredSlider.put(SettingType.HARD_VERT_SETTING.getSettingID(), "3000 900 1 300");
        vehicleSettingSlider.setDesired(desiredSlider);
    }
    
    @Test
    public void createDefaultValues(){
        
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(), 
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),
                VEHICLE_ID, DEVICE_ID);
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.createDefaultValues(VEHICLE_ID);
        assertTrue(editableVehicleSettings instanceof TiwiproEditableVehicleSettings);
        TiwiproEditableVehicleSettings tevs = (TiwiproEditableVehicleSettings) editableVehicleSettings;
        
        assertEquals(IdlingSetting.getDefault().getSeconds(), tevs.getIdlingSeconds());
    }
    @Test
    public void createFromExistingCustomValues(){
        
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),vehicleSettingCustom);
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.associateSettings(VEHICLE_ID);
        assertTrue(editableVehicleSettings instanceof TiwiproEditableVehicleSettings);
        TiwiproEditableVehicleSettings tevs = (TiwiproEditableVehicleSettings) editableVehicleSettings;
        assertEquals(new Integer(16),tevs.getHardAcceleration());
        assertEquals(new Integer(11),tevs.getHardBrake());
        assertEquals(new Integer(11),tevs.getHardTurn());
        assertEquals(new Integer(16),tevs.getHardVertical());
        assertEquals(new Integer(3600), tevs.getIdlingSeconds());
    }
    @Test
    public void createFromExistingSliderValues(){
        
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),vehicleSettingSlider);
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.associateSettings(VEHICLE_ID);
        assertTrue(editableVehicleSettings instanceof TiwiproEditableVehicleSettings);
        TiwiproEditableVehicleSettings tevs = (TiwiproEditableVehicleSettings) editableVehicleSettings;
        assertEquals(new Integer(1),tevs.getHardAcceleration());
        assertEquals(new Integer(1),tevs.getHardBrake());
        assertEquals(new Integer(1),tevs.getHardTurn());
        assertEquals(new Integer(1),tevs.getHardVertical());
    }
    @Test
    public void evaluateChangedSettings_noChanges_idleNotAddedToChanged(){
        vehicleSettingSlider = new VehicleSetting();
        vehicleSettingSlider.setDeviceID(DEVICE_ID);
        vehicleSettingSlider.setVehicleID(VEHICLE_ID);
        Map<Integer, String> settings = new HashMap<Integer,String>();
        int idlingSetting = 3210;
        settings.put(SettingType.IDLING_TIMEOUT.getSettingID(), idlingSetting+"");
        vehicleSettingSlider.setDesired(settings);
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(),vehicleSettingSlider);
        
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.createDefaultValues(VEHICLE_ID);
        ((TiwiproEditableVehicleSettings)editableVehicleSettings).setIdlingSeconds(idlingSetting);
        Map<Integer, String> changes = tiwiproSettingManager.evaluateSettings(VEHICLE_ID, editableVehicleSettings, null);     
        assertEquals(idlingSetting+"", changes.get(SettingType.IDLING_TIMEOUT.getSettingID()));
    }
    @Test
    public void evaluateChangedSettings_newIdlingValue_newIdleGetsAddedToChangedSettings(){
        vehicleSettingSlider = new VehicleSetting();
        vehicleSettingSlider.setDeviceID(DEVICE_ID);
        vehicleSettingSlider.setVehicleID(VEHICLE_ID);
        Map<Integer, String> settings = new HashMap<Integer,String>();
        int actualIdling = 3210;
        int desiredIdling = 1230;
        settings.put(SettingType.IDLING_TIMEOUT.getSettingID(), actualIdling+"");
        vehicleSettingSlider.setActual(settings);
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(), vehicleSettingSlider);
        
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.createDefaultValues(VEHICLE_ID);
        ((TiwiproEditableVehicleSettings)editableVehicleSettings).setIdlingSeconds(desiredIdling);
        Map<Integer, String> changes = tiwiproSettingManager.evaluateSettings(VEHICLE_ID, editableVehicleSettings, null);
        assertEquals(desiredIdling+"", changes.get(SettingType.IDLING_TIMEOUT.getSettingID()));
    }
    @Test
    public void evaluateChangedSettings_newIdlingBuzzerValue_newIdleBuzzerGetsAddedToChangedSettings(){
        vehicleSettingSlider = new VehicleSetting();
        vehicleSettingSlider.setDeviceID(DEVICE_ID);
        vehicleSettingSlider.setVehicleID(VEHICLE_ID);
        Map<Integer, String> settings = new HashMap<Integer,String>();
        int actualIdling = 3210;
        settings.put(SettingType.IDLING_TIMEOUT.getSettingID(), actualIdling+"");
        settings.put(SettingType.BUZZER_IDLE.getSettingID(), 0+"");
        vehicleSettingSlider.setActual(settings);
        VehicleSettingManager tiwiproSettingManager = new TiwiproSettingManager(new ConfiguratorHessianDAO(),  
                sensitivitySlidersMockDataCreator.getSensitivitySliders(), vehicleSettingSlider);
        
        EditableVehicleSettings editableVehicleSettings = tiwiproSettingManager.createDefaultValues(VEHICLE_ID);
        ((TiwiproEditableVehicleSettings)editableVehicleSettings).setIdleBuzzer(true);
        Map<Integer, String> changes = tiwiproSettingManager.evaluateSettings(VEHICLE_ID, editableVehicleSettings, null);
        assertEquals(1+"", changes.get(SettingType.BUZZER_IDLE.getSettingID()));
    }
    
}
