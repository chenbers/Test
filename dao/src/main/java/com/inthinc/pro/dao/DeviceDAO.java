package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Device;

public interface DeviceDAO extends GenericDAO<Device, Integer>
{
    List<Device> getDevicesByAcctID(Integer accountID);
}
