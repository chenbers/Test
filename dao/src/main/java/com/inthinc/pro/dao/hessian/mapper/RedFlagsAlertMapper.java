package com.inthinc.pro.dao.hessian.mapper;

import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;

public class RedFlagsAlertMapper extends AbstractMapper
{
    @ConvertColumnToField(columnName = "speedSettings")
    public void speedSettingsToModel(RedFlagAlert redFlagAlert, Object value)
    {
        if (redFlagAlert == null || value == null)
            return;

        if (value instanceof String)
        {
            Integer[] speedSettingsArray = new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
            
            String[] list = ((String)value).split(" ");
            for (int i = 0; i < list.length; i++)
            {
                if (i == speedSettingsArray.length)
                    break;
                try
                {
                   speedSettingsArray[i] = Integer.valueOf(list[i]);
                }
                catch (NumberFormatException ex)
                {
                    speedSettingsArray[i] = null;
                }
            }
            
            
            for (int i = list.length; i < speedSettingsArray.length; i++)
            {
                speedSettingsArray[i] = null;
            }
            
            redFlagAlert.setSpeedSettings(speedSettingsArray);
            
        }
    }
    @ConvertFieldToColumn(fieldName = "speedSettings")
    public void speedSettingsToColumn(RedFlagAlert redFlagAlert, Object value)
    {
        if (Map.class.isInstance(value) && (redFlagAlert.getSpeedSettings() != null))
        {
            StringBuffer buf = new StringBuffer();
            for (Integer setting : redFlagAlert.getSpeedSettings())
            {
                if (setting!=null)
                    buf.append(setting);
                buf.append(" ");
            }
            ((Map<String, Object>)value).put("speedSettings", buf.toString());
        }
    }
}
