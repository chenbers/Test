package com.inthinc.pro.model.event;

import java.text.MessageFormat;
import java.util.Date;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.MeasurementType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdleEvent extends Event
{
    private static final long serialVersionUID = 1L;
    
    /**
     * lowIdle - The duration in seconds the vehicle was idling with RPM < 1000
     * highIdle - The duration in seconds the vehicle was idling with RPM > 1000
     * ptoIdle
     */
    @EventAttrID(name="LOW_IDLE")
    private Integer lowIdle = 0;
    @EventAttrID(name="HIGH_IDLE")
    private Integer highIdle = 0;
    @EventAttrID(name="PTO_IDLE")
    private Integer ptoIdle = 0;
	
    private static EventAttr[] eventAttrList = {
        EventAttr.LOW_IDLE,
        EventAttr.HIGH_IDLE,
        EventAttr.PTO_IDLE
    };

	public IdleEvent()
	{
		super();
	}
	public IdleEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
						Integer lowIdle, Integer highIdle)
	{
		super(noteID, vehicleID, type, time, speed, odometer, latitude, longitude);
		this.lowIdle = lowIdle;
		this.highIdle = highIdle;
		
	}
	public IdleEvent(Long noteID, Integer vehicleID, NoteType type, Date time, Integer speed, Integer odometer, Double latitude, Double longitude,
                    Integer lowIdle, Integer highIdle, Integer ptoIdle)
	{
    this(noteID, vehicleID, type, time, speed, odometer, latitude, longitude, lowIdle, highIdle);
    this.ptoIdle = ptoIdle;    
	}
	
	public String getHighIdleDuration()
	{
        if (getHighIdle() != null && getHighIdle() != 0) {
            return DateUtil.getDurationFromSeconds(getHighIdle());
        } else if (getPtoIdle() != null && getPtoIdle() != 0) {
            return DateUtil.getDurationFromSeconds(getPtoIdle());
        }
        return "";
	}
	
    @Override
    public EventAttr[] getEventAttrList() {
        return eventAttrList;
    }

   public String getLowIdleDuration()
    {
       if(this.lowIdle != null)
           return DateUtil.getDurationFromSeconds(this.lowIdle);
       else
           return "";
    }
	
	public Integer getLowIdle()
    {
        return lowIdle;
    }
    public void setLowIdle(Integer lowIdle)
    {
        this.lowIdle = lowIdle;
    }
    public Integer getHighIdle()
    {
        return highIdle;
    }
    public void setHighIdle(Integer highIdle)
    {
        this.highIdle = highIdle;
    }
    
    public Integer getPtoIdle() {
        return ptoIdle;
    }
    
    public void setPtoIdle(Integer ptoIdle) {
        this.ptoIdle = ptoIdle;
    }
    
    public EventType getEventType()
	{
		return EventType.IDLING;
	}
    
    public Integer getTotalIdling(){
        int totalIdling = 0;

        if (getHighIdle() != null) {
            totalIdling += getHighIdle();
        }
        if (getPtoIdle() != null) {
            totalIdling += getPtoIdle();
        }
        if (getLowIdle() != null) {
            totalIdling += getLowIdle();
        }
        return totalIdling;
    }
    @Override
    public String getDetails(String formatStr,MeasurementType measurementType,String... mphString)
    {
        Integer highIdle = 0;
        if (getHighIdle() != null && getHighIdle() != 0) {
            highIdle = getHighIdle();
        } else if (getPtoIdle() != null && getPtoIdle() != 0) {
            highIdle = getPtoIdle();
        }
        return MessageFormat.format(formatStr, new Object[] {getLowIdle() == null ? 0 : getLowIdle(), highIdle});
    }

}
