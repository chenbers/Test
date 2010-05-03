package com.inthinc.pro.backing.dao.mapper;

import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;

public class DaoUtilRedFlagAlertMapper extends DaoUtilMapper {
	
    @ConvertColumnToField(columnName = "speedSettings")
    public void speedSettingsToModel(RedFlagAlert redFlagAlert, Object value)
    {
        if (redFlagAlert == null || value == null)
            return;

        if (value instanceof String)
        {
            Integer[] speedSettingsArray = new Integer[15];
            
            String[] list = ((String)value).split(" ");
            for (int i = 0; i < list.length; i++)
            {
                if (i == 15)
                    break;
                try
                {
                   speedSettingsArray[i] = Integer.valueOf(list[i]);
                }
                catch (NumberFormatException ex)
                {
                    speedSettingsArray[i] = 0;
                }
            }
            
            
            for (int i = list.length; i < 15; i++)
            {
                speedSettingsArray[i] = 0;
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
                buf.append(setting + " ");
            }
            ((Map<String, Object>)value).put("speedSettings", buf.toString());
        }
    }


    @ConvertColumnToField(columnName = "speedLevels")
    public void speedLevelsToModel(RedFlagAlert redFlagAlert, Object value)
    {
        if (redFlagAlert == null || value == null)
            return;

        if (value instanceof String)
        {
            RedFlagLevel[] speedLevelsArray = new RedFlagLevel[15];
            
            String[] list = ((String)value).split(" ");
            for (int i = 0; i < list.length; i++)
            {
                if (i == 15)
                    break;
                try
                {
                    speedLevelsArray[i] = RedFlagLevel.valueOf(Integer.valueOf(list[i]));
                }
                catch (NumberFormatException ex)
                {
                    speedLevelsArray[i] = RedFlagLevel.NONE;
                }
            }
            
            
            for (int i = list.length; i < 15; i++)
            {
                speedLevelsArray[i] = RedFlagLevel.NONE;
            }
            
            redFlagAlert.setSpeedLevels(speedLevelsArray);
            
        }
    }

    @ConvertFieldToColumn(fieldName = "speedLevels")
    public void speedLevelsToColumn(RedFlagAlert redFlagAlert, Object value)
    {
        if (Map.class.isInstance(value) && (redFlagAlert.getSpeedLevels() != null))
        {
            StringBuffer buf = new StringBuffer();
            for (RedFlagLevel level : redFlagAlert.getSpeedLevels())
            {
                buf.append(level.getCode() + " ");
            }
            ((Map<String, Object>)value).put("speedLevels", buf.toString());
        }
    }

}
