package com.inthinc.pro.backing.model.supportData;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleName;

public class VehicleNameMap extends CacheItemMap<Vehicle, VehicleName> {
    private Integer groupID;
    private VehicleDAO vehicleDAO; 

    public VehicleNameMap(Integer groupID) {
        super();
        this.groupID = groupID;
    }

    @Override
    protected VehicleName fetchItem(Integer key) {
        Vehicle vehicle = vehicleDAO.findByID(key);
        if(vehicle != null){
            VehicleName vehicleName = new VehicleName(vehicle.getVehicleID(),vehicle.getName());
            return vehicleName;
        }
        return null;
    }

    @Override
    protected List<VehicleName> fetchItems() {
        return getVehicleNames();
    }

    protected List<VehicleName> getVehicleNames(){
        return vehicleDAO.getVehicleNames(groupID);
    }
    @Override
    protected GenericDAO<Vehicle, Integer> getDAO() {
        return vehicleDAO;
    }

    @Override
    protected Integer getId(VehicleName item) {
        // TODO Auto-generated method stub
        return item.getVehicleID();
    }

    @Override
    public List<VehicleName> getItems() {
        return new ArrayList<VehicleName>(itemMap.values());
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
