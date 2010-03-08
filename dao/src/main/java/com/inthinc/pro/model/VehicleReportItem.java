package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;

@XmlRootElement
public class VehicleReportItem extends BaseEntity implements Comparable<VehicleReportItem>
{
    /**
     * 
     */
    private static final long serialVersionUID = -199959826868545534L;

    // not sure
    private String styleOverall;
    private String styleSpeed;
    private String styleStyle;
    private String goTo;

    // not needed (replaced in pagination)
    private String group;
	private Vehicle vehicle;
	private Driver driver;
    private String makeModelYear;

    private Integer groupID;
    private Integer overallScore;
    private Integer speedScore;
    private Integer styleScore;
    private Number milesDriven;
    private Number odometer;

    
    // new with pagination
    private String groupName;
    private String driverName;
    private Integer driverID;
    private String vehicleName;
    private Integer vehicleID;
    private String vehicleYMM;
    
    public String getGroup()
    {
        return group;
    }
    public void setGroup(String group)
    {
        this.group = group;
    }
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }
    public String getMakeModelYear()
    {
        return makeModelYear;
    }
    public void setMakeModelYear(String makeModelYear)
    {
        this.makeModelYear = makeModelYear;
    }
    public Driver getDriver()
    {
        return driver;
    }
    public void setDriver(Driver driver)
    {
        this.driver = driver;
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
    public String getStyleOverall()
    {
        return styleOverall;
    }
    public void setStyleOverall(String styleOverall)
    {
        this.styleOverall = styleOverall;
    }
    public String getStyleSpeed()
    {
        return styleSpeed;
    }
    public void setStyleSpeed(String styleSpeed)
    {
        this.styleSpeed = styleSpeed;
    }
    public String getStyleStyle()
    {
        return styleStyle;
    }
    public void setStyleStyle(String styleStyle)
    {
        this.styleStyle = styleStyle;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    public String getGoTo()
    {
        return goTo;
    }
    public void setGoTo(String goTo)
    {
        this.goTo = goTo;
    }
            
    public Number getOdometer() {
        return odometer;
    }
    public void setOdometer(Number odometer) {
        this.odometer = odometer;
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
    public String getVehicleYMM() {
		return vehicleYMM;
	}
	public void setVehicleYMM(String vehicleYMM) {
		this.vehicleYMM = vehicleYMM;
	}
    @Override
    public int compareTo(VehicleReportItem item)
    {
    	if (vehicleName != null && item.getVehicleName() != null)
            return vehicleName.toLowerCase().compareTo(item.getVehicleName().toLowerCase());
        return this.getVehicle().getName().toLowerCase().compareTo(item.getVehicle().getName().toLowerCase());
    }

}
