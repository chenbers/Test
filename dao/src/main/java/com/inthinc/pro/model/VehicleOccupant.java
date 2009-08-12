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
    private OccupantType occupantType;
    private String name;
    @Column(name = "addrID")
    private Integer addressID;
    @Column(updateable = false)
    private Address address;
    private String license;
    private InjuryType injuryType;
    private Integer personID; //Not required
    private Integer crashReportID;
    @Column(updateable = false)
    private CrashReport crashReport;
    
    public VehicleOccupant(){
        
    }

    public VehicleOccupant(Integer occupantID, Boolean seatBelt, String name, Address address, String license, InjuryType injuryType,
            Integer personID, CrashReport crashReport,OccupantType occupantType) {
        super();
        this.occupantID = occupantID;
        this.seatBelt = seatBelt;
        this.name = name;
        this.addressID = address != null?address.getAddrID():null;
        this.address = address;
        this.license = license;
        this.injuryType = injuryType;
        this.personID = personID;
        this.crashReportID = crashReport != null?crashReport.getCrashReportID():null;
        this.crashReport = crashReport;
        this.occupantType = occupantType;
    }

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

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setCrashReportID(Integer crashReportID) {
        this.crashReportID = crashReportID;
    }

    public Integer getCrashReportID() {
        return crashReportID;
    }

    public void setCrashReport(CrashReport crashReport) {
        this.crashReport = crashReport;
    }

    public CrashReport getCrashReport() {
        return crashReport;
    }

    @Override
    public String toString() {
        return "VehicleOccupant [address=" + address + ", injuryType=" + injuryType  + ", license=" + license + ", name=" + name + ", occupantID="
                + occupantID + ", personID=" + personID + ", seatBelt=" + seatBelt + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((occupantID == null) ? 0 : occupantID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VehicleOccupant)) {
            return false;
        }
        VehicleOccupant other = (VehicleOccupant) obj;
        if (occupantID == null) {
            if (other.occupantID != null) {
                return false;
            }
        }
        else if (!occupantID.equals(other.occupantID)) {
            return false;
        }
        return true;
    }

    public void setOccupantType(OccupantType occupantType) {
        this.occupantType = occupantType;
    }

    public OccupantType getOccupantType() {
        return occupantType;
    }
}
