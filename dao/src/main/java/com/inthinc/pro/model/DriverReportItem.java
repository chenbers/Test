package com.inthinc.pro.model;

public class DriverReportItem extends BaseEntity implements Comparable<DriverReportItem>
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6797365101619066450L;
    
    private String group;
    private String employeeID;
    private String employee;
    private Vehicle vehicle;
    private Integer milesDriven;
    private Integer overallScore;
    private Integer speedScore;
    private Integer styleScore;
    private Integer seatBeltScore;
    private String styleOverall;
    private String styleSpeed;
    private String styleStyle;
    private String styleSeatBelt;
    private Driver driver;   
    private Integer groupID;
    private String goTo;
    
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
    public Integer getMilesDriven()
    {
        return milesDriven;
    }
    public void setMilesDriven(Integer milesDriven)
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
    public String getGoTo()
    {
        return goTo;
    }
    public void setGoTo(String goTo)
    {
        this.goTo = goTo;
    }
    
    @Override
    public int compareTo(DriverReportItem o)
    {
        return getEmployee().toLowerCase().compareTo(o.getEmployee().toLowerCase());
    }
}
