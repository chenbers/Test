package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

@XmlRootElement
public class Vehicle extends BaseEntity
{
    @Column(updateable = false)
    private static final long serialVersionUID = 6102998742224160619L;

    @ID
    private Integer           vehicleID;

    private Integer           groupID;
    private Status            status;
    private String            name;
    private String            make;
    private String            model;
    private Integer            year;
    private String            color;
    private VehicleType       vtype;
    @Column(name = "vin")
    private String            VIN;                                    // 17 chars
    private Integer           weight;
    private String            license;
    @Column(name = "stateID")
    private State             state;
    @Column(updateable = false)
    private Integer           driverID;
    @Column(updateable = false)
    private Integer           deviceID;
    
    private Integer           odometer;
    
    private Boolean           dot;
    private Boolean           ifta;
    
    public Vehicle()
    {
        super();
    }

    public Boolean getDot() {
        return dot;
    }

    public void setDot(Boolean dot) {
        this.dot = dot;
    }

    public Boolean getIfta() {
        return ifta;
    }

    public void setIfta(Boolean ifta) {
        this.ifta = ifta;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }


    public String getVIN()
    {
        return VIN;
    }

    public void setVIN(String vin)
    {
        VIN = vin;
    }

    public Integer getWeight()
    {
        // Value of 1 is treated as null.
        if(weight != null && weight == 1)
            return null;
        else
            return weight;
    }

    public void setWeight(Integer weight)
    {
        if(weight == null)
            this.weight = 1;
        else
            this.weight = weight;
    }

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public Integer getDriverID()
    {
        return driverID;
    }

    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }

    public Integer getDeviceID()
    {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID)
    {
        this.deviceID = deviceID;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public Vehicle(Integer vehicleID, Integer groupID, Status status, String name, String make, String model, 
            Integer year, String color,
            VehicleType vtype, String vin, Integer weight, String license, State state)
    {
        super();
        this.vehicleID = vehicleID;
        this.groupID = groupID;
        this.status = status;
        this.name = name;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.vtype = vtype;
        VIN = vin;
        this.weight = weight;
        this.license = license;
        this.state = state;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public VehicleType getVtype()
    {
        return vtype;
    }

    public void setVtype(VehicleType vtype)
    {
        this.vtype = vtype;
    }

    public String getFullName()
    {
        if ((this.year == null) && (this.make == null) && (this.model == null))
            return null;
        
        StringBuilder sb = new StringBuilder();
        sb.append(this.year!=null?this.year:"");
        sb.append(" ");
        sb.append(this.make!=null?this.make:"");
        sb.append(" ");
        sb.append(this.model!=null?this.model:"");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Vehicle [VIN=" + VIN + ", color=" + color + ", deviceID=" + deviceID + ", driverID=" + driverID + ", groupID=" + groupID
                + ", license=" + license + ", make=" + make + ", model=" + model + ", name=" + name + ", state=" + state + ", status=" + status + ", vehicleID=" + vehicleID
                + ", vtype=" + vtype + ", weight=" + weight + ", year=" + year + //", hos="+ hos+
                ", dot="+dot+
                ", ifta="+ifta+
               "]";
    }
}
