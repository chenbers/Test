package com.inthinc.pro.model;

public class DriverLocation
{
    private Integer driverID;        
    private Integer groupID;             // driver's groupID
    private Integer vehicleID;      
    private Integer vType;               // 0=light, 1-medium, 2-heavy
    private String name;                 // person.first " " person.last
    private String homePhone;            // person.homePhone
    private String workPhone;            // person.workPhone
    private LatLng loc;                  // last location of driver
   
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
    public Integer getVType()
    {
        return vType;
    }
    public void setVType(Integer type)
    {
        vType = type;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getHomePhone()
    {
        return homePhone;
    }
    public void setHomePhone(String homePhone)
    {
        this.homePhone = homePhone;
    }
    public String getWorkPhone()
    {
        return workPhone;
    }
    public void setWorkPhone(String workPhone)
    {
        this.workPhone = workPhone;
    }
    public LatLng getLoc()
    {
        return loc;
    }
    public void setLoc(LatLng loc)
    {
        this.loc = loc;
    }
 
}
