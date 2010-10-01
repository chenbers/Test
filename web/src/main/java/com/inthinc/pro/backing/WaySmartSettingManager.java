package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.backing.model.WaySmartSettingsAndSliders;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.SensitivityMapping;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivityType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class WaySmartSettingManager extends VehicleSettingManager {

//    private static final Integer CUSTOM_SLIDER_VALUE = 99;
 
    public WaySmartSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,vehicleSetting);
        properties = new Properties();
        properties.put("defaultSettings",SensitivityType.getDefaultSettings());
        properties.put("sensitivities", SensitivityType.getSensitivities());
        properties.put("settingCounts",new HashMap<Integer,Map<Integer,Integer>>());

    }
    @Override
    public void init() {
        // TODO Auto-generated method stub
    }
    @Override
    public VehicleSettingAdapter associateSettings(Integer vehicleID){
        
        if (vehicleSetting == null){
            
            return createDefaultValues(vehicleID); 

        }
        else {
            return createFromExistingValues(vehicleSetting);
        }
    }
    private VehicleSettingAdapter createDefaultValues(Integer vehicleID){
        
        Integer speedLimit = 5;
        Integer speedBuffer = 10;
        Integer severeSpeed = 15;
        Integer hardVertical = SensitivityType.WS_HARD_VERT_SETTING.getDefaultSetting();
        Integer hardTurn = SensitivityType.WS_HARD_TURN_SETTING.getDefaultSetting();
        Integer hardAcceleration = SensitivityType.WS_HARD_ACCEL_SETTING.getDefaultSetting();
        Integer hardBrake = SensitivityType.WS_HARD_BRAKE_SETTING.getDefaultSetting();
        
        return new WaySmartSettingAdapter(vehicleID==null?-1:vehicleID, speedLimit,speedBuffer,severeSpeed,
                                        hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    @SuppressWarnings("unchecked")
    private VehicleSettingAdapter createFromExistingValues(VehicleSetting vs){
        
        Integer speedLimit = convertString(vs.getCombined(SensitivityType.SPEED_LIMIT.getSettingID()));
        Integer speedBuffer = convertString(vs.getCombined(SensitivityType.SPEED_BUFFER.getSettingID()));
        Integer severeSpeed = convertString(vs.getCombined(SensitivityType.SEVERE_SPEED.getSettingID()));
 
        Integer hardVertical = WaySmartSettingsAndSliders.HARD_VERT_SETTING.getSettingsAndSliders().getSlider(getSettingsForHardVertical(vs));
        Integer hardTurn = WaySmartSettingsAndSliders.HARD_TURN_SETTING.getSettingsAndSliders().getSlider(getSettingsForHardTurn(vs));
        Integer hardAcceleration = WaySmartSettingsAndSliders.HARD_ACCEL_SETTING.getSettingsAndSliders().getSlider(getSettingsForHardAccel(vs));
        Integer hardBrake = WaySmartSettingsAndSliders.HARD_BRAKE_SETTING.getSettingsAndSliders().getSlider(getSettingsForHardBrake(vs));

        Map<Integer,Map<SensitivityType,Integer>> settingCounts = (Map<Integer,Map<SensitivityType,Integer>>)properties.get("settingCounts");
        settingCounts.put(vs.getVehicleID(),adjustedSettingCounts(hardVertical,hardTurn,hardAcceleration,hardBrake));
        return new WaySmartSettingAdapter(vs.getVehicleID(), speedLimit,speedBuffer,severeSpeed, 
                                          hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    private Map<SensitivityType, String> getSettingsForHardVertical(VehicleSetting vehicleSetting){
        
        Map<SensitivityType, String> settings = new HashMap<SensitivityType, String>();
        settings.put(SensitivityType.RMS_LEVEL, vehicleSetting.getCombined(SensitivityType.RMS_LEVEL.getSettingID()));
        settings.put(SensitivityType.SEVERE_HARDVERT_LEVEL, vehicleSetting.getCombined(SensitivityType.SEVERE_HARDVERT_LEVEL.getSettingID()));
        settings.put(SensitivityType.HARDVERT_DMM_PEAKTOPEAK, vehicleSetting.getCombined(SensitivityType.HARDVERT_DMM_PEAKTOPEAK.getSettingID()));

        return settings;
    }
    private Map<SensitivityType, String> getSettingsForHardTurn(VehicleSetting vehicleSetting){
        
        Map<SensitivityType, String> settings = new HashMap<SensitivityType, String>();
        settings.put(SensitivityType.Y_LEVEL, vehicleSetting.getCombined(SensitivityType.Y_LEVEL.getSettingID()));
        settings.put(SensitivityType.DVY, vehicleSetting.getCombined(SensitivityType.DVY.getSettingID()));

        return settings;
    }
    private Map<SensitivityType, String> getSettingsForHardAccel(VehicleSetting vehicleSetting){
        
        Map<SensitivityType, String> settings = new HashMap<SensitivityType, String>();
        settings.put(SensitivityType.HARD_ACCEL_LEVEL, vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_LEVEL.getSettingID()));
        settings.put(SensitivityType.HARD_ACCEL_DELTAV, vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_DELTAV.getSettingID()));

        return settings;
    }
    private Map<SensitivityType, String> getSettingsForHardBrake(VehicleSetting vehicleSetting){
        
        Map<SensitivityType, String> settings = new HashMap<SensitivityType, String>();
        settings.put(SensitivityType.X_ACCEL, vehicleSetting.getCombined(SensitivityType.X_ACCEL.getSettingID()));
        settings.put(SensitivityType.DVX, vehicleSetting.getCombined(SensitivityType.DVX.getSettingID()));

        return settings;
    }
   private Map<SensitivityType,Integer> adjustedSettingCounts(Integer hardVertical, Integer hardTurn,Integer hardAcceleration, Integer hardBrake){
       
       Map<SensitivityType,Integer> settingCount = new HashMap<SensitivityType, Integer>();
       
       settingCount.put(SensitivityType.WS_HARD_ACCEL_SETTING, SensitivityType.WS_HARD_ACCEL_SETTING.getSettingsCount()+(hardAcceleration==99?1:0));
       settingCount.put(SensitivityType.WS_HARD_BRAKE_SETTING, SensitivityType.WS_HARD_BRAKE_SETTING.getSettingsCount()+(hardBrake==99?1:0));
       settingCount.put(SensitivityType.WS_HARD_TURN_SETTING,  SensitivityType.WS_HARD_TURN_SETTING.getSettingsCount()+(hardTurn==99?1:0));
       settingCount.put(SensitivityType.WS_HARD_VERT_SETTING,  SensitivityType.WS_HARD_VERT_SETTING.getSettingsCount()+(hardVertical==99?1:0));

       return settingCount;
  }
    @Override
    public Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter) {
        if (vehicleSetting == null) {
            
            vehicleSetting = createVehicleSetting(vehicleID);
        }
        Map<Integer, String> desiredSettings = new HashMap<Integer, String>();
        
        //need to get individual settings from sliders and fields
        
        if(isRequested(batchEdit,updateField,SensitivityType.SPEED_LIMIT)){
            desiredSettings = addIfDifferent(SensitivityType.SPEED_LIMIT.getSettingID(), 
                    ""+vehicleSettingAdapter.getProperties().get("speed_limit"), 
                    vehicleSetting.getCombined(SensitivityType.SPEED_LIMIT.getSettingID()),
                    desiredSettings);
        }
        if(isRequested(batchEdit,updateField,SensitivityType.SPEED_BUFFER)){
        desiredSettings = addIfDifferent(SensitivityType.SPEED_BUFFER.getSettingID(), 
                ""+vehicleSettingAdapter.getProperties().get("speed_buffer"), 
                vehicleSetting.getCombined(SensitivityType.SPEED_BUFFER.getSettingID()),
                desiredSettings);
        }
        if(isRequested(batchEdit,updateField,SensitivityType.SEVERE_SPEED)){
        desiredSettings = addIfDifferent(SensitivityType.SEVERE_SPEED.getSettingID(), 
                ""+vehicleSettingAdapter.getProperties().get("severe_speed"), 
                vehicleSetting.getCombined(SensitivityType.SEVERE_SPEED.getSettingID()),
                desiredSettings);
        }
        if(isRequested(batchEdit,updateField,SensitivityType.WS_HARD_VERT_SETTING)){
        desiredSettings =  getSliderValuesForHardVertical(desiredSettings,vehicleSettingAdapter);
        }
        if(isRequested(batchEdit,updateField,SensitivityType.WS_HARD_ACCEL_SETTING)){
        desiredSettings =  getSliderValuesForHardAcceleration(desiredSettings,vehicleSettingAdapter);
        }
        if(isRequested(batchEdit,updateField,SensitivityType.WS_HARD_BRAKE_SETTING)){
        desiredSettings =  getSliderValuesForHardBrake(desiredSettings,vehicleSettingAdapter);
        }
        if(isRequested(batchEdit,updateField,SensitivityType.WS_HARD_TURN_SETTING)){
        desiredSettings =  getSliderValuesForHardTurn(desiredSettings,vehicleSettingAdapter);
        }
        
        return desiredSettings;
        
    }

    @Override
    public Map<Integer, String> evaluateSettings(Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter) {
        
        if (vehicleSetting == null) {
            
            vehicleSetting = createVehicleSetting(vehicleID);
        }
        Map<Integer, String> desiredSettings = new HashMap<Integer, String>();
        //need to get individual settings from sliders and fields
        
        desiredSettings = addIfDifferent(SensitivityType.SPEED_LIMIT.getSettingID(), 
                ""+vehicleSettingAdapter.getProperties().get("speed_limit"), 
                vehicleSetting.getCombined(SensitivityType.SPEED_LIMIT.getSettingID()),
                desiredSettings);
        desiredSettings = addIfDifferent(SensitivityType.SPEED_BUFFER.getSettingID(), 
                ""+vehicleSettingAdapter.getProperties().get("speed_buffer"), 
                vehicleSetting.getCombined(SensitivityType.SPEED_BUFFER.getSettingID()),
                desiredSettings);
        desiredSettings = addIfDifferent(SensitivityType.SEVERE_SPEED.getSettingID(), 
                ""+vehicleSettingAdapter.getProperties().get("severe_speed"), 
                vehicleSetting.getCombined(SensitivityType.SEVERE_SPEED.getSettingID()),
                desiredSettings);
        desiredSettings =  getSliderValuesForHardVertical(desiredSettings,vehicleSettingAdapter);
        desiredSettings =  getSliderValuesForHardAcceleration(desiredSettings,vehicleSettingAdapter);
        desiredSettings =  getSliderValuesForHardBrake(desiredSettings,vehicleSettingAdapter);
        desiredSettings =  getSliderValuesForHardTurn(desiredSettings,vehicleSettingAdapter);
        
        return desiredSettings;
    }
    private Map<Integer, String> getSliderValuesForHardVertical(Map<Integer, String> desiredSettings,VehicleSettingAdapter vehicleSettingAdapter){
        
        Map<SensitivityType, String> hardVertical = WaySmartSettingsAndSliders.HARD_VERT_SETTING.getSettingsAndSliders().getSettings((Integer)vehicleSettingAdapter.getProperties().get("hardVertical"));
        desiredSettings = addIfDifferent(SensitivityType.SEVERE_HARDVERT_LEVEL.getSettingID(), 
                                        hardVertical.get(SensitivityType.SEVERE_HARDVERT_LEVEL), 
                                        vehicleSetting.getCombined(SensitivityType.SEVERE_HARDVERT_LEVEL.getSettingID()),
                                        desiredSettings);
        desiredSettings = addIfDifferent(SensitivityType.HARDVERT_DMM_PEAKTOPEAK.getSettingID(), 
                                        hardVertical.get(SensitivityType.HARDVERT_DMM_PEAKTOPEAK), 
                                        vehicleSetting.getCombined(SensitivityType.HARDVERT_DMM_PEAKTOPEAK.getSettingID()),
                                        desiredSettings);
        desiredSettings = addIfDifferent(SensitivityType.RMS_LEVEL.getSettingID(), 
                                        hardVertical.get(SensitivityType.RMS_LEVEL), 
                                        vehicleSetting.getCombined(SensitivityType.RMS_LEVEL.getSettingID()),
                                        desiredSettings);

        return desiredSettings;
    }
    private Map<Integer, String> getSliderValuesForHardTurn(Map<Integer, String> desiredSettings,VehicleSettingAdapter vehicleSettingAdapter){
        
        Map<SensitivityType, String> hardTurn = WaySmartSettingsAndSliders.HARD_TURN_SETTING.getSettingsAndSliders().getSettings((Integer)vehicleSettingAdapter.getProperties().get("hardVertical"));
        desiredSettings = addIfDifferent(SensitivityType.DVY.getSettingID(), 
                hardTurn.get(SensitivityType.DVY), 
                vehicleSetting.getCombined(SensitivityType.DVY.getSettingID()),
                desiredSettings);
        desiredSettings = addIfDifferent(SensitivityType.Y_LEVEL.getSettingID(), 
                hardTurn.get(SensitivityType.Y_LEVEL), 
                vehicleSetting.getCombined(SensitivityType.Y_LEVEL.getSettingID()),
                desiredSettings);

        return desiredSettings;
    }
    private  Map<Integer, String> getSliderValuesForHardAcceleration(Map<Integer, String> desiredSettings,VehicleSettingAdapter vehicleSettingAdapter){
        
        Map<SensitivityType, String> hardAcceleration = WaySmartSettingsAndSliders.HARD_ACCEL_SETTING.getSettingsAndSliders().getSettings((Integer)vehicleSettingAdapter.getProperties().get("hardVertical"));
        desiredSettings = addIfDifferent(SensitivityType.HARD_ACCEL_LEVEL.getSettingID(), 
                hardAcceleration.get(SensitivityType.HARD_ACCEL_LEVEL), 
                vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_LEVEL.getSettingID()),
                desiredSettings);
        desiredSettings = addIfDifferent(SensitivityType.HARD_ACCEL_DELTAV.getSettingID(), 
                hardAcceleration.get(SensitivityType.HARD_ACCEL_DELTAV), 
                vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_DELTAV.getSettingID()),
                desiredSettings);

        return desiredSettings;
    }
    private  Map<Integer, String> getSliderValuesForHardBrake(Map<Integer, String> desiredSettings,VehicleSettingAdapter vehicleSettingAdapter){
        
        Map<SensitivityType, String> hardBrake = WaySmartSettingsAndSliders.HARD_TURN_SETTING.getSettingsAndSliders().getSettings((Integer)vehicleSettingAdapter.getProperties().get("hardVertical"));
        desiredSettings = addIfDifferent(SensitivityType.X_ACCEL.getSettingID(), 
                hardBrake.get(SensitivityType.X_ACCEL), 
                vehicleSetting.getCombined(SensitivityType.X_ACCEL.getSettingID()),
                desiredSettings);
        desiredSettings = addIfDifferent(SensitivityType.DVX.getSettingID(), 
                hardBrake.get(SensitivityType.DVX), 
                vehicleSetting.getCombined(SensitivityType.DVX.getSettingID()),
                desiredSettings);

        return desiredSettings;
    }
    private boolean isRequested(Boolean batchEdit, Map<String, Boolean> updateField, SensitivityType setting){
        
        if (!batchEdit) return true;
        if (updateField.get("vehicleSettingAdapter."+setting.getPropertyName())){
            return true;
        }
        return false;
    }
    private VehicleSetting createVehicleSetting(Integer vehicleID){
        
        VehicleSetting vehicleSetting = new VehicleSetting();
        vehicleSetting.setVehicleID(vehicleID);
        vehicleSetting.setDesired(new HashMap<Integer, String>());
        vehicleSetting.setProductType(ProductType.WS820);

        return vehicleSetting;
    }

    private Map<Integer, String> addIfDifferent(Integer settingID, String newValue, String oldValue, Map<Integer, String> changedSettings){
        
        if(newValue == null) return changedSettings;
        if((oldValue == null) || (!oldValue.equals(newValue))){
            
            changedSettings.put(settingID, newValue);
        }
        return changedSettings;
        
    }

   @Override
   public void setVehicleSettings(Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter, Integer userID, String reason){
       
       Map<Integer, String> setMap = evaluateSettings(vehicleID,vehicleSettingAdapter);
       configuratorDAO.setVehicleSettings(vehicleID, setMap, userID, reason);
   }
   @Override
   public void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField,Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter, Integer userID, String reason){
      
       Map<Integer, String> setMap = evaluateChangedSettings(batchEdit, updateField, vehicleID,vehicleSettingAdapter);
       configuratorDAO.updateVehicleSettings(vehicleID, setMap, userID, reason);
   }
    public class Properties extends HashMap<String,Object>{
        
        private static final long serialVersionUID = 1L;

    }

}
