package com.inthinc.pro.automation.models;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.objects.AutomationCalendar;

/**
 * You'll see this through out this bean. It is mainly used so that the table sorting on these fields treats "" and null the same. if(fieldName != null && fieldName.equals(""))
 * return null
 * 
 */

@XmlRootElement
public class Driver extends BaseEntity implements Comparable<Driver> {

    private static final long serialVersionUID = -1791892231790514608L;
    private Integer driverID;
    private Integer personID;
    private Status status;
    
    private String license; // max 10 characters
    private State state;
    private String licenseClass; // max 4 characters
    private final AutomationCalendar expiration = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    private String certifications;
    private RuleSetType dot;
    private Person person;
    private Integer groupID;

    @JsonInclude(Include.NON_NULL)
    private String barcode;
    private Long rfid1;
    private Long rfid2;


    public Driver(Integer driverID, Integer personID, Status status, String barcode, Long rfid1, Long rfid2, String license, State state, String licenseClass, Date expiration, String certifications,
            RuleSetType dot, Integer groupID) {
        super();
        this.driverID = driverID;
        this.personID = personID;
        this.status = status;
        this.barcode = barcode;
        this.rfid1 = rfid1;
        this.rfid2 = rfid2;
        this.license = license;
        this.state = state;
        this.licenseClass = licenseClass;
        this.expiration.setDate(expiration.getTime());
        this.certifications = certifications;
        this.dot = dot;
        this.groupID = groupID;
    }

    public Driver() {
        super();
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public Integer getPersonID() {
        if (person != null) {
            setPersonID(person.getPersonID());
        }
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? "" : license;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getLicenseClass() {
        if (licenseClass != null && licenseClass.equals(""))
            return null;
        return licenseClass;
    }

    public void setLicenseClass(String licenseClass) {
        this.licenseClass = licenseClass == null ? "" : licenseClass;
    }

    @JsonProperty(value="expiration")
    public String getExpirationString(){
        return expiration.toString();
    }
    
    public AutomationCalendar getExpiration() {
        return expiration;
    }

    @JsonProperty(value="expiration")
    public void setExpiration(String expiration) {
        this.expiration.setDate(expiration);
    }

    public String getCertifications() {
        // if (certifications != null && certifications.equals(""))
        // return null;
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications == null ? "" : certifications;
    }

    public RuleSetType getDot() {
        return dot;
    }

    public void setDot(RuleSetType dot) {
        this.dot = dot;
    }


    @XmlTransient
    // Prevent Circular Reference on XML rendering
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }



    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getRfid1() {
        return rfid1;
    }

    public void setRfid1(Long rfid1) {
        this.rfid1 = rfid1;
    }

    public Long getRfid2() {
        return rfid2;
    }

    public void setRfid2(Long rfid2) {
        this.rfid2 = rfid2;
    }
   

    @Override
    public int compareTo(Driver o) {
        // for now, a Person instance's compareTo seems to be the best way to determine the natural order of Drivers
        // (note: Person should never be null. If it is, there is a big problem)
        return this.person.compareTo(o.getPerson());
    }

    @Override
    public String toString() {
        return "Driver [barcode=" + barcode + ", certifications=" + certifications + ", dot=" + dot + ", driverID=" + driverID + ", expiration=" + expiration + ", groupID=" + groupID + ", license="
                + license + ", licenseClass=" + licenseClass + ", personID=" + personID + ", rfid1=" + rfid1 + ", rfid2=" + rfid2 + ", state=" + state + ", status=" + status + "]";
    }

//    public Cellblock getCellblock() {
//        return cellblock;
//    }
//
//    public void setCellblock(Cellblock cellblock) {
//        this.cellblock = cellblock;
//    }

}
