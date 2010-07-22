package com.inthinc.pro.dao.hessian.mapper;

import java.util.Arrays;
import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class ConfiguratorMapper extends AbstractMapper {

    private static final long serialVersionUID = 1L;

    @ConvertColumnToField(columnName = "choices")
    public void choicesToModel(DeviceSettingDefinition deviceSettingDefinition, Object value)
    {
        
        if (deviceSettingDefinition == null || value == null)
            return;

        if (value instanceof String)
        {
            deviceSettingDefinition.setChoices(Arrays.asList(((String)value).split(":")));
        }
    }
    @SuppressWarnings("unchecked")
    @ConvertColumnToField(columnName = "actual")
    public void actualToModel(VehicleSetting vehicleSetting, Object value)
    {
        if (value instanceof Map){
            
            vehicleSetting.setActual((Map<Integer,String>) value);
        }
    }
    @SuppressWarnings("unchecked")
    @ConvertColumnToField(columnName = "desired")
    public void desiredToModel(VehicleSetting vehicleSetting, Object value)
    {
        if (value instanceof Map){
            
            vehicleSetting.setDesired((Map<Integer,String>) value);
        }
    }
}
