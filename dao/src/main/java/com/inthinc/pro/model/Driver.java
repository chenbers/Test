package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Driver extends BaseEntity implements Comparable<Driver>
{
    @Column(updateable = false)
    private static final long serialVersionUID = -1791892231790514608L;

    @ID
    private Integer driverID;
    private Integer personID;
    private Status status;

    @Column(name = "rfid")
    private Long RFID;
    private String license; // max 10 characters

    @Column(name = "stateID")
    private State state;

    @Column(name = "class")
    private String licenseClass; // max 4 characters

    private Date expiration;
    @Column(name = "certs")
    private String certifications;
    private String dot;

    @Column(updateable = false)
    private Person person;

    private Integer groupID;

    public Driver(Integer driverID, Integer personID, Status status, Long rfid, String license, State state, String licenseClass, Date expiration, String certifications,
            String dot, Integer groupID)
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
        this.certifications = certifications;
        this.dot = dot;
        this.groupID = groupID;
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

    public Long getRFID()
    {
        return RFID;
    }

    public void setRFID(Long rfid)
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

    public String getCertifications()
    {
        return certifications;
    }

    public void setCertifications(String certifications)
    {
        this.certifications = certifications;
    }

    public String getDot()
    {
        return dot;
    }

    public void setDot(String dot)
    {
        this.dot = dot;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    @Override
    public int compareTo(Driver o)
    {
        // for now, a Person instance's compareTo seems to be the best way to determine the natural order of Drivers
        // (note: Person should never be null. If it is, there is a big problem)
        return this.person.compareTo(o.getPerson());
    }

}
