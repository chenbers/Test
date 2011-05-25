package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriverName implements Comparable<DriverName>{
    
    private Integer driverID;
    private String driverName;
    
    public DriverName() {
    }
    public DriverName(Integer driverID, String driverName) {
        super();
        this.driverID = driverID;
        this.driverName = driverName;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    @Override
    public int compareTo(DriverName o) {
        
        return driverName.compareTo(o.getDriverName());
    }
    
}
