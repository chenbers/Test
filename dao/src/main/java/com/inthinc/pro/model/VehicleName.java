package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VehicleName implements Comparable<VehicleName> {
    private Integer vehicleID;
    private String vehicleName;
    
    
    public VehicleName() {
    }
    public VehicleName(Integer vehicleID, String vehicleName) {
        super();
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    @Override
    public int compareTo(VehicleName o) {
        
        return vehicleName.compareTo(o.getVehicleName());
    }

}
