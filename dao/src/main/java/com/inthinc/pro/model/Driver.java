package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;

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
    private RuleSetType dot;
    @Column(updateable = false)
    private Person person;
    private Integer groupID;
    @Column(updateable = false)
    private CellProviderInfo cellProviderInfo;
    
    public static class CellProviderInfo {
        private String cellPhone;
        private CellStatusType cellStatus;
        private CellProviderType provider;
        private String providerUsername;
        
        private String providerPassword;
        
        public String getProviderPassword() {
            return providerPassword;
        }
        @XmlTransient
        public void setProviderPassword(String providerPassword) {
            this.providerPassword = providerPassword;
        }
        public String getCellPhone() {
            return cellPhone;
        }
        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }
        public CellStatusType getCellStatus() {
            return cellStatus;
        }
        public void setCellStatus(CellStatusType cellStatus) {
            this.cellStatus = cellStatus;
        }
        public CellProviderType getProvider() {
            return provider;
        }
        public void setProvider(CellProviderType provider) {
            this.provider = provider;
        }
        public String getProviderUsername() {
            return providerUsername;
        }
        public void setProviderUsername(String providerUsername) {
            this.providerUsername = providerUsername;
        }   
    }

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
    
    public CellProviderInfo getCellProviderInfo() {
        return this.cellProviderInfo;
    }

    public void setCellProviderInfo(CellProviderInfo cellProviderInfo) {
        this.cellProviderInfo = cellProviderInfo;
    }
}
