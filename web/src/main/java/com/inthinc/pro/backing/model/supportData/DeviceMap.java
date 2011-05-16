package com.inthinc.pro.backing.model.supportData;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;

public class DeviceMap extends CacheItemMap<Device, Device> {

    private Integer accountID;
    private DeviceDAO deviceDAO;

    public DeviceMap(Integer accountID) {
        super();
        this.accountID = accountID;
    }

    @Override
    protected Device fetchItem(Integer key) {
        return deviceDAO.findByID(key);
    }

    @Override
    protected List<Device> fetchItems() {
        return getDevices();
    }

    @Override
    protected GenericDAO<Device, Integer> getDAO() {
        return deviceDAO;
    }

    @Override
    protected Integer getId(Device item) {
        return item.getDeviceID();
    }

    @Override
    public List<Device> getItems() {
        return new ArrayList<Device>(itemMap.values());
    }

    @Override
    public void refreshMap() {
        buildMap();
    }

    @Override
    public void setDAO(GenericDAO<Device, Integer> dao) {
        if (dao instanceof DeviceDAO){
            deviceDAO = (DeviceDAO) dao;
        }
    }
    protected List<Device> getDevices()
    {
        return deviceDAO.getDevicesByAcctID(accountID);
    }

}
