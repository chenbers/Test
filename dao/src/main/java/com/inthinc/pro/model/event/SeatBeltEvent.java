package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

@XmlRootElement
public class SeatBeltEvent extends Event
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private Integer avgSpeed;
    private Integer topSpeed;
    private Integer distance;

    public SeatBeltEvent()
    {
        super();
    }

    public SeatBeltEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude, Integer avgSpeed,
            Integer topSpeed, Integer distance)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.avgSpeed = avgSpeed;
        this.topSpeed = topSpeed;
        this.distance = distance;

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
        return EventType.SEATBELT;
    }
    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType,String... mString)
    {   
        // distance is x100
        float distance = 0.0f;
        if(this.distance != null) {
            distance = this.distance.floatValue()/100.0f;
        }
        
        if(measurementType.equals(MeasurementType.METRIC))
        {
            distance = MeasurementConversionUtil.fromMilesToKilometers(distance).floatValue();
        }
        
        return MessageFormat.format(
                formatStr, 
                new Float(distance),
                mString[1]
               );        
    }        

}
