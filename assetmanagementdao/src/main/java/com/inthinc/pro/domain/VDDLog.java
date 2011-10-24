package com.inthinc.pro.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="vddlog")
public class VDDLog {
	@Id
	private Integer vddlogID;
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;
	@Temporal(TemporalType.TIMESTAMP)
	private Date stop;
	private String imei;
	private Integer acctID;
	@ManyToOne
	@JoinColumn(name="deviceID")
	@Basic(fetch=FetchType.LAZY)
	private Device device;
	private Integer baseID;
	private Integer emuFeatureMask;
	@ManyToOne
	@JoinColumn(name="vehicleID")
	@Basic(fetch=FetchType.LAZY)
	private Vehicle vehicle;
	private Integer vgroupID;
	private Integer vtype;	
	@ManyToOne
	@JoinColumn(name="driverID")
	@Basic(fetch=FetchType.LAZY)
	private Driver driver;
	private Integer dgroupID;	
	private Integer tzID;
	
	public Integer getVddlogID() {
		return vddlogID;
	}
	public void setVddlogID(Integer vddlogID) {
		this.vddlogID = vddlogID;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getStop() {
		return stop;
	}
	public void setStop(Date stop) {
		this.stop = stop;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Integer getAcctID() {
		return acctID;
	}
	public void setAcctID(Integer acctID) {
		this.acctID = acctID;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Integer getBaseID() {
		return baseID;
	}
	public void setBaseID(Integer baseID) {
		this.baseID = baseID;
	}
	public Integer getEmuFeatureMask() {
		return emuFeatureMask;
	}
	public void setEmuFeatureMask(Integer emuFeatureMask) {
		this.emuFeatureMask = emuFeatureMask;
	}
	public Integer getVgroupID() {
		return vgroupID;
	}
	public void setVgroupID(Integer vgroupID) {
		this.vgroupID = vgroupID;
	}
	public Integer getVtype() {
		return vtype;
	}
	public void setVtype(Integer vtype) {
		this.vtype = vtype;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public Integer getDgroupID() {
		return dgroupID;
	}
	public void setDgroupID(Integer dgroupID) {
		this.dgroupID = dgroupID;
	}
	public Integer getTzID() {
		return tzID;
	}
	public void setTzID(Integer tzID) {
		this.tzID = tzID;
	}
	@Override
	public String toString() {
		return "VDDLog [vddlogID=" + vddlogID + ", start=" + start + ", stop="
				+ stop + ", imei=" + imei + ", acctID=" + acctID
				+ ", device=" + device.toString() +
				", baseID=" + baseID
				+ ", emuFeatureMask=" + emuFeatureMask 
				+ ", vehicleID="+ vehicle.toString() 
				+ ", vgroupID=" + vgroupID + ", vtype=" + vtype
				+ ", driver=" + driver.toString() + ", dgroupID=" + dgroupID
				+ ", tzID=" + tzID + "]";
	}
}
