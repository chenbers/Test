package com.inthinc.pro.dao.hessian.mapper;

import java.util.ArrayList;
import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;

@SuppressWarnings("serial")
public class RedFlagAlertMapper extends AbstractMapper
{
    private static final String MAX_SPEED_MARKER = "~";
    
    @ConvertColumnToField(columnName = "speedSettings")
    public void speedSettingsToModel(RedFlagAlert redFlagAlert, Object value)
    {
        // speedSetting is either a space delimited list of speeds per speed limit or a single max speed value preceded by a tilda 
        if (redFlagAlert == null || value == null)
            return;

        if (value instanceof String)
        {
            String valueStr = (String)value;
            redFlagAlert.setUseMaxSpeed(valueStr.startsWith(MAX_SPEED_MARKER));
            if (redFlagAlert.getUseMaxSpeed()) {
                try {
                    redFlagAlert.setMaxSpeed(Integer.valueOf(valueStr.substring(1).trim()));
                }
                catch (NumberFormatException ex) {
                    redFlagAlert.setMaxSpeed(null);
                }
                return;
                
            }
                
            
            
            Integer[] speedSettingsArray = new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
            
            String[] list = valueStr.split(" ");
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
    @SuppressWarnings("unchecked")
    @ConvertFieldToColumn(fieldName = "speedSettings")
    public void speedSettingsToColumn(RedFlagAlert redFlagAlert, Object value)
    {
        if (Map.class.isInstance(value) && redFlagAlert.getSpeedSettings() != null && !redFlagAlert.getUseMaxSpeed())
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
    
    @ConvertFieldToColumn(fieldName = "maxSpeed")
    public void maxSpeedToColumn(RedFlagAlert redFlagAlert, Object value)
    {
        if (Map.class.isInstance(value) && redFlagAlert.getMaxSpeed() != null && redFlagAlert.getUseMaxSpeed())
        {
            StringBuffer buf = new StringBuffer(MAX_SPEED_MARKER);
            buf.append(redFlagAlert.getMaxSpeed());
            ((Map<String, Object>)value).put("speedSettings", buf.toString());
        }
    }

    @SuppressWarnings("unchecked")
    @ConvertFieldToColumn(fieldName = "types")
    public void alertTypeMaskToColumn(RedFlagAlert redFlagAlert, Object value)
    {
        if (Map.class.isInstance(value) && (redFlagAlert.getTypes() != null))
        {
            Long alertTypeMask = AlertMessageType.convertTypes(redFlagAlert.getTypes());
            ((Map<String, Object>)value).put("alertTypeMask", alertTypeMask);
        }
    }
    @ConvertColumnToField(columnName = "alertTypeMask")
    public void alertTypeMaskToModel(RedFlagAlert redFlagAlert, Object value)
    {
        if (redFlagAlert == null || value == null)
            return;

        if (value instanceof Long)
        {
            redFlagAlert.setTypes(new ArrayList<AlertMessageType>(AlertMessageType.getAlertMessageTypes((Long)value)));
        }
    }
}
