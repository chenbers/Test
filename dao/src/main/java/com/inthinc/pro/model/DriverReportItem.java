package com.inthinc.pro.model;

public class DriverReportItem extends BaseEntity
{
    private String group;
    private Integer employeeID;
    private String employee;
    private String vehicleID;
    private Integer milesDriven;
    private Integer overallScore;
    private Integer speedScore;
    private Integer styleScore;
    private Integer seatBeltScore;
    private String styleOverall;
    private String styleSpeed;
    private String styleStyle;
    private String styleSeatBelt;
    
    public String getGroup()
    {
        return group;
    }
    public void setGroup(String group)
    {
        this.group = group;
    }
    public Integer getEmployeeID()
    {
        return employeeID;
    }
    public void setEmployeeID(Integer employeeID)
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
    public String getVehicleID()
    {
        return vehicleID;
    }
    public void setVehicleID(String vehicleID)
    {
        this.vehicleID = vehicleID;
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

}
