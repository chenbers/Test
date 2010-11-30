package com.inthinc.pro.backing;

import com.inthinc.pro.model.configurator.ProductType;

public class WaySmartEditableVehicleSettings extends EditableVehicleSettings {
	
	private Double speedLimit;
	private Double speedBuffer;
	private Double severeSpeed;
	private Integer hardAcceleration;
	private Integer hardBrake;
	private Integer hardTurn;
    private Integer hardVertical;

    public WaySmartEditableVehicleSettings() {
        super();
    }


    public WaySmartEditableVehicleSettings(	int vehicleID, Double speedLimit, Double speedBuffer, Double severeSpeed,
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

    public Integer getSpeedLimitInteger() {
        return speedLimit.intValue();
    }


    public void setSpeedLimitInteger(Integer speedLimit) {
        this.speedLimit = new Double(speedLimit);
    }


    public Integer getSpeedBufferInteger() {
        return speedBuffer.intValue();
    }


    public void setSpeedBufferInteger(Integer speedBuffer) {
        this.speedBuffer = new Double(speedBuffer);
    }


    public Integer getSevereSpeedInteger() {
        return severeSpeed.intValue();
    }


    public void setSevereSpeedInteger(Integer severeSpeed) {
        this.severeSpeed = new Double(severeSpeed);
    }
	public WaySmartEditableVehicleSettings getSelf(){
	    return this;
	}
}
