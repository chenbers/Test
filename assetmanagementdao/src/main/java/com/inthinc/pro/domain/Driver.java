package com.inthinc.pro.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.configurator.model.Status;

/**
 * Entity implementation class for Entity: Driver
 *
 */
@Entity
@Table(name="driver")
public class Driver implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer driverID;
	@ManyToOne
	@JoinColumn(name="groupID")
	@Basic(fetch=FetchType.LAZY)
	private Group group;
	@OneToOne
	@JoinColumn(name="personID")
	@Basic(fetch=FetchType.LAZY)
	private Person person;
	
	private Status status;
	private String groupPath;
	@Temporal(TemporalType.DATE)
	private Date modified;
    private String barcode;
    private Long rfid1;
    private Long rfid2;
    private String license; // max 10 characters
	@ManyToOne
	@JoinColumn(name="stateID")
	@Basic(fetch=FetchType.LAZY)
    private State state;
    @Column(name = "class")
    private String licenseClass; // max 4 characters
	@Temporal(TemporalType.TIMESTAMP)
    private Date expiration;
    @Column(name = "certs")
    private String certifications;
    private RuleSetType dot;

    public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

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

	public String getGroupPath() {
		return groupPath;
	}

	public void setGroupPath(String groupPath) {
		this.groupPath = groupPath;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
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

	
	public Integer getDriverID() {
		return driverID;
	}

	public void setDriverID(Integer driverID) {
		this.driverID = driverID;
	}

	public Driver() {
		super();
	}
   
}
