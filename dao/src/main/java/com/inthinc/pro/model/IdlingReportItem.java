package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdlingReportItem extends BaseEntity implements Comparable<IdlingReportItem>
{
   
    private static final long serialVersionUID = 835696282264901782L;
    
    private String group;
    private Integer groupID;
    private Driver driver;
    private Vehicle vehicle;
    private Float lowHrs;
    private Float highHrs;
    private Float totalHrs;

    private Float lowPerSort;
    private Float highPerSort;
    private Float totalPerSort;
    private Float driveTimeSort;
    
    
    // new pagination
    private String groupName;
    private String driverName;
    private Integer driverID;
    private Number driveTime;		// changed to float from number
    private Number lowIdleTime;
    private Number highIdleTime;
    
    public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Integer getDriverID() {
		return driverID;
	}
	public void setDriverID(Integer driverID) {
		this.driverID = driverID;
	}
	public Number getLowIdleTime() {
		return lowIdleTime;
	}
	public void setLowIdleTime(Number lowIdleTime) {
		this.lowIdleTime = lowIdleTime;
	}
	public Number getHighIdleTime() {
		return highIdleTime;
	}
	public void setHighIdleTime(Number highIdleTime) {
		this.highIdleTime = highIdleTime;
	}
	public String getGroup()
    {
        return group;
    }
    public void setGroup(String group)
    {
        this.group = group;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    public Driver getDriver()
    {
        return driver;
    }
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }
    public Number getDriveTime()
    {
        return (driveTime == null) ? 0 : driveTime;
    }
    public void setDriveTime(Number driveTime)
    {
        this.driveTime = driveTime;
    }
    public Float getLowHrs()
    {
        return (lowHrs == null) ? 0f : lowHrs;
    }
    public void setLowHrs(Float lowHrs)
    {
        this.lowHrs = lowHrs;
    }
    public Float getHighHrs()
    {
        return (highHrs == null) ? 0f : highHrs;
    }
    public void setHighHrs(Float highHrs)
    {
        this.highHrs = highHrs;
    }
    public Float getTotalHrs()
    {
        return totalHrs;
    }
    public void setTotalHrs(Float totalHrs)
    {
        this.totalHrs = totalHrs;
    }
    public Float getLowPerSort()
    {
        return lowPerSort;
    }
    public void setLowPerSort(Float lowPerSort)
    {
        this.lowPerSort = lowPerSort;
    }
    public Float getHighPerSort()
    {
        return highPerSort;
    }
    public void setHighPerSort(Float highPerSort)
    {
        this.highPerSort = highPerSort;
    }
    public Float getTotalPerSort()
    {
        return totalPerSort;
    }
    public void setTotalPerSort(Float totalPerSort)
    {
        this.totalPerSort = totalPerSort;
    }
    public Float getDriveTimeSort()
    {
        return driveTimeSort;
    }
    public void setDriveTimeSort(Float driveTimeSort)
    {
        this.driveTimeSort = driveTimeSort;
    } 
    
    
    public Float getLowPercent(){
    	
        Float totHrs = getTotalHours();
        
	    if ( totHrs != null && totHrs.floatValue() != 0.0f ) {
	    	
	        return 100.0f*getLowHrs()/totHrs; 
	    } 
	    return 0f;
	}
    public Float getHighPercent(){
    	
        Float totHrs = getTotalHours();
        
	    if ( totHrs != null && totHrs.floatValue() != 0.0f ) {
	    	
	        return 100.0f*getHighHrs()/totHrs; 
	    } 
	    return 0f;
	}
 
    public Float getTotalPercent(){
    	
        Float totHrs = getTotalHours();
        
	    if ( totHrs != null && totHrs.floatValue() != 0.0f ) {
	    	
	        return 100.0f*getTotalHrs()/totHrs; 
	    } 
	    return 0f;
	}
    private Float getTotalHours(){
    	
    	return new Float(getDriveTime().floatValue() + getLowHrs()+getHighHrs());
    }
    @Override
    public int compareTo(IdlingReportItem item)
    { 
    	if (getDriver() == null || getDriver().getPerson() == null || getDriver().getPerson().getFullName() == null)
    		return -1;
    	if (item.getDriver() == null || item.getDriver().getPerson() == null || item.getDriver().getPerson().getFullName() == null)
    		return 1;
        return this.getDriver().getPerson().getFullName().toLowerCase().compareTo(item.getDriver().getPerson().getFullName().toLowerCase());
    }

}
