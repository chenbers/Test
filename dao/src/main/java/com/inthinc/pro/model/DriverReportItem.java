package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriverReportItem extends BaseEntity implements Comparable<DriverReportItem>
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6797365101619066450L;
        
    

    // not sure if these belong here
    private String styleOverall;
	private String styleSpeed;
    private String styleStyle;
    private String styleSeatBelt;
    
    // not used
    private Vehicle vehicle;
    private String group;
    private Driver driver;   
    private String employee;
    private Integer seatBeltScore;
    
    
    // used with pagination
    private Integer groupID;
    private String employeeID;
    private Number milesDriven;
    private Integer overallScore;
    private Integer speedScore;
    private Integer styleScore;
    
    // new with pagination
    private String groupName;
    private String driverName;
    private Integer driverID;
    private String vehicleName;
    private Integer vehicleID;
    private Integer seatbeltScore;
    
    public Integer getSeatbeltScore() {
		return seatbeltScore;
	}
	public void setSeatbeltScore(Integer seatbeltScore) {
		this.seatbeltScore = seatbeltScore;
	}
	public String getGroup()
    {
        return group;
    }
    public void setGroup(String group)
    {
        this.group = group;
    }
    public String getEmployeeID()
    {
        return employeeID;
    }
    public void setEmployeeID(String employeeID)
    {
        this.employeeID = employeeID;
    }
    public String getEmployee()
    {
        return employee;
    }
    public void setEmployee(String employee)
    {
        this.employee = employee;
    }
    public Vehicle getVehicle()
    {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
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
    public Integer getSeatBeltScore()
    {
        return seatBeltScore;
    }
    public void setSeatBeltScore(Integer seatBeltScore)
    {
        this.seatBeltScore = seatBeltScore;
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
    public String getStyleSeatBelt()
    {
        return styleSeatBelt;
    }
    public void setStyleSeatBelt(String styleSeatBelt)
    {
        this.styleSeatBelt = styleSeatBelt;
    }
    public Driver getDriver()
    {
        return driver;
    }
    public void setDriver(Driver driver)
    {
        this.driver = driver;
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
        return getEmployee().toLowerCase().compareTo(o.getEmployee().toLowerCase());
    }
}
