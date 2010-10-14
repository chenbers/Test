package com.inthinc.pro.backing;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;

public class WaySmartEditableVehicleSettings extends EditableVehicleSettings {
	
	private Integer speedLimit;
	private Integer speedBuffer;
	private Integer severeSpeed;
	private Integer hardAcceleration;
	private Integer hardBrake;
	private Integer hardTurn;
    private Integer hardVertical;

    public WaySmartEditableVehicleSettings() {
        super();
    }


    public WaySmartEditableVehicleSettings(	int vehicleID, Integer speedLimit, Integer speedBuffer, Integer severeSpeed,
                                  			Integer hardAcceleration, Integer hardBrake, Integer hardTurn,
                                  			Integer hardVertical) {
        
        super(vehicleID,ProductType.WS820);
        
        this.speedLimit = speedLimit;
        this.speedBuffer = speedBuffer;
        this.severeSpeed = severeSpeed;
        setHardAcceleration(hardAcceleration);
        setHardBrake(hardBrake);
        setHardTurn(hardTurn);
        setHardVertical(hardVertical);

    }

    private void setHardAcceleration(Integer hardAcceleration) {
        
        this.hardAcceleration = hardAcceleration==null?SettingType.WS_HARD_ACCEL_SETTING.getDefaultSetting():
                                hardAcceleration==99?SettingType.WS_HARD_ACCEL_SETTING.getSettingsCount()+1:hardAcceleration;
     }
     private void setHardBrake(Integer hardBrake) {
         
    	 this.hardBrake = hardBrake==null?SettingType.WS_HARD_BRAKE_SETTING.getDefaultSetting():
                          hardBrake==99?SettingType.WS_HARD_BRAKE_SETTING.getSettingsCount()+1:hardBrake;
     }
     private void setHardTurn(Integer hardTurn) {

    	 this.hardTurn = hardTurn==null?SettingType.WS_HARD_TURN_SETTING.getDefaultSetting():
                         hardTurn==99?SettingType.WS_HARD_TURN_SETTING.getSettingsCount()+1:hardTurn;
     }
     private void setHardVertical(Integer hardVertical) {
             
    	 this.hardVertical = hardVertical==null?SettingType.WS_HARD_VERT_SETTING.getDefaultSetting():
                             hardVertical==99?SettingType.WS_HARD_VERT_SETTING.getSettingsCount()+1:hardVertical;
     }

    public Integer getSpeedLimit() {
		return speedLimit;
	}


	public void setSpeedLimit(Integer speedLimit) {
		this.speedLimit = speedLimit;
	}


	public Integer getSpeedBuffer() {
		return speedBuffer;
	}


	public void setSpeedBuffer(Integer speedBuffer) {
		this.speedBuffer = speedBuffer;
	}


	public Integer getSevereSpeed() {
		return severeSpeed;
	}


	public void setSevereSpeed(Integer severeSpeed) {
		this.severeSpeed = severeSpeed;
	}


	public Integer getHardAcceleration() {
        if (hardAcceleration == null)
            return SettingType.WS_HARD_ACCEL_SETTING.getDefaultSetting();
        return hardAcceleration;
	}


	public Integer getHardBrake() {
        if (hardBrake == null)
            return SettingType.WS_HARD_BRAKE_SETTING.getDefaultSetting();
        return hardBrake;
	}


	public Integer getHardTurn() {
        if (hardTurn == null)
            return SettingType.WS_HARD_TURN_SETTING.getDefaultSetting();
        return hardTurn;
	}


	public Integer getHardVertical() {
        if (hardVertical == null)
            return SettingType.WS_HARD_VERT_SETTING.getDefaultSetting();
        return hardVertical;
	}

}
