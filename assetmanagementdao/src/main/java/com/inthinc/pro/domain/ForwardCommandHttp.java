package com.inthinc.pro.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.inthinc.pro.configurator.model.ForwardCommandStatus;

@Entity
@Table(name="fwd")
public class ForwardCommandHttp {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer fwdID;
    private Integer deviceID;
    private Integer driverID;
    private Integer vehicleID;
    private Integer personID;
    private Integer fwdCmd;
    private Integer fwdInt;
    private String fwdStr;
    private Integer tries;
    private ForwardCommandStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

	public ForwardCommandHttp() {
		super();
	}

	public ForwardCommandHttp(Integer fwdID, Integer deviceID, Integer driverID,
			Integer vehicleID, Integer personID, Integer fwdCmd,
			Integer fwdInt, String fwdStr, Integer tries,
			ForwardCommandStatus status, Date created, Date modified) {
		super();
		this.fwdID = fwdID;
		this.deviceID = deviceID;
		this.driverID = driverID;
		this.vehicleID = vehicleID;
		this.personID = personID;
		this.fwdCmd = fwdCmd;
		this.fwdInt = fwdInt;
		this.fwdStr = fwdStr;
		this.tries = tries;
		this.status = status;
		this.created = created;
		this.modified = modified;
	}
	public ForwardCommandHttp(Integer deviceID, Integer driverID,
			Integer vehicleID, Integer personID, Integer fwdCmd,
			Integer fwdInt, String fwdStr, Integer tries,
			ForwardCommandStatus status, Date created, Date modified) {
		super();
		this.fwdID = null;
		this.deviceID = deviceID;
		this.driverID = driverID;
		this.vehicleID = vehicleID;
		this.personID = personID;
		this.fwdCmd = fwdCmd;
		this.fwdInt = fwdInt;
		this.fwdStr = fwdStr;
		this.tries = tries;
		this.status = status;
		this.created = created;
		this.modified = modified;
	}
	public Integer getFwdID() {
		return fwdID;
	}

	public void setFwdID(Integer fwdID) {
		this.fwdID = fwdID;
	}

	public Integer getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	public Integer getDriverID() {
		return driverID;
	}

	public void setDriverID(Integer driverID) {
		this.driverID = driverID;
	}

	public Integer getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}

	public Integer getPersonID() {
		return personID;
	}

	public void setPersonID(Integer personID) {
		this.personID = personID;
	}

	public Integer getFwdCmd() {
		return fwdCmd;
	}

	public void setFwdCmd(Integer fwdCmd) {
		this.fwdCmd = fwdCmd;
	}

	public Integer getFwdInt() {
		return fwdInt;
	}

	public void setFwdInt(Integer fwdInt) {
		this.fwdInt = fwdInt;
	}

	public String getFwdStr() {
		return fwdStr;
	}

	public void setFwdStr(String fwdStr) {
		this.fwdStr = fwdStr;
	}

	public Integer getTries() {
		return tries;
	}

	public void setTries(Integer tries) {
		this.tries = tries;
	}

	public ForwardCommandStatus getStatus() {
		return status;
	}

	public void setStatus(ForwardCommandStatus status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public String toString() {
		return "ForwardCommand [fwdID=" + fwdID + ", deviceID=" + deviceID
				+ ", driverID=" + driverID + ", vehicleID=" + vehicleID
				+ ", personID=" + personID + ", fwdCmd=" + fwdCmd + ", fwdInt="
				+ fwdInt + ", fwdStr=" + fwdStr + ", tries=" + tries
				+ ", status=" + status + ", created=" + created + ", modified="
				+ modified + ", getFwdID()=" + getFwdID() + ", getDeviceID()="
				+ getDeviceID() + ", getDriverID()=" + getDriverID()
				+ ", getVehicleID()=" + getVehicleID() + ", getPersonID()="
				+ getPersonID() + ", getFwdCmd()=" + getFwdCmd()
				+ ", getFwdInt()=" + getFwdInt() + ", getFwdStr()="
				+ getFwdStr() + ", getTries()=" + getTries() + ", getStatus()="
				+ getStatus() + ", getCreated()=" + getCreated()
				+ ", getModified()=" + getModified() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}


}
