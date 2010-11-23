package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.model.phone.ServiceProviderType;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * You'll see this through out this bean. It is mainly used so that the table sorting on these fields treats "" and null the same. if(fieldName != null && fieldName.equals(""))
 * return null
 * 
 */

@XmlRootElement
public class Driver extends BaseEntity implements Comparable<Driver> {

    @Column(updateable = false)
    private static final long serialVersionUID = -1791892231790514608L;
    @ID
    private Integer driverID;
    private Integer personID;
    private Status status;
    private String barcode;
    private Long rfid1;
    private Long rfid2;
    private String license; // max 10 characters
    @Column(name = "stateID")
    private State state;
    @Column(name = "class")
    private String licenseClass; // max 4 characters
    private Date expiration;
    @Column(name = "certs")
    private String certifications;
    private Integer dot;
    @Column(updateable = false)
    private Person person;
    private Integer groupID;
    private String cellPhone;
    private CellStatusType cellStatus;
    private ServiceProviderType provider;

    public Driver(Integer driverID, Integer personID, Status status, String barcode, Long rfid1, Long rfid2, String license, State state, String licenseClass, Date expiration, String certifications,
            Integer dot, Integer groupID) {
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
        this.expiration = expiration;
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

    public String getLicense() {
        if (license != null && license.equals(""))
            return null;
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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
        this.licenseClass = licenseClass;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getCertifications() {
        // if (certifications != null && certifications.equals(""))
        // return null;
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public Integer getDot() {
        return dot;
    }

    public void setDot(Integer dot) {
        this.dot = dot;
    }
    

    public RuleSetType getDriverDOTType() {
        return (dot == null) ? null : RuleSetType.valueOf(dot);
    }

    public void setDriverDOTType(RuleSetType driverDOTType) {
        this.dot = driverDOTType == null ? null : driverDOTType.getCode();
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

    /**
     * The cellPhone getter.
     * @return the cellPhone
     */
    public String getCellPhone() {
        return this.cellPhone;
    }

    /**
     * The cellPhone setter.
     * @param cellPhone the cellPhone to set
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * The cellStatus getter.
     * @return the cellStatus
     */
    public CellStatusType getCellStatus() {
        return this.cellStatus;
    }
    
    /**
     * The provider getter.
     * @return the provider
     */
    public ServiceProviderType getProvider() {
        return this.provider;
    }

    /**
     * The cellStatus setter.
     * @param cellStatus the cellStatus to set
     */
    public void setCellStatus(CellStatusType cellStatus) {
        this.cellStatus = cellStatus;
    }

    /**
     * The provider setter.
     * @param provider the provider to set
     */
    public void setProvider(ServiceProviderType provider) {
        this.provider = provider;
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
                + license + ", licenseClass=" + licenseClass + ", personID=" + personID + ", rfid1=" + rfid1 + ", rfid2=" + rfid2 + ", state=" + state + ", status=" + status
                + "]";
    }

}
