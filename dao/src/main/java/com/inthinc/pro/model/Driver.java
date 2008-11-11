package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.ID;

public class Driver extends BaseEntity
{
    @ID
    private Integer driverID;
    private Integer personID;
    private Boolean active;
    private Integer RFID;
    private String  license;
    private State   state;
    private String  licenseClass;
    private Date    expiration;
    
    private transient Person person;

    
    // cj - temporarily added this back in until we can sort out the driver/user/person stuff -- this was breaking us
    private Integer groupID;
    
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
        return personID;
    }

    public void setPersonID(Integer personID)
    {
        this.personID = personID;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
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

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }
}
