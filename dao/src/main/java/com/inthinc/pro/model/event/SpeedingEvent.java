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
    @EventAttrID(name="SPEED_LIMIT")
    private Integer speedLimit;
    @EventAttrID(name="DISTANCE")
    private Integer distance;
    @EventAttrID(name="AVG_RPM")
    private Integer avgRPM;
    
    @Override
    public EventAttr[] getEventAttrList() {
        EventAttr[] eventAttrList = new EventAttr[6];
        eventAttrList[0] = EventAttr.TOP_SPEED;
        eventAttrList[1] = EventAttr.DISTANCE;
        eventAttrList[2] = EventAttr.MAX_RPM;
        eventAttrList[3] = EventAttr.SPEED_LIMIT;
        eventAttrList[4] = EventAttr.AVG_SPEED;
        eventAttrList[5] = EventAttr.AVG_RPM;
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
        this.speedLimit = speedLimit;
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

    public Integer getSpeedLimit()
    {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit)
    {
        this.speedLimit = speedLimit;
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
        if(this.speedLimit != null)
            speedLimit = this.speedLimit;
        
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
		// TODO Auto-generated method stub
		return (speedLimit != null) && (speedLimit != 0) && (topSpeed != null) && (topSpeed != 0);
	}


}
