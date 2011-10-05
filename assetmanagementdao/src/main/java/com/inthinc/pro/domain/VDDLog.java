package com.inthinc.pro.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
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
	private Integer deviceID;
	private Integer baseID;
	private Integer emuFeatureMask;
	private Integer vehicleID;
	private Integer vgroupID;
	private Integer vtype;	
	private Integer driverID;
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
	public Integer getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
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
	public Integer getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
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
	public Integer getDriverID() {
		return driverID;
	}
	public void setDriverID(Integer driverID) {
		this.driverID = driverID;
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
				+ ", deviceID=" + deviceID + ", baseID=" + baseID
				+ ", emuFeatureMask=" + emuFeatureMask + ", vehicleID="
				+ vehicleID + ", vgroupID=" + vgroupID + ", vtype=" + vtype
				+ ", driverID=" + driverID + ", dgroupID=" + dgroupID
				+ ", tzID=" + tzID + "]";
	}
}
