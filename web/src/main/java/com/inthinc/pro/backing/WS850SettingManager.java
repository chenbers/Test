package com.inthinc.pro.backing;

import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.VehicleDOTType;
import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class WS850SettingManager extends VehicleSettingManager {
    public final int NUM_SPEEDS = 15;
    public final Integer[] DEFAULT_SPEED_SETTING = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};    
//    public final Integer[] DEFAULT_SPEED_SETTING = {5, 10, 15, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};    
    public final String DEFAULT_SPEED_SET = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
    public final Integer[] SPEED_LIMITS = {5,10,15,20,25,30,35,40,45,50,55,60,65,70,75};
    public final String[] SPEED_FIELDS = {"speed0","speed1","speed2","speed3","speed4","speed5","speed6","speed7","speed8","speed9","speed10","speed11","speed12","speed13","speed14"};

    private static final Double DEFAULT_MAX_SPEED_LIMIT = 78.0;

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
        Integer[] speedSettings = convertFromSpeedSettings(DEFAULT_SPEED_SET);
        Double maxSpeed = DEFAULT_MAX_SPEED_LIMIT;
        
//        public WS850EditableVehicleSettings(Integer[] speedSettings, Integer hardAcceleration, Integer hardBrake, Integer hardTurn, Integer hardVertical, Integer dotVehicleType) {
        return new WS850EditableVehicleSettings(vehicleID==null?-1:vehicleID, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical,maxSpeed,VehicleDOTType.NON_DOT.getConfiguratorSetting());
    }
    protected EditableVehicleSettings createFromExistingValues(Integer vehicleID, VehicleSetting vs){
        Integer hardVertical = hardVerticalSlider.getSliderValueFromSettings(vs);
        Integer hardTurn = hardTurnSlider.getSliderValueFromSettings(vs);
        Integer hardAcceleration = hardAccelerationSlider.getSliderValueFromSettings(vs);
        Integer hardBrake = hardBrakeSlider.getSliderValueFromSettings(vs);
        Integer[] speedSettings = convertFromSpeedSettings(vs.getBestOption(SettingType.WS850_SPEED_LIMIT.getSettingID())); 
        Double maxSpeed = NumberUtil.convertStringToDouble(vs.getBestOption(SettingType.WS850_SEVERE_SPEED_LIMIT.getSettingID()));
        if (maxSpeed < 1.0)
            maxSpeed = DEFAULT_MAX_SPEED_LIMIT;
        
        adjustCountsForCustomValues(hardAcceleration, hardBrake, hardTurn, hardVertical);
        return new WS850EditableVehicleSettings(vs.getVehicleID(),speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical, maxSpeed,VehicleDOTType.NON_DOT.getConfiguratorSetting());
    }
    private Integer[] convertFromSpeedSettings(String speedSet){
        
        if (speedSet == null) return createSpeedSettings();
        
        Integer[] speedSettingValues= new Integer[NUM_SPEEDS];
        String[] speeds = speedSet.split(" ");
        for (int i = 0; i < speeds.length; i++){
            
            speedSettingValues[i] = NumberUtil.convertString(speeds[i])-SPEED_LIMITS[i];
        }
        return speedSettingValues;
    }
    
    private Integer[] createSpeedSettings(){
        Integer[] speedSettings = new Integer[NUM_SPEEDS];
        for (int i = 0; i < NUM_SPEEDS; i++){
            speedSettings[i] = DEFAULT_SPEED_SETTING[i]; 
        }

        return speedSettings;
    }
    public String getSpeedSettingsString(Integer[] speedSettings){
        
        StringBuilder speedSet = new StringBuilder();
        
        for (int i = 0; i<NUM_SPEEDS-1;i++){
            
           speedSet.append(speedSettings[i]+SPEED_LIMITS[i]);
           speedSet.append(' '); 
        }
        speedSet.append(speedSettings[NUM_SPEEDS-1]+SPEED_LIMITS[NUM_SPEEDS-1]);
        
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
