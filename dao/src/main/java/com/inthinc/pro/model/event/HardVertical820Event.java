package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class HardVertical820Event extends Event 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @EventAttrID(name="SEVERITY")
    private Integer severity; 

    public HardVertical820Event()
    {
        super();
    }

    public HardVertical820Event(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed,
            Integer odometer, Double latitude, Double longitude)
    {
        super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
    }

    
    public EventType getEventType()
    {
        return EventType.HARD_VERT;
        
    }

    public Integer getSeverity()
    {
        if (severity == null)
            return 1;
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


}

