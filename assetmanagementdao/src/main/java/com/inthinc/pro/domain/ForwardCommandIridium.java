package com.inthinc.pro.domain;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name="Fwd_WSiridium")
public class ForwardCommandIridium {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer Fwd_WSiridiumID;
    private byte[] data;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    private Integer processing;
    private ForwardCommandStatus status;
    private IridiumFCStatus iridiumStatus;
    private Integer command;
	@Column(name = "datatype")
	private ForwardCommandParamType dataType;
    private Integer personID;
    private Integer driverID;
    private Integer vehicleID;
	private Integer deviceID;
    
    
    public ForwardCommandIridium() {
		super();
	}

    public ForwardCommandIridium(Integer fwd_WSiridiumID, byte[] data,
			Date created, Date modified, Integer processing,
			ForwardCommandStatus status, IridiumFCStatus iridiumStatus,
			Integer command, ForwardCommandParamType dataType,
			Integer personID, Integer driverID, Integer vehicleID,
			Integer deviceID) {
		super();
		Fwd_WSiridiumID = fwd_WSiridiumID;
		this.data = data;
		this.created = created;
		this.modified = modified;
		this.processing = processing;
		this.status = status;
		this.iridiumStatus = iridiumStatus;
		this.command = command;
		this.dataType = dataType;
		this.personID = personID;
		this.driverID = driverID;
		this.vehicleID = vehicleID;
		this.deviceID = deviceID;
	}

	public Integer getFwd_WSiridiumID() {
		return Fwd_WSiridiumID;
	}
	public void setFwd_WSiridiumID(Integer fwd_WSiridiumID) {
		Fwd_WSiridiumID = fwd_WSiridiumID;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
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
	public Integer getProcessing() {
		return processing;
	}
	public void setProcessing(Integer processing) {
		this.processing = processing;
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
	public Integer getCommand() {
		return command;
	}
	public void setCommand(Integer command) {
		this.command = command;
	}
	public ForwardCommandParamType getDataType() {
		return dataType;
	}
	public void setDataType(ForwardCommandParamType dataType) {
		this.dataType = dataType;
	}
	public Integer getPersonID() {
		return personID;
	}
	public void setPersonID(Integer personID) {
		this.personID = personID;
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
	public Integer getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	@Override
	public String toString() {
		return "ForwardCommandIridium [Fwd_WSiridiumID=" + Fwd_WSiridiumID
				+ ", data=" + Arrays.toString(data) + ", created=" + created
				+ ", modified=" + modified + ", processing=" + processing
				+ ", status=" + status + ", iridiumStatus=" + iridiumStatus
				+ ", command=" + command + ", dataType=" + dataType
				+ ", personID=" + personID + ", driverID=" + driverID
				+ ", vehicleID=" + vehicleID + ", deviceID=" + deviceID + "]";
	}

}
