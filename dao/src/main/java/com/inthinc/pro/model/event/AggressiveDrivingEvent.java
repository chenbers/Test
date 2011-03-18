package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

@XmlRootElement
public class AggressiveDrivingEvent extends Event implements MultipleEventTypes
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer deltaX; // deltas store as Integer, divide by 10 for float value
    private Integer deltaY;
    private Integer deltaZ;
    private Integer severity; // This number represents the severity of the event 
                        // in the range of 1 to 5, 5 being the most extreme.
                        // This may need to be changed.
    private Integer speedLimit;

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(AggressiveDrivingEvent.class);

    public AggressiveDrivingEvent()
    {
        super();
    }

    public AggressiveDrivingEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude, Integer avgSpeed, Integer deltaX, Integer deltaY,
            Integer deltaZ, Integer severity)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.severity = severity;
    }

    public EventType getEventType()
    {
        if (deltaX != null && deltaY != null && deltaZ != null)
        {
            return deriveEventType();
        }
        
        return EventType.UNKNOWN;
    }
    
    private EventType deriveEventType(){
        
        // For tiwipro the unused deltas == 0
        // For waySmart the unused deltas == 5 or -5
        
        int fives = 0;
        
        fives += Math.abs(deltaX)==5?1:0;
        fives += Math.abs(deltaY)==5?1:0;
        fives += Math.abs(deltaZ)==5?1:0;
        
        if (fives == 2){
            //convert 5s to zeros
            deltaX = Math.abs(deltaX)==5?0:deltaX;
            deltaY = Math.abs(deltaY)==5?0:deltaY;
            deltaZ = Math.abs(deltaZ)==5?0:deltaZ;
        }
        
        if ( deltaX < 0)  return EventType.HARD_BRAKE;
        if ( deltaX > 0)  return EventType.HARD_ACCEL;
        if ( deltaY != 0) return EventType.HARD_TURN;
        if ( deltaZ != 0) return EventType.HARD_VERT;
        
        return EventType.UNKNOWN;
    }
    public Integer getSeverity()
    {
        return severity;
    }

    public void setSeverity(Integer severity)
    {
        this.severity = severity;
    }
    
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType,String... mphString)
    {
        Integer speed = 0;
        
        if(this.getSpeed() != null)
            speed = this.getSpeed();
        
        if(measurementType.equals(MeasurementType.METRIC))
            speed = MeasurementConversionUtil.fromMPHtoKPH(speed).intValue();
      
        return MessageFormat.format(formatStr, new Object[] {speed, mphString[0]});
    }

    public Integer getDeltaX()
    {
        return deltaX;
    }

    public void setDeltaX(Integer deltaX)
    {
        this.deltaX = deltaX;
    }

    public Integer getDeltaY()
    {
        return deltaY;
    }

    public void setDeltaY(Integer deltaY)
    {
        this.deltaY = deltaY;
    }

    public Integer getDeltaZ()
    {
        return deltaZ;
    }

    public void setDeltaZ(Integer deltaZ)
    {
        this.deltaZ = deltaZ;
    }

    public Integer getSpeedLimit()
    {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit)
    {
        this.speedLimit = speedLimit;
    }

    @Override
    public Set<EventType> getEventTypes() {
        return EnumSet.of(EventType.HARD_ACCEL, EventType.HARD_BRAKE, EventType.HARD_TURN, EventType.HARD_VERT);
    }

}
