package com.inthinc.pro.backing;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
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
        //Need to convert here for the validation to work correctly on the number slider
        Integer speedLimitInteger = convertMPHtoKPH(speedLimit.intValue());
        return speedLimitInteger;
    }

    public Integer convertMPHtoKPH(Integer mph) throws ConverterException
    {
            if (getMeasurementType().equals(MeasurementType.METRIC))
                return (Integer) MeasurementConversionUtil.fromMPHtoKPH(mph);
            else
                return mph;
    }    

    public MeasurementType getMeasurementType() {
        if (getUser() != null)
            return getUser().getPerson().getMeasurementType();
        else
            return MeasurementType.ENGLISH;
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
}
