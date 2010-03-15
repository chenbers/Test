package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeviceReportItem extends BaseEntity
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -627734795756981820L;
	private String deviceName;
    private String vehicleName;
    private Integer vehicleID;
    private String deviceIMEI;
    private String devicePhone;
    private String deviceEPhone;
    private DeviceStatus deviceStatus;

    public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public Integer getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}

	public String getDeviceIMEI() {
		return deviceIMEI;
	}

	public void setDeviceIMEI(String deviceIMEI) {
		this.deviceIMEI = deviceIMEI;
	}

	public String getDevicePhone() {
		return devicePhone;
	}

	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}

	public String getDeviceEPhone() {
		return deviceEPhone;
	}

	public void setDeviceEPhone(String deviceEPhone) {
		this.deviceEPhone = deviceEPhone;
	}

	public DeviceStatus getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(DeviceStatus deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
}
