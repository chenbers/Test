package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;

public class DriverLocation
{
    private Integer driverID;        
    private Integer groupID;             // driver's groupID
    private Integer vehicleID;
    
    @Column(name = "vType")
    private VehicleType vehicleType;     // 0=light, 1-medium, 2-heavy  - TODO: This field is not being returned from the backend
    private String name;                 // person.first " " person.last
    private String priPhone;             // person.priPhone
    private String secPhone;             // person.secPhone
    private LatLng loc;                  // last location of driver
    private Date time;
    private String addressStr;
   
    public Integer getDriverID()
    {
        return driverID;
    }
    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }
    public Integer getVehicleID()
    {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }
    public VehicleType getVehicleType()
    {
        return vehicleType;
    }
    
    public void setVehicleType(VehicleType type)
    {
        vehicleType = type;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getPriPhone()
    {
        return priPhone;
    }
    public void setPriPhone(String homePhone)
    {
        this.priPhone = homePhone;
    }
    public String getSecPhone()
    {
        return secPhone;
    }
    public void setSecPhone(String workPhone)
    {
        this.secPhone = workPhone;
    }
    public LatLng getLoc()
    {
        return loc;
    }
    public void setLoc(LatLng loc)
    {
        this.loc = loc;
    }
    public Date getTime()
    {
        return time;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }
    public String getAddressStr()
    {
        return addressStr;
    }
    public void setAddressStr(String addressStr)
    {
        this.addressStr = addressStr;
    }
 
}
