package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdlingReportItem extends BaseEntity implements Comparable<IdlingReportItem>
{
   
    private static final long serialVersionUID = 835696282264901782L;
    
    
    // new pagination
    private String groupName;
    private Integer groupID;
    private String driverName;
    private Integer driverID;
    private Number driveTime;		// changed to float from number
    private Number lowIdleTime;
    private Number highIdleTime;
    private Integer hasRPM;
    private String vehicleName;
    private Integer vehicleID;

	public Integer getHasRPM() {
		return hasRPM;
	}
	public void setHasRPM(Integer hasRPM) {
		this.hasRPM = hasRPM;
	}
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
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
    public Number getDriveTime()
    {
        return (driveTime == null) ? 0 : driveTime;
    }
    public void setDriveTime(Number driveTime)
    {
        this.driveTime = driveTime;
    }
    @Override
    public int compareTo(IdlingReportItem item)
    { 
    	if (getDriverName() == null)
    		return -1;
    	if (item.getDriverName() == null)
    		return 1;
        return this.getDriverName().toLowerCase().compareTo(item.getDriverName().toLowerCase());
    }

    
    public Number getLowIdlePercent(){
    	
    	return computePercent(getLowIdleTime(), getDriveTime());
	}
    public Number getHighIdlePercent(){
    	
    	return computePercent(getHighIdleTime(), getDriveTime());
	}
 
    public Number getTotalIdlePercent(){
    	
    	return computePercent(getTotalIdleTime(), getDriveTime());
	}
    public Number getTotalIdleTime() {
    	
    	return lowIdleTime.doubleValue() + highIdleTime.doubleValue();
    }
    
    private Number computePercent(Number fraction, Number total) {

    	if (total == null || total.longValue() == 0)
    		return 0;
    	return 100.0f*((fraction == null) ? 0 : fraction.floatValue())/total.floatValue(); 
    	
    }
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public Integer getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}
    

}
