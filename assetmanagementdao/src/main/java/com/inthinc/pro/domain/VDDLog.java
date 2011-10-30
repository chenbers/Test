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
	@ManyToOne
	@JoinColumn(name="acctID")
	@Basic(fetch=FetchType.LAZY)
	private Account  account;
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
	@ManyToOne
	@JoinColumn(name="vgroupID")
	@Basic(fetch=FetchType.LAZY)
	private Group vehicleGroup;
	private Integer vtype;	
	@ManyToOne
	@JoinColumn(name="driverID")
	@Basic(fetch=FetchType.LAZY)
	private Driver driver;
	
	@ManyToOne
	@JoinColumn(name="dgroupID")
	@Basic(fetch=FetchType.LAZY)
	private Group driverGroup;
	@ManyToOne
	@JoinColumn(name="tzID")
	@Basic(fetch=FetchType.LAZY)
	private SupportedTimeZone timeZone;
	
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
//	public Integer getAcctID() {
//		return acctID;
//	}
//	public void setAcctID(Integer acctID) {
//		this.acctID = acctID;
//	}
	public Device getDevice() {
		return device;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
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
	public Group getVehicleGroup() {
		return vehicleGroup;
	}
	public void setVehicleGroup(Group vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}
	public Group getDriverGroup() {
		return driverGroup;
	}
	public void setDriverGroup(Group driverGroup) {
		this.driverGroup = driverGroup;
	}
	public SupportedTimeZone getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(SupportedTimeZone timeZone) {
		this.timeZone = timeZone;
	}
}
