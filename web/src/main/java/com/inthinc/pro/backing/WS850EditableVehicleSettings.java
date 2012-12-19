package com.inthinc.pro.backing;

import java.util.Map;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.backing.ui.IdlingSetting;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SpeedingConstants;

public class WS850EditableVehicleSettings extends EditableVehicleSettings {


    private Integer idlingSeconds;
    public Integer getIdlingSeconds() {
        return idlingSeconds;
    }
    public void setIdlingSeconds(Integer idlingSeconds) {
        this.idlingSeconds = idlingSeconds;
    }

    private Integer[] speedSettings;
    private Double maxSpeed;
    private Integer hardAcceleration; //SensitivitySlider value
    private Integer hardBrake; //SensitivitySlider value
    private Integer hardTurn; //SensitivitySlider value
    private Integer hardVertical; //SensitivitySlider value

    private Integer dotVehicleType;

    public WS850EditableVehicleSettings(Integer vehicleID,Integer[] speedSettings, Integer hardAcceleration, Integer hardBrake, Integer hardTurn, Integer hardVertical, double maxSpeed,Integer dotVehicleType, Integer idlingThreshold) {
        super(vehicleID, ProductType.WS850, null);
        this.speedSettings = speedSettings;
        this.maxSpeed = maxSpeed;
        this.hardAcceleration = hardAcceleration;
        this.hardBrake = hardBrake;
        this.hardTurn = hardTurn;
        this.hardVertical = hardVertical;
        this.dotVehicleType = dotVehicleType;
        this.idlingSeconds = idlingThreshold;
    }
    public WS850EditableVehicleSettings() {
        super();
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer[] getSpeedSettings() {
        return speedSettings;
    }

    public void setSpeedSettings(Integer[] speedSettings) {
        if (speedSettings == null)
        {
            this.speedSettings = new Integer[SpeedingConstants.INSTANCE.NUM_SPEEDS];
            for (int i = 0; i < SpeedingConstants.INSTANCE.NUM_SPEEDS; i++){
                this.speedSettings[i] = SpeedingConstants.INSTANCE.DEFAULT_SPEED_SETTING[i]; 
            }
        }
        else {
            
            this.speedSettings = speedSettings;
        }
    }

    public Integer getHardAcceleration() {
        return hardAcceleration;
    }

    public void setHardAcceleration(Integer hardAcceleration) {
        this.hardAcceleration = hardAcceleration;
    }

    public Integer getHardBrake() {
        return hardBrake;
    }

    public void setHardBrake(Integer hardBrake) {
        this.hardBrake = hardBrake;
    }

    public Integer getHardTurn() {
        return hardTurn;
    }

    public void setHardTurn(Integer hardTurn) {
        this.hardTurn = hardTurn;
    }

    public Integer getHardVertical() {
        return hardVertical;
    }

    public void setHardVertical(Integer hardVertical) {
        this.hardVertical = hardVertical;
    }

    public Integer getDotVehicleType() {
        return dotVehicleType;
    }

    public void setDotVehicleType(Integer dotVehicleType) {
        this.dotVehicleType = dotVehicleType;
    }

    @Override
    public void dealWithSpecialSettings(VehicleView vehicle, VehicleView batchItem, Map<String, Boolean> updateField, Boolean isBatchEdit) {
        String keyBase = "speed";
        
        WS850EditableVehicleSettings editedSettings = isBatchEdit ? ((WS850EditableVehicleSettings)batchItem.getEditableVehicleSettings()) : ((WS850EditableVehicleSettings)vehicle.getEditableVehicleSettings()); 
        for (int i=0; i< 15;i+=5){
            Boolean isSpeedFieldUpdated = true;
            if (isBatchEdit)
                isSpeedFieldUpdated = updateField.get(keyBase+i);
            if(isSpeedFieldUpdated != null && isSpeedFieldUpdated) {
                Integer setting = editedSettings.getSpeedSettings()[i];
                for (int j = i+1; j < i+5; j++) {
                    editedSettings.getSpeedSettings()[j] = setting;
                    if (isBatchEdit)
                        updateField.put(keyBase+j, Boolean.TRUE);
                }
            }
        }
    
        if (isBatchEdit) {
            for (int i=0; i< 15;i++){
                Boolean isSpeedFieldUpdated = updateField.get(keyBase+i);
                if(isSpeedFieldUpdated != null && isSpeedFieldUpdated){
                    ((WS850EditableVehicleSettings)vehicle.getEditableVehicleSettings()).getSpeedSettings()[i] = 
                        ((WS850EditableVehicleSettings)batchItem.getEditableVehicleSettings()).getSpeedSettings()[i];
                }
            }
        }
    }
    public String getSpeedSettingsString(){
        
        StringBuilder speedSet = new StringBuilder();
        
        for (int i = 0; i<SpeedingConstants.INSTANCE.NUM_SPEEDS-1;i++){
            
           speedSet.append(speedSettings[i]+SpeedingConstants.INSTANCE.SPEED_LIMITS[i]);
           speedSet.append(' '); 
        }
        speedSet.append(speedSettings[SpeedingConstants.INSTANCE.NUM_SPEEDS-1]+SpeedingConstants.INSTANCE.SPEED_LIMITS[SpeedingConstants.INSTANCE.NUM_SPEEDS-1]);
        
        return speedSet.toString();
     }
    public Integer getMaxSpeedLimitInteger() {
        return NumberUtil.intValue(getMaxSpeed());
    }

    public void setMaxSpeedLimitInteger(Integer maxSpeedLimitInteger) {
        setMaxSpeed((double) maxSpeedLimitInteger);
    }
    public Integer getIdlingEvent() {
        return (idlingSeconds == null || idlingSeconds == 0)?0:1;
    }
    public void setIdlingSlider(Integer idlingSlider){
        setIdlingSeconds(IdlingSetting.valueOf((Integer)idlingSlider).getSeconds());
    }
    public Integer getIdlingSlider(){   
        if (idlingSeconds == null) return IdlingSetting.OFF.getSlider();
        return IdlingSetting.findBySeconds(idlingSeconds).getSlider();
    }
    public Integer getIdlingSecondsDefault() {
        return IdlingSetting.getDefault().getSlider();
    }
    public Integer getIdlingSecondsCount() {
        return IdlingSetting.values().length;
    }
    public Integer getIdlingSecondsMin() {
        return IdlingSetting.OFF.getSlider();
    }
    public Integer getIdlingSecondsMax() {
        return IdlingSetting.MAX.getSlider();
    }
}
