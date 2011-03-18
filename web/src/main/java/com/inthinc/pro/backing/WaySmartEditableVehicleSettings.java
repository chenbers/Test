package com.inthinc.pro.backing;

import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.WirelineStatus;
import com.inthinc.pro.model.configurator.ProductType;

public class WaySmartEditableVehicleSettings extends EditableVehicleSettings {
	
	private Double speedLimit;
	private Double speedBuffer;
	private Double severeSpeed;
	private Integer hardAcceleration;
	private Integer hardBrake;
	private Integer hardTurn;
    private Integer hardVertical;
    
    
    private WirelineStatus wirelineModule;
    private WirelineStatus  doorAlarm;
    private WirelineStatus  killMotor;
    private String  doorAlarmPasscode;
    private String  killMotorPasscode;
    private Integer autoArmTime;

    private MeasurementType measurementType;



    public WaySmartEditableVehicleSettings() {
        super();
    }


    public WaySmartEditableVehicleSettings(	int vehicleID, Double speedLimit, Double speedBuffer, Double severeSpeed,
                                  			Integer hardAcceleration, Integer hardBrake, Integer hardTurn,
                                  			Integer hardVertical, MeasurementType measurementType,
                                  			WirelineStatus wirelineModule, 
                                  			WirelineStatus doorAlarm, String doorAlarmPasscode, 
                                  			WirelineStatus killMotor, String killMotorPasscode, Integer autoArmTime) {
        
        super(vehicleID,ProductType.WAYSMART);
        
        this.speedLimit = speedLimit;
        this.speedBuffer = speedBuffer;
        this.severeSpeed = severeSpeed;
        setHardAcceleration(hardAcceleration);
        setHardBrake(hardBrake);
        setHardTurn(hardTurn);
        setHardVertical(hardVertical);
        this.measurementType = measurementType;
        this.wirelineModule = wirelineModule;
        this.doorAlarm = doorAlarm;
        this.doorAlarmPasscode = doorAlarmPasscode; 
        this.killMotor = killMotor;
        this.killMotorPasscode = killMotorPasscode;
        this.autoArmTime = autoArmTime;
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
        //Need to convert here for the validation to work correctly on the number slider
        Integer speedLimitInteger = convertMPHtoKPH((speedLimit == null) ? 0 : speedLimit.intValue());
        return speedLimitInteger;
    }

    public Integer convertMPHtoKPH(Integer mph) throws ConverterException
    {
            if (getMeasurementType().equals(MeasurementType.METRIC))
                return (Integer) MeasurementConversionUtil.fromMPHtoKPH(mph);
            else
                return mph;
    }    

    public void setSpeedLimitInteger(Integer speedLimit) {
        Integer speedLimitInteger = convertKPHtoMPH(speedLimit);
        this.speedLimit = new Double(speedLimitInteger);
    }
    public Integer convertKPHtoMPH(Integer kph) throws ConverterException
    {
            if (getMeasurementType().equals(MeasurementType.METRIC))
                return (Integer) MeasurementConversionUtil.fromKPHtoMPH(kph);
            else
                return kph;
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

    public WirelineStatus getWirelineModule() {
        return wirelineModule;
    }
    public void setWirelineModule(WirelineStatus wirelineModule) {
        this.wirelineModule = wirelineModule;
    }
    public String getDoorAlarmPasscode() {
        return (getDoorAlarm() == WirelineStatus.DISABLE) ? "" : doorAlarmPasscode;
    }
    public void setDoorAlarmPasscode(String doorAlarmPasscode) {
        this.doorAlarmPasscode = doorAlarmPasscode;
    }
    public String getKillMotorPasscode() {
        return (getKillMotor() == WirelineStatus.DISABLE) ? "" : killMotorPasscode;
    }
    public void setKillMotorPasscode(String killMotorPasscode) {
        this.killMotorPasscode = killMotorPasscode;
    }
    
    public WirelineStatus getDoorAlarm() {
        if (doorAlarm == null)
            return WirelineStatus.DISABLE;
        return doorAlarm;
    }
    public void setDoorAlarm(WirelineStatus doorAlarm) {
        this.doorAlarm = doorAlarm;
    }
    public WirelineStatus getKillMotor() {
        if (killMotor == null)
            killMotor = WirelineStatus.DISABLE;
        return killMotor;
    }
    public void setKillMotor(WirelineStatus killMotor) {
        this.killMotor = killMotor;
    }

    public Integer getAutoArmTime() {
        return autoArmTime;
    }


    public void setAutoArmTime(Integer autoArmTime) {
        this.autoArmTime = autoArmTime;
    }

    public MeasurementType getMeasurementType() {
        if (measurementType == null)
            return MeasurementType.ENGLISH;
        return measurementType;
    }


    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }



}
