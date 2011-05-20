package com.inthinc.pro.backing.model.supportData;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverName;

public class DriverNameMap extends CacheItemMap<Driver, DriverName> {
    
    private Integer groupID;
    private DriverDAO   driverDAO; 

    public DriverNameMap(Integer groupID) {
        super();
        this.groupID = groupID;
    }

    @Override
    protected DriverName fetchItem(Integer key) {
        
        if(key==null) return null;
        
        Driver driver = driverDAO.findByID(key);
        if (driver != null){
            DriverName driverName = new DriverName(driver.getDriverID(),driver.getPerson().getFullName());
            return driverName;
        }
        return null;
    }

    @Override
    protected List<DriverName> fetchItems() {
        return getDriverNames();
    }

    protected List<DriverName> getDriverNames(){
        return driverDAO.getDriverNames(groupID);
    }
    @Override
    protected GenericDAO<Driver, Integer> getDAO() {
        return driverDAO;
    }

    @Override
    protected Integer getId(DriverName item) {
        // TODO Auto-generated method stub
        return item.getDriverID();
    }

    @Override
    public List<DriverName> getItems() {
        return new ArrayList<DriverName>(itemMap.values());
    }

    @Override
    public void refreshMap() {
        buildMap();
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
