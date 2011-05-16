package com.inthinc.pro.backing.model.supportData;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Driver;

public class DriverMap extends CacheItemMap<Driver,Driver> {

    private Integer groupID;
    private DriverDAO   driverDAO; 
    
    public DriverMap(Integer groupID) {
        super();
        this.groupID = groupID;
    }

    @Override
    protected Driver fetchItem(Integer key) {
        return driverDAO.findByID(key);
    }

    @Override
    protected List<Driver> fetchItems() {
        return getDrivers();
    }

    @Override
    protected Integer getId(Driver item) {
        return item.getDriverID();
    }

    @Override
    public List<Driver> getItems() {
        return new ArrayList<Driver>(itemMap.values());
    }

    @Override
    public void refreshMap() {
        buildMap();
    }
    protected List<Driver> getDrivers(){
        return driverDAO.getAllDrivers(groupID);
    }
    @Override
    protected GenericDAO<Driver, Integer> getDAO() {
        return driverDAO;
    }
    @Override
    public void setDAO(GenericDAO<Driver, Integer> dao) {
        if (dao instanceof DriverDAO){
            driverDAO = (DriverDAO) dao;
        }
    }
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
}
