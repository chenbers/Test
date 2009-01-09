package com.inthinc.pro.dao.hessian.mapper;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.Device;

public class DeviceMapper extends AbstractMapper
{
    private static final Integer DEFAULT_LEVEL = 3;
    
    @ConvertColumnToField(columnName = "accel")
    public void accelToModel(Device device, Object value)
    {
        if (device == null || value == null)
            return;

        if (value instanceof String)
        {
            device.setHardAcceleration(parseLevel(value));

        }
    }
    @ConvertColumnToField(columnName = "brake")
    public void brakeToModel(Device device, Object value)
    {
        if (device == null || value == null)
            return;

        if (value instanceof String)
        {
            device.setHardBrake(parseLevel(value));

        }
    }
    @ConvertColumnToField(columnName = "turn")
    public void turnToModel(Device device, Object value)
    {
        if (device == null || value == null)
            return;

        if (value instanceof String)
        {
            device.setHardTurn(parseLevel(value));

        }
    }
    @ConvertColumnToField(columnName = "vert")
    public void vertToModel(Device device, Object value)
    {
        if (device == null || value == null)
            return;

        if (value instanceof String)
        {
            device.setHardVertical(parseLevel(value));

        }
    }

    private Integer parseLevel(Object value)
    {
        String[] comp = ((String) value).split(";", 2);
        Integer level = null;
        if (comp.length == 3)
        {
            try
            {
                level = Integer.valueOf(comp[2]);
            }
            catch (NumberFormatException ex)
            {
                level = DEFAULT_LEVEL;
            }
            
        }
        else
        {
            level = DEFAULT_LEVEL;
        }
        return level;
    }

    
 
}
