package com.inthinc.pro.aggregation.model;

import java.util.Date;
public class DeviceDay
{
    private Long           deviceID;
    private Date              day;

	public DeviceDay()
    {
    }

	public Long getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Long deviceID) {
		this.deviceID = deviceID;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

}
