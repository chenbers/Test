package com.inthinc.pro.dao.hessian;

import java.util.List;

import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;

public class ConfiguratorHessianDAO extends GenericHessianDAO<DeviceSettingDefinition, Integer> implements ConfiguratorDAO {

    private static final long serialVersionUID = 1L;

    @Override
    public List<DeviceSettingDefinition> getDeviceSettingDefinitions() {
 
        return getMapper().convertToModelObject(getSiloService().getSettingDefs(), DeviceSettingDefinition.class);
    }
}
