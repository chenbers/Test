package com.inthinc.pro.model.configurator;

import java.util.List;

import com.inthinc.pro.dao.ConfiguratorDAO;

public class VehicleSettings {
    
    private ConfiguratorDAO configuratorDAO;
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }

    public List<VehicleSetting> getVehicleSettings(Integer selectedGroup){
        
        return configuratorDAO.getVehicleSettingsByGroupIDDeep(selectedGroup);
    }


}
