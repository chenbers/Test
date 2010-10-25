package com.inthinc.pro.backing;

import com.inthinc.pro.model.configurator.ProductType;

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

	public WaySmartEditableVehicleSettings getSelf(){
	    return this;
	}
}
