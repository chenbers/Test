package com.inthinc.pro.model;

import java.text.NumberFormat;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdlingReportItem extends BaseEntity implements Comparable<IdlingReportItem>
{
   
    private static final long serialVersionUID = 835696282264901782L;
    
    private String group;
    private Integer groupID;
    private Driver driver;
    private Vehicle vehicle;
    private Float driveTime;
    private Number milesDriven;
    private Float lowHrs;
    private String lowPercent;
    private Float highHrs;
    private String highPercent;
    private Float totalHrs;
    private String totalPercent;

    private Float lowPerSort;
    private Float highPerSort;
    private Float totalPerSort;
    private Float driveTimeSort;
    
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
    public Float getDriveTime()
    {
        return driveTime;
    }
    public void setDriveTime(Float driveTime)
    {
        this.driveTime = driveTime;
    }
    public Number getMilesDriven()
    {
        return milesDriven;
    }
    public void setMilesDriven(Number milesDriven)
    {
        this.milesDriven = milesDriven;
    }
    public Float getLowHrs()
    {
        return lowHrs;
    }
    public void setLowHrs(Float lowHrs)
    {
        this.lowHrs = lowHrs;
    }
    public String getLowPercent()
    {
        return lowPercent;
    }
    public void setLowPercent(String lowPercent)
    {
        this.lowPercent = lowPercent;
    }
    public Float getHighHrs()
    {
        return highHrs;
    }
    public void setHighHrs(Float highHrs)
    {
        this.highHrs = highHrs;
    }
    public String getHighPercent()
    {
        return highPercent;
    }
    public void setHighPercent(String highPercent)
    {
        this.highPercent = highPercent;
    }
    public Float getTotalHrs()
    {
        return totalHrs;
    }
    public void setTotalHrs(Float totalHrs)
    {
        this.totalHrs = totalHrs;
    }
    public String getTotalPercent()
    {
        return totalPercent;
    }
    public void setTotalPercent(String totalPercent)
    {
        this.totalPercent = totalPercent;
    }
    public Float getLowPerSort()
    {
        return lowPerSort;
    }
    public void setLowPerSort(Float lowPerSort)
    {
        this.lowPerSort = lowPerSort;
    }
    public Float getHighPerSort()
    {
        return highPerSort;
    }
    public void setHighPerSort(Float highPerSort)
    {
        this.highPerSort = highPerSort;
    }
    public Float getTotalPerSort()
    {
        return totalPerSort;
    }
    public void setTotalPerSort(Float totalPerSort)
    {
        this.totalPerSort = totalPerSort;
    }
    public Float getDriveTimeSort()
    {
        return driveTimeSort;
    }
    public void setDriveTimeSort(Float driveTimeSort)
    {
        this.driveTimeSort = driveTimeSort;
    } 
    
    public String getTotalHrsAsString()
    {
        return floatToString(getTotalHrs());
    }
    
    public String getLowHrsAsString()
    {
        return floatToString(getLowHrs());
    }
    
    public String getHighHrsAsString()
    {
        return floatToString(getHighHrs());
    }
    
    public String getDriveTimeAsString()
    {
        return floatToString(getDriveTime());
    }
    
    private String floatToString(Float flt)
    {
       
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(2);
            format.setMinimumFractionDigits(2);
            return format.format(flt);
    }
    
    @Override
    public int compareTo(IdlingReportItem item)
    { 
        return this.getDriver().getPerson().getFullName().toLowerCase().compareTo(item.getDriver().getPerson().getFullName().toLowerCase());
    }
}
