package com.inthinc.pro.model;

public class VehicleReportItem
{
    private String group;
    private Vehicle vehicle;
    private String makeModelYear;
    private Integer milesDriven;
    private Float overallScore;
    private Float speedScore;
    private Float styleScore;
    private String styleOverall;
    private String styleSpeed;
    private String styleStyle;
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

}
