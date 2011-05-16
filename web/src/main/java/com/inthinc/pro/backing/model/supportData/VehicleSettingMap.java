package com.inthinc.pro.backing.model.supportData;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettingMap extends CacheItemMap<DeviceSettingDefinition,VehicleSetting> {

    private Integer groupID;
    private ConfiguratorDAO configuratorDAO;

    public VehicleSettingMap(Integer groupID) {
        super();
        this.groupID = groupID;
    }

    @Override
    protected VehicleSetting fetchItem(Integer key) {
//        return configuratorDAO.getVehicleSettings(key);
        return null;
    }

    @Override
    protected List<VehicleSetting> fetchItems() {
        return configuratorDAO.getVehicleSettingsByGroupIDDeep(groupID);
    }

    @Override
    protected ConfiguratorDAO getDAO() {
        return configuratorDAO;
    }

    @Override
    protected Integer getId(VehicleSetting item) {
        return item.getVehicleID();
    }

    @Override
    public List<VehicleSetting> getItems() {
        return new ArrayList<VehicleSetting>(itemMap.values());
    }

    @Override
    public void refreshMap() {
        buildMap();
    }

    @Override
    public void setDAO(GenericDAO<DeviceSettingDefinition, Integer> dao) {
        if (dao instanceof ConfiguratorDAO){
            configuratorDAO = (ConfiguratorDAO) dao;
        }
    }
}
