package com.inthinc.pro.backing;

import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.backing.ui.IdlingSetting;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.WS850HOSDOTType;
import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.SpeedingConstants;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class WS850SettingManager extends VehicleSettingManager {

    public WS850SettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders, VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,sensitivitySliders,ProductType.WS850, vehicleSetting);
    }
    public WS850SettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders, Integer vehicleID, Integer deviceID) {
        
        super(configuratorDAO,sensitivitySliders,ProductType.WS850,vehicleID,deviceID);
    }
    @Override
    public EditableVehicleSettings createDefaultValues(Integer vehicleID) {
        Integer hardVertical = hardVerticalSlider.getDefaultValueIndex();
        Integer hardTurn = hardTurnSlider.getDefaultValueIndex();
        Integer hardAcceleration =  hardAccelerationSlider.getDefaultValueIndex();
        Integer hardBrake = hardBrakeSlider.getDefaultValueIndex();
        Integer[] speedSettings = convertFromSpeedSettings(SpeedingConstants.INSTANCE.DEFAULT_SPEED_SET);
        Double maxSpeed = SpeedingConstants.INSTANCE.DEFAULT_MAX_SPEED_LIMIT;
        Integer idlingThresholdSeconds = IdlingSetting.DEFAULT.getSeconds();
        
        
        return new WS850EditableVehicleSettings(vehicleID==null?-1:vehicleID, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical,maxSpeed, WS850HOSDOTType.LIGHT_DUTY_NO_HOS.getConfiguratorSetting(), idlingThresholdSeconds);
    }
    protected EditableVehicleSettings createFromExistingValues(Integer vehicleID, VehicleSetting vs){
        Integer hardVertical = hardVerticalSlider.getSliderValueFromSettings(vs);
        Integer hardTurn = hardTurnSlider.getSliderValueFromSettings(vs);
        Integer hardAcceleration = hardAccelerationSlider.getSliderValueFromSettings(vs);
        Integer hardBrake = hardBrakeSlider.getSliderValueFromSettings(vs);
        Integer[] speedSettings = convertFromSpeedSettings(vs.getBestOption(SettingType.WS850_SPEED_LIMIT.getSettingID())); 
        Double maxSpeed = NumberUtil.convertStringToDouble(vs.getBestOption(SettingType.WS850_SEVERE_SPEED_LIMIT.getSettingID()));
        if (maxSpeed < 1.0)
            maxSpeed = SpeedingConstants.INSTANCE.DEFAULT_MAX_SPEED_LIMIT;
        Integer idlingThresholdSeconds = NumberUtil.convertString(vs.getBestOption(SettingType.WS850_IDLING_TIMEOUT.getSettingID()));
        Integer dotVehicleType = NumberUtil.convertString(vs.getBestOption(SettingType.WS850_HOS_SETTING.getSettingID()));
        adjustCountsForCustomValues(hardAcceleration, hardBrake, hardTurn, hardVertical);
        return new WS850EditableVehicleSettings(vs.getVehicleID(),speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical, maxSpeed,dotVehicleType,idlingThresholdSeconds);
    }
    private Integer[] convertFromSpeedSettings(String speedSet){
        
        if (speedSet == null) return createSpeedSettings();
        
        Integer[] speedSettingValues= new Integer[SpeedingConstants.INSTANCE.NUM_SPEEDS];
        String[] speeds = speedSet.split(" ");
        for (int i = 0; i < speeds.length; i++){
            
            speedSettingValues[i] = NumberUtil.convertString(speeds[i])-SpeedingConstants.INSTANCE.SPEED_LIMITS[i];
        }
        return speedSettingValues;
    }
    
    private Integer[] createSpeedSettings(){
        Integer[] speedSettings = new Integer[SpeedingConstants.INSTANCE.NUM_SPEEDS];
        for (int i = 0; i < SpeedingConstants.INSTANCE.NUM_SPEEDS; i++){
            speedSettings[i] = SpeedingConstants.INSTANCE.DEFAULT_SPEED_SETTING[i]; 
        }

        return speedSettings;
    }
    public String getSpeedSettingsString(Integer[] speedSettings){
        
        StringBuilder speedSet = new StringBuilder();
        
        for (int i = 0; i<SpeedingConstants.INSTANCE.NUM_SPEEDS-1;i++){
            
           speedSet.append(speedSettings[i]+SpeedingConstants.INSTANCE.SPEED_LIMITS[i]);
           speedSet.append(' '); 
        }
        speedSet.append(speedSettings[SpeedingConstants.INSTANCE.NUM_SPEEDS-1]+SpeedingConstants.INSTANCE.SPEED_LIMITS[SpeedingConstants.INSTANCE.NUM_SPEEDS-1]);
        
        return speedSet.toString();
     }


    @Override
    public Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Map<String, Boolean> updateField) {
        try{
            WS850EditableVehicleSettings ws850EditableVehicleSettings = downCastEditableVehicleSettings(editableVehicleSettings);
            
            if (vehicleSetting == null) {
                
                vehicleSetting = new VehicleSetting(vehicleID,ProductType.WS850);
            }
            
            DesiredSettings newSettings = new DesiredSettings();
            newSettings.addSliderIfNeeded(SettingType.WS850_HARD_VERT_SETTING,
                                       getHardVerticalValue(ws850EditableVehicleSettings.getHardVertical()).get(SettingType.WS850_HARD_VERT_SETTING.getSettingID()),
                                       vehicleSetting.getBestOption(SettingType.WS850_HARD_VERT_SETTING.getSettingID()), 
                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardVertical"));
            newSettings.addSliderIfNeeded(SettingType.WS850_HARD_TURN_SETTING, 
                                       getHardTurnValue(ws850EditableVehicleSettings.getHardTurn()).get(SettingType.WS850_HARD_TURN_SETTING.getSettingID()), 
                                       vehicleSetting.getBestOption(SettingType.WS850_HARD_TURN_SETTING.getSettingID()), 
                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardTurn"));
            newSettings.addSliderIfNeeded(SettingType.WS850_HARD_ACCEL_SETTING, 
                                       getHardAccelerationValue(ws850EditableVehicleSettings.getHardAcceleration()).get(SettingType.WS850_HARD_ACCEL_SETTING.getSettingID()), 
                                       vehicleSetting.getBestOption(SettingType.WS850_HARD_ACCEL_SETTING.getSettingID()), 
                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardAcceleration"));
            newSettings.addSliderIfNeeded(SettingType.WS850_HARD_BRAKE_SETTING, 
                                       getHardBrakeValue(ws850EditableVehicleSettings.getHardBrake()).get(SettingType.WS850_HARD_BRAKE_SETTING.getSettingID()), 
                                       vehicleSetting.getBestOption(SettingType.WS850_HARD_BRAKE_SETTING.getSettingID()), 
                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardBrake"));
            newSettings.addSettingIfNeeded(SettingType.WS850_SPEED_LIMIT, 
                                        ws850EditableVehicleSettings.getSpeedSettingsString(), 
                                        vehicleSetting.getBestOption(SettingType.WS850_SPEED_LIMIT.getSettingID()), 
                                        updateSpeedSettings(updateField));
            newSettings.addSettingIfNeeded(SettingType.WS850_SEVERE_SPEED_LIMIT, 
                                        ws850EditableVehicleSettings.getMaxSpeed().toString(), 
                                        vehicleSetting.getBestOption(SettingType.WS850_SEVERE_SPEED_LIMIT.getSettingID()), 
                                        fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.maxSpeed"));
            newSettings.addSettingIfNeeded(SettingType.WS850_IDLING_TIMEOUT, 
                    ws850EditableVehicleSettings.getIdlingSeconds().toString(), 
                    vehicleSetting.getBestOption(SettingType.WS850_IDLING_TIMEOUT.getSettingID()), 
                    fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.idlingSeconds"));

            newSettings.addSettingIfNeeded(SettingType.WS850_HOS_SETTING,
                    ""+ws850EditableVehicleSettings.getDotVehicleType(), 
                    vehicleSetting.getBestOption(SettingType.WS850_HOS_SETTING.getSettingID()),
                    fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.dotVehicleType"));
           return newSettings.getDesiredSettings();
        }
        catch(IllegalArgumentException iae){
         
            return null;
        }
    }
    private boolean updateSpeedSettings(Map<String, Boolean> updateField){
        if(updateField == null) return true;
        String keyBase = "speed";
        for (int i=0; i< 15;i++){
            Boolean isSpeedFieldUpdated = updateField.get(keyBase+i);
            if(isSpeedFieldUpdated != null && isSpeedFieldUpdated){

                return true;
            }
        }
        return false;
    }

    @Override
    public void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason,
               Map<String, Boolean> updateField){
        Map<Integer, String> setMap = evaluateSettings(vehicleID,editableVehicleSettings,updateField);
        configuratorDAO.setVehicleSettings(vehicleID, setMap, userID, reason);
    }

    private WS850EditableVehicleSettings downCastEditableVehicleSettings(EditableVehicleSettings editableVehicleSettings) throws IllegalArgumentException{
        
        if (!(editableVehicleSettings instanceof WS850EditableVehicleSettings)){
            
            throw new IllegalArgumentException();
        }
        return(WS850EditableVehicleSettings)editableVehicleSettings;
      }

}
