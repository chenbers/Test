package com.inthinc.pro.model;

public class IdlingReportItem extends BaseEntity
{
    private String group;
    private Integer groupID;
    private Driver driver;
    private Vehicle vehicle;
    private String driveTime;
    private Integer milesDriven;
    private String lowHrs;
    private Integer lowPercent;
    private String highHrs;
    private Integer highPercent;
    private String totalHrs;
    private Integer totalPercent;

    private Integer lowPerSort;
    private Integer highPerSort;
    private Integer totalPerSort;
    private Integer driveTimeSort;
    
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
    public String getDriveTime()
    {
        return driveTime;
    }
    public void setDriveTime(String driveTime)
    {
        this.driveTime = driveTime;
    }
    public Integer getMilesDriven()
    {
        return milesDriven;
    }
    public void setMilesDriven(Integer milesDriven)
    {
        this.milesDriven = milesDriven;
    }
    public String getLowHrs()
    {
        return lowHrs;
    }
    public void setLowHrs(String lowHrs)
    {
        this.lowHrs = lowHrs;
    }
    public Integer getLowPercent()
    {
        return lowPercent;
    }
    public void setLowPercent(Integer lowPercent)
    {
        this.lowPercent = lowPercent;
    }
    public String getHighHrs()
    {
        return highHrs;
    }
    public void setHighHrs(String highHrs)
    {
        this.highHrs = highHrs;
    }
    public Integer getHighPercent()
    {
        return highPercent;
    }
    public void setHighPercent(Integer highPercent)
    {
        this.highPercent = highPercent;
    }
    public String getTotalHrs()
    {
        return totalHrs;
    }
    public void setTotalHrs(String totalHrs)
    {
        this.totalHrs = totalHrs;
    }
    public Integer getTotalPercent()
    {
        return totalPercent;
    }
    public void setTotalPercent(Integer totalPercent)
    {
        this.totalPercent = totalPercent;
    }
    public Integer getLowPerSort()
    {
        return lowPerSort;
    }
    public void setLowPerSort(Integer lowPerSort)
    {
        this.lowPerSort = lowPerSort;
    }
    public Integer getHighPerSort()
    {
        return highPerSort;
    }
    public void setHighPerSort(Integer highPerSort)
    {
        this.highPerSort = highPerSort;
    }
    public Integer getTotalPerSort()
    {
        return totalPerSort;
    }
    public void setTotalPerSort(Integer totalPerSort)
    {
        this.totalPerSort = totalPerSort;
    }
    public Integer getDriveTimeSort()
    {
        return driveTimeSort;
    }
    public void setDriveTimeSort(Integer driveTimeSort)
    {
        this.driveTimeSort = driveTimeSort;
    }
    
}
