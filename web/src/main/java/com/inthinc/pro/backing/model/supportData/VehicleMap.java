package com.inthinc.pro.backing.model.supportData;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Vehicle;

public class VehicleMap extends CacheItemMap<Vehicle,Vehicle> {

    private Integer groupID;
    private VehicleDAO vehicleDAO;
    
    
    public VehicleMap(Integer groupID) {
        super();
        this.groupID = groupID;
    }

    @Override
    protected Vehicle fetchItem(Integer key) {
        
        return vehicleDAO.findByID(key);
    }

    @Override
    protected List<Vehicle> fetchItems() {
        return vehicleDAO.getVehiclesInGroupHierarchy(groupID);
    }

    @Override
    protected GenericDAO<Vehicle, Integer> getDAO() {
        return vehicleDAO;
    }

    @Override
    protected Integer getId(Vehicle item) {
        return item.getVehicleID();
    }

    @Override
    public List<Vehicle> getItems() {
        return new ArrayList<Vehicle>(itemMap.values());
    }

    @Override
    public void refreshMap() {
        buildMap();
    }

    @Override
    public void setDAO(GenericDAO<Vehicle, Integer> dao) {
        if (dao instanceof VehicleDAO){
            vehicleDAO = (VehicleDAO) dao;
        }
    }
    
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

}
