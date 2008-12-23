package com.inthinc.pro.model;

public class DriverReportItem extends BaseEntity
{
    private String group;
    private String employeeID;
    private String employee;
    private Vehicle vehicle;
    private Integer milesDriven;
    private Float overallScore;
    private Float speedScore;
    private Float styleScore;
    private Float seatBeltScore;
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
    public Float getOverallScore()
    {
        return overallScore;
    }
    public void setOverallScore(Float overallScore)
    {
        this.overallScore = overallScore;
    }
    public Float getSpeedScore()
    {
        return speedScore;
    }
    public void setSpeedScore(Float speedScore)
    {
        this.speedScore = speedScore;
    }
    public Float getStyleScore()
    {
        return styleScore;
    }
    public void setStyleScore(Float styleScore)
    {
        this.styleScore = styleScore;
    }
    public Float getSeatBeltScore()
    {
        return seatBeltScore;
    }
    public void setSeatBeltScore(Float seatBeltScore)
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
}
