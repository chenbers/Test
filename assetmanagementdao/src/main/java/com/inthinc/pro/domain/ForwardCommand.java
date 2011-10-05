package com.inthinc.pro.domain;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.inthinc.pro.configurator.model.ForwardCommandParamType;
import com.inthinc.pro.configurator.model.ForwardCommandStatus;
import com.inthinc.pro.configurator.model.IridiumFCStatus;

@Entity
@Table(name="fwd")
public class ForwardCommand {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer fwdID;
    private ForwardCommandParamType dataType;
    private Integer deviceID;
    private String address;
    private Integer command;
    private byte[] data;
    private String strData;
    private Integer intData;
    private Integer processed;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    private ForwardCommandStatus status;
    private IridiumFCStatus iridiumStatus;

	public ForwardCommand() {
		super();
	}

    public ForwardCommand(ForwardCommandParamType dataType, Integer deviceID,
			String address, Integer command, byte[] data, String strData,
			Integer intData) {
		super();
		this.dataType = dataType;
		this.deviceID = deviceID;
		this.address = address;
		this.command = command;
		this.data = data;
		this.strData = strData;
		this.intData = intData;
		this.status = ForwardCommandStatus.STATUS_QUEUED;
		this.created = new Date();
		this.modified = new Date();
	}
    
	public Integer getFwdID() {
		return fwdID;
	}

	public void setFwdID(Integer fwdID) {
		this.fwdID = fwdID;
	}

	public ForwardCommandParamType getDataType() {
		return dataType;
	}

	public void setDataType(ForwardCommandParamType dataType) {
		this.dataType = dataType;
	}

	public Integer getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCommand() {
		return command;
	}

	public void setCommand(Integer command) {
		this.command = command;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getStrData() {
		return strData;
	}

	public void setStrData(String strData) {
		this.strData = strData;
	}

	public Integer getIntData() {
		return intData;
	}

	public void setIntData(Integer intData) {
		this.intData = intData;
	}

	public Integer getProcessed() {
		return processed;
	}

	public void setProcessed(Integer processed) {
		this.processed = processed;
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

	public ForwardCommandStatus getStatus() {
		return status;
	}

	public void setStatus(ForwardCommandStatus status) {
		this.status = status;
	}

	public IridiumFCStatus getIridiumStatus() {
		return iridiumStatus;
	}

	public void setIridiumStatus(IridiumFCStatus iridiumStatus) {
		this.iridiumStatus = iridiumStatus;
	}

	@Override
	public String toString() {
		return "ForwardCommand [fwdID=" + fwdID + ", dataType=" + dataType
				+ ", deviceID=" + deviceID + ", address=" + address
				+ ", command=" + command + ", data=" + Arrays.toString(data)
				+ ", strData=" + strData + ", intData=" + intData
				+ ", processed=" + processed + ", created=" + created
				+ ", modified=" + modified + ", status=" + status
				+ ", iridiumStatus=" + iridiumStatus + "]";
	}
}
