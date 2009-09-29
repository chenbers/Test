package com.inthinc.pro.service.impl;

import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.service.DeviceService;
import com.inthinc.pro.util.SecurityBean;


public class DeviceServiceImpl implements DeviceService{
	
	private DeviceDAO deviceDAO;
	private SecurityBean securityBean;

	public List<Device> getAll() {
		return deviceDAO.getDevicesByAcctID(securityBean.getAccountID());
	}
	
	public Device get(Integer deviceID)
	{
		Device device = deviceDAO.findByID(deviceID);
		
		if (securityBean.isAuthorized(device))
			return device;

		return null;
	}
	
	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}
	
	public SecurityBean getSecurityBean() {
		return securityBean;
	}

	public void setSecurityBean(SecurityBean securityBean) {
		this.securityBean = securityBean;
	}
}
