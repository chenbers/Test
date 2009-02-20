package com.inthinc.pro.model;

public class VehicleReportItem extends BaseEntity implements Comparable<VehicleReportItem>
{
    /**
     * 
     */
    private static final long serialVersionUID = -199959826868545534L;
    
    
    private String group;
    private Vehicle vehicle;
    private String makeModelYear;
    private Integer milesDriven;
    private Integer overallScore;
    private Integer speedScore;
    private Integer styleScore;
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
    
    @Override
    public int compareTo(VehicleReportItem item)
    {
        return this.getVehicle().getName().toLowerCase().compareTo(item.getVehicle().getName().toLowerCase());
    }

}
