package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Driver extends BaseEntity
{
    @ID
    private Integer driverID;
    private Integer personID;
    private DriverStatus status;
    
    @Column(name = "rfid")
    private Integer RFID;
    private String  license;            // max 10 characters

    @Column(name = "stateID")
    private State   state;

    @Column(name = "class")
    private String  licenseClass;       // max 4 characters
    
    private Date    expiration;

    @Column(updateable = false)
    private Person person;

    public Driver(Integer driverID, Integer personID, DriverStatus status, Integer rfid, String license, State state, String licenseClass, Date expiration)
    {
        super();
        this.driverID = driverID;
        this.personID = personID;
        this.status = status;
        RFID = rfid;
        this.license = license;
        this.state = state;
        this.licenseClass = licenseClass;
        this.expiration = expiration;
    }

    public Driver()
    {
        super();
    }

    public Integer getDriverID()
    {
        return driverID;
    }

    public void setDriverID(Integer driverID)
    {
        this.driverID = driverID;
    }

    public Integer getPersonID()
    {
        if (person != null)
        {
            setPersonID(person.getPersonID());
        }
        return personID;
    }

    public void setPersonID(Integer personID)
    {
        this.personID = personID;
    }

    public Integer getRFID()
    {
        return RFID;
    }

    public void setRFID(Integer rfid)
    {
        this.RFID = rfid;
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

    public String getLicenseClass()
    {
        return licenseClass;
    }

    public void setLicenseClass(String licenseClass)
    {
        this.licenseClass = licenseClass;
    }

    public Date getExpiration()
    {
        return expiration;
    }

    public void setExpiration(Date expiration)
    {
        this.expiration = expiration;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public DriverStatus getStatus()
    {
        return status;
    }

    public void setStatus(DriverStatus status)
    {
        this.status = status;
    }
    
    public Boolean getActive()
    {
        return status != null && status.equals(DriverStatus.ACTIVE);
    }
}
