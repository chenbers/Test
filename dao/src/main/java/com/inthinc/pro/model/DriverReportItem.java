package com.inthinc.pro.model;

import java.math.BigDecimal;

public class DriverReportItem extends BaseEntity implements Comparable<DriverReportItem>
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6797365101619066450L;
    
    private static final String NA = "N/A";
    
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
    
    public String getSpeedScoreAsString()
    {
        String returnString = NA;
        if(speedScore != null && speedScore >= 0)
        {
            returnString = new BigDecimal(speedScore).movePointLeft(1).toString();
        }
        
        return returnString;
    }
    
    public String getStyleScoreAsString()
    {
        String returnString = NA;
        if(styleScore != null && styleScore >= 0)
        {
            returnString = new BigDecimal(styleScore).movePointLeft(1).toString();
        }
        
        return returnString;
    }
    
    public String getOverallScoreAsString()
    {
        String returnString = NA;
        
        if(overallScore != null && overallScore >= 0)
        {
            returnString = new BigDecimal(overallScore).movePointLeft(1).toString();
        }
        
        return returnString;
    }
    
    public String getSeatBeltScoreAsString()
    {
        String returnString = NA;
        if(seatBeltScore != null && seatBeltScore >= 0)
        {
            returnString = new BigDecimal(seatBeltScore).movePointLeft(1).toString();
        }
        
        return returnString;
    }
    
    @Override
    public int compareTo(DriverReportItem o)
    {
        return getEmployee().toLowerCase().compareTo(o.getEmployee().toLowerCase());
    }
}
