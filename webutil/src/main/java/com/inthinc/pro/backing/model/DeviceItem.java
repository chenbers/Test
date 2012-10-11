package com.inthinc.pro.backing.model;

import com.inthinc.pro.model.DeviceStatus;

public class DeviceItem {
	private Integer deviceID;
	private String deviceName;
    private String deviceSIM;
	private String deviceIMEI;
    private DeviceStatus deviceStatus;
    private boolean selected;

	public DeviceItem()
	{
		
	}
	public DeviceItem(Integer deviceID, String deviceName, String deviceSIM, String deviceIMEI, DeviceStatus deviceStatus) 
	{
		this.deviceID = deviceID;
		this.deviceName = deviceName;
		this.deviceSIM = deviceSIM;
		this.deviceIMEI = deviceIMEI;
		this.deviceStatus = deviceStatus;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceIMEI() {
		return deviceIMEI;
	}
	public void setDeviceIMEI(String deviceIMEI) {
		this.deviceIMEI = deviceIMEI;
	}
	public DeviceStatus getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(DeviceStatus deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
    public String getDeviceSIM() {
		return (deviceSIM == null) ? "" : deviceSIM;
	}
	public void setDeviceSIM(String deviceSIM) {
		this.deviceSIM = deviceSIM;
	}
	public Integer getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}
}
