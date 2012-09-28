package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpeedingEvent extends Event
{
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @EventAttrID(name="TOP_SPEED")
    private Integer topSpeed;
    @EventAttrID(name="AVG_SPEED")
    private Integer avgSpeed;
    @EventAttrID(name="DISTANCE")
    private Integer distance;
    @EventAttrID(name="AVG_RPM")
    private Integer avgRPM;
   
    private static EventAttr[] eventAttrList = {
        EventAttr.TOP_SPEED,
        EventAttr.DISTANCE,
        EventAttr.MAX_RPM,
        EventAttr.SPEED_LIMIT,
        EventAttr.AVG_SPEED,
        EventAttr.AVG_RPM
    };
    
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }


    public SpeedingEvent()
    {
        super();
    }

    public SpeedingEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, Integer topSpeed,
            Integer avgSpeed, Integer speedLimit, Integer distance, Integer avgRPM)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.topSpeed = topSpeed;
        this.avgSpeed = avgSpeed;
        setSpeedLimit(speedLimit);
        this.distance = distance;
        this.avgRPM = avgRPM;
    }

    public Integer getAvgSpeed()
    {
        return avgSpeed;
    }

    public void setAvgSpeed(Integer avgSpeed)
    {
        this.avgSpeed = avgSpeed;
    }

    public Integer getDistance()
    {
        return distance;
    }

    public void setDistance(Integer distance)
    {
        this.distance = distance;
    }


    public Integer getTopSpeed()
    {
        return topSpeed;
    }

    public void setTopSpeed(Integer topSpeed)
    {
        this.topSpeed = topSpeed;
    }

    public EventType getEventType()
    {
        return EventType.SPEEDING;
    }

    public Integer getAvgRPM()
    {
        return avgRPM;
    }

    public void setAvgRPM(Integer avgRPM)
    {
        this.avgRPM = avgRPM;
    }
    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType,String... mString)
    {
        Integer topSpeed = 0;
        if(this.topSpeed != null)
         topSpeed =  this.topSpeed;
        
        Integer speedLimit = 0;
        if(getSpeedLimit() != null)
            speedLimit = getSpeedLimit();
        
        // distance is x100
        float distance = 0.0f;
        if(this.distance != null) {
            distance = this.distance.floatValue()/100.0f;
        }
        
        if(measurementType.equals(MeasurementType.METRIC))
        {
            topSpeed = MeasurementConversionUtil.fromMPHtoKPH(topSpeed).intValue();
            speedLimit = MeasurementConversionUtil.fromMPHtoKPHSpeedLimit(speedLimit).intValue();
            distance = MeasurementConversionUtil.fromMilesToKilometers(distance).floatValue();
        }
        
        return MessageFormat.format(
                formatStr, 
                topSpeed, 
                speedLimit , 
                new Float(distance),
                mString[0],
                mString[1]);        
    }        
    @Override
	public boolean isValidEvent() {
		return (getSpeedLimit() != null) && (getSpeedLimit() != 0) && (topSpeed != null) && (topSpeed != 0);
	}
    
    @Override
    public String toString() {
        return "Speeding Event [speedLimit: " + getSpeedLimit() + " topSpeed=" + topSpeed + ", avgSpeed=" + avgSpeed + "]" + super.toString();
    }


}
