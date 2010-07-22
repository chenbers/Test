package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.ConfiguratorMapper;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class ConfiguratorHessianDAO extends GenericHessianDAO<DeviceSettingDefinition, Integer> implements ConfiguratorDAO {

    private static final long serialVersionUID = 1L;

    public ConfiguratorHessianDAO() {
        super();
        super.setMapper(new ConfiguratorMapper());
    }

    @Override
    public List<DeviceSettingDefinition> getDeviceSettingDefinitions() {
 
        return getMapper().convertToModelObject(getSiloService().getSettingDefs(), DeviceSettingDefinition.class);
    }

    @Override
    public List<VehicleSetting> getVehicleSettingsByGroupIDDeep(Integer groupID) {
        try {
            
            return getMapper().convertToModelObject(getSiloService().getVehicleSettingsByGroupIDDeep(groupID), VehicleSetting.class);
        } 
        catch (EmptyResultSetException e) {
            
            return Collections.emptyList();
        }
    }
}
