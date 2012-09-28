package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriverReportItem extends BaseEntity implements Comparable<DriverReportItem>
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6797365101619066450L;
    
    private String groupName;
    private Integer groupID;
    private String driverName;
    private Integer driverID;
    private String vehicleName;
    private Integer vehicleID;
    private String employeeID;
    private Number milesDriven;
    private Integer overallScore;
    private Integer speedScore;
    private Integer styleScore;
    private Integer seatbeltScore;
    private Status status;
    
    public Integer getSeatbeltScore() {
		return seatbeltScore;
	}
	public void setSeatbeltScore(Integer seatbeltScore) {
		this.seatbeltScore = seatbeltScore;
	}
    public String getEmployeeID()
    {
        return employeeID;
    }
    public void setEmployeeID(String employeeID)
    {
        this.employeeID = employeeID;
    }
    public Number getMilesDriven()
    {
        return milesDriven;
    }
    public void setMilesDriven(Number milesDriven)
    {
        this.milesDriven = milesDriven;
    }
    public Integer getOverallScore()
    {
        return overallScore;
    }
    public void setOverallScore(Integer overallScore)
    {
        this.overallScore = overallScore;
    }
    public Integer getSpeedScore()
    {
        return speedScore;
    }
    public void setSpeedScore(Integer speedScore)
    {
        this.speedScore = speedScore;
    }
    public Integer getStyleScore()
    {
        return styleScore;
    }
    public void setStyleScore(Integer styleScore)
    {
        this.styleScore = styleScore;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
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

    @Override
    public int compareTo(DriverReportItem o)
    {
    	if (driverName != null && o.driverName != null)
            return getDriverName().toLowerCase().compareTo(o.getDriverName().toLowerCase());
    	return 0;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
