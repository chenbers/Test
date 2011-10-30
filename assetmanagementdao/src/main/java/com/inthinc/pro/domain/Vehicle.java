package com.inthinc.pro.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.inthinc.pro.configurator.model.Status;
import com.inthinc.pro.configurator.model.VehicleDOTType;
import com.inthinc.pro.configurator.model.VehicleType;

@Entity
@Table(name="vehicle")
public class Vehicle implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
    private Integer             stateID;
    
    private Integer           odometer;
    
//    private VehicleDOTType    dot;
    private Boolean           ifta;
	@Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    private String 			  groupPath;
    private Boolean			  hos;
    private Integer			  zoneType;
	private Integer			  absOdometer;
	
	@Temporal(TemporalType.DATE)
	private Date				warrantyStart;
	@Temporal(TemporalType.DATE)
	private Date				warrantyStop;
	@Temporal(TemporalType.DATE)
	private Date				aggDate;
	@Temporal(TemporalType.DATE)
	private Date				newAggDate;
	
	public Vehicle(){
	}
	public Integer getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}
	public Integer getGroupID(){
		return groupID;
	}
	
	public void setGroupID(Integer groupID){
	    this.groupID = groupID;
	}

    

//    public VehicleDOTType getDot() {
//        return dot;
//    }
//
//    public void setDot(VehicleDOTType dot) {
//        this.dot = dot;
//    }

    public Boolean getIfta() {
        return ifta;
    }

    public void setIfta(Boolean ifta) {
        this.ifta = ifta;
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

//    public State getState()
//    {
//        return state;
//    }
//
//    public void setState(State state)
//    {
//        this.state = state;
//    }

//    public Integer getDriverID()
//    {
//        return driverID;
//    }
//
//    public void setDriverID(Integer driverID)
//    {
//        this.driverID = driverID;
//    }

//    public Integer getDeviceID()
//    {
//        return deviceID;
//    }
//
//    public void setDeviceID(Integer deviceID)
//    {
//        this.deviceID = deviceID;
//    }

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
//        this.state = state;
//        this.dot = dot;
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
    public void setFullName(String fullName){
        //just to keep the Jackson JSON marshaller happy
    }
    @Override
    public String toString() {
        return "Vehicle [VIN=" + VIN + ", color=" + color + ", groupID=" + groupID
                + ", license=" + license + ", make=" + make + ", model=" + model + ", name=" + name + ", stateID=" + stateID + ", status=" + status + ", vehicleID=" + vehicleID
                + ", vtype=" + vtype + ", weight=" + weight + ", year=" + year + //", hos="+ hos+
//                ", dot="+dot+
                ", ifta="+ifta+
               "]";
    }
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getGroupPath() {
		return groupPath;
	}
	public void setGroupPath(String groupPath) {
		this.groupPath = groupPath;
	}
	public Boolean getHos() {
		return hos;
	}
	public void setHos(Boolean hos) {
		this.hos = hos;
	}
	public Integer getZoneType() {
		return zoneType;
	}
	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
	}
	public Integer getAbsOdometer() {
		return absOdometer;
	}
	public void setAbsOdometer(Integer absOdometer) {
		this.absOdometer = absOdometer;
	}
	public Date getWarrantyStart() {
		return warrantyStart;
	}
	public void setWarrantyStart(Date warrantyStart) {
		this.warrantyStart = warrantyStart;
	}
	public Date getWarrantyStop() {
		return warrantyStop;
	}
	public void setWarrantyStop(Date warrantyStop) {
		this.warrantyStop = warrantyStop;
	}
	public Date getAggDate() {
		return aggDate;
	}
	public void setAggDate(Date aggDate) {
		this.aggDate = aggDate;
	}
	public Date getNewAggDate() {
		return newAggDate;
	}
	public void setNewAggDate(Date newAggDate) {
		this.newAggDate = newAggDate;
	}
	public Integer getStateID() {
		return stateID;
	}
	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((VIN == null) ? 0 : VIN.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (VIN == null) {
			if (other.VIN != null)
				return false;
		} else if (!VIN.equals(other.VIN))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
