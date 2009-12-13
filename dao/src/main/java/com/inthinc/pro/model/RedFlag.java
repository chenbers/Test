package com.inthinc.pro.model;

import java.util.TimeZone;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RedFlag extends BaseEntity
{
    @Column(updateable=false)
    private RedFlagLevel level;
    
    @Column(name="sent",updateable=false)
    private Boolean alert;
    
    @Column(name="note",updateable=false)
    private Event event;
    
    @Column(name="tzName",updateable=false)
    private TimeZone timezone;
    
/*    
    // new (for pagination)
    private Zone zone;
	private String driverName;
	private String vehicleName;
	private String group;
    private String detail;
    public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
    public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	// END -- new for pagination
	 * 
*/
    public RedFlag()
    {
        
    }
    public RedFlag(Integer redFlagID, RedFlagLevel level, Boolean alert, Event event, TimeZone timezone)
    {
        this.level = level;
        this.alert = alert;
        this.event = event;
        this.timezone = timezone;
    }
    

    public RedFlagLevel getLevel()
    {
        return level;
    }

    public void setLevel(RedFlagLevel level)
    {
        this.level = level;
    }

    public Boolean getAlert()
    {
        return alert;
    }

    public void setAlert(Boolean alert)
    {
        this.alert = alert;
    }

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }
    public TimeZone getTimezone()
    {
        return timezone;
    }
    public void setTimezone(TimeZone timezone)
    {
        this.timezone = timezone;
    }



}
