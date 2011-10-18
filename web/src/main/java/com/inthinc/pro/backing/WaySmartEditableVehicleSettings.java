package com.inthinc.pro.backing;

import com.inthinc.pro.model.configurator.ProductType;

public class WaySmartEditableVehicleSettings extends EditableVehicleSettings {
	
	private Double  speedLimit;
	private Double  speedBuffer;
	private Double  severeSpeed;
	private Integer hardAcceleration;
	private Integer hardBrake;
	private Integer hardTurn;
    private Integer hardVertical;
    
    
    private String  doorAlarmPasscode;
    private String  killMotorPasscode;
    private Integer autoArmTime;
    private Integer dotVehicleType;

    public WaySmartEditableVehicleSettings() {
        super();
    }


    public WaySmartEditableVehicleSettings(	int vehicleID, Double speedLimit, Double speedBuffer, Double severeSpeed,
                                  			Integer hardAcceleration, Integer hardBrake, Integer hardTurn,
                                  			Integer hardVertical,
                                  			String doorAlarmPasscode, 
                                  			String killMotorPasscode, Integer autoArmTime, Integer dotVehicleType) {
        
        super(vehicleID,ProductType.WAYSMART,"");
        
        this.speedLimit = speedLimit;
        this.speedBuffer = speedBuffer;
        this.severeSpeed = severeSpeed;
        setHardAcceleration(hardAcceleration);
        setHardBrake(hardBrake);
        setHardTurn(hardTurn);
        setHardVertical(hardVertical);
        this.doorAlarmPasscode = doorAlarmPasscode; 
        this.killMotorPasscode = killMotorPasscode;
        this.autoArmTime = autoArmTime;
        this.dotVehicleType = dotVehicleType;
    }

    public void setHardAcceleration(Integer hardAcceleration) {
        
        this.hardAcceleration = hardAcceleration;
     }
     public void setHardBrake(Integer hardBrake) {
         
    	 this.hardBrake = hardBrake;
     }
     public void setHardTurn(Integer hardTurn) {

    	 this.hardTurn = hardTurn;
     }
     public void setHardVertical(Integer hardVertical) {
             
    	 this.hardVertical = hardVertical;
     }

    public Double getSpeedLimit() {
		return speedLimit;
	}


	public void setSpeedLimit(Double speedLimit) {
		this.speedLimit = speedLimit;
	}


	public Double getSpeedBuffer() {
		return speedBuffer;
	}


	public void setSpeedBuffer(Double speedBuffer) {
		this.speedBuffer = speedBuffer;
	}


	public Double getSevereSpeed() {
		return severeSpeed;
	}


	public void setSevereSpeed(Double severeSpeed) {
		this.severeSpeed = severeSpeed;
	}


	public Integer getHardAcceleration() {
         return hardAcceleration;
	}


	public Integer getHardBrake() {
        return hardBrake;
	}


	public Integer getHardTurn() {
        return hardTurn;
	}


	public Integer getHardVertical() {
        return hardVertical;
	}

    public Integer getSpeedLimitInteger(){
        if (speedLimit == null) speedLimit = 0.0;
        return speedLimit.intValue();
    }
    public void setSpeedLimitInteger(Integer speedLimitValue){
        speedLimit = new Double(speedLimitValue);
    }

    public Integer getSpeedBufferInteger() {
        if (speedBuffer == null) return null;
        return speedBuffer.intValue();
    }

    public void setSpeedBufferInteger(Integer speedBuffer) {
        this.speedBuffer = new Double(speedBuffer);
    }

    public Integer getSevereSpeedInteger() {
        return (severeSpeed == null) ? 0 : severeSpeed.intValue();
    }

    public void setSevereSpeedInteger(Integer severeSpeed) {
        this.severeSpeed = new Double(severeSpeed);
    }
	public WaySmartEditableVehicleSettings getSelf(){
	    return this;
	}

    public String getDoorAlarmPasscode() {
        return doorAlarmPasscode;
    }
    public void setDoorAlarmPasscode(String doorAlarmPasscode) {
        this.doorAlarmPasscode = doorAlarmPasscode;
    }
    public String getKillMotorPasscode() {
        return killMotorPasscode;
    }
    public void setKillMotorPasscode(String killMotorPasscode) {
        this.killMotorPasscode = killMotorPasscode;
    }
    public Integer getAutoArmTime() {
        return autoArmTime;
    }


    public void setAutoArmTime(Integer autoArmTime) {
        this.autoArmTime = autoArmTime;
    }

    public Integer getDotVehicleType() {
        return dotVehicleType;
    }


    public void setDotVehicleType(Integer dotVehicleType) {
        this.dotVehicleType = dotVehicleType;
    }


}
