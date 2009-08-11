package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class VehicleOccupant extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 7041387131328308915L;
    
    @ID
    private Integer occupantID;
    private Boolean seatBelt;
    private Boolean isDriver;
    private String name;
    
    @Column(name = "addrID")
    private Integer addressID;
    @Column(updateable=false)
    private Address address;
    private String license;
    private InjuryType injuryType;
    private Integer personID;

    public Integer getOccupantID() {
        return occupantID;
    }

    public void setOccupantID(Integer occupantID) {
        this.occupantID = occupantID;
    }

    public Boolean getSeatBelt() {
        return seatBelt;
    }

    public void setSeatBelt(Boolean seatBelt) {
        this.seatBelt = seatBelt;
    }

    public Boolean getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(Boolean isDriver) {
        this.isDriver = isDriver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public InjuryType getInjuryType() {
        return injuryType;
    }

    public void setInjuryType(InjuryType injuryType) {
        this.injuryType = injuryType;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    @Override
    public String toString() {
        return "VehicleOccupant [address=" + address + ", injuryType=" + injuryType + ", isDriver=" + isDriver + ", license=" + license + ", name=" + name + ", occupantID="
                + occupantID + ", personID=" + personID + ", seatBelt=" + seatBelt + "]";
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public Integer getAddressID() {
        return addressID;
    }
}
