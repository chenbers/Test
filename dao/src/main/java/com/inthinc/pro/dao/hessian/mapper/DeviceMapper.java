package com.inthinc.pro.dao.hessian.mapper;

import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.configurator.SettingType;

public class DeviceMapper extends AbstractMapper
{
    
//    @ConvertColumnToField(columnName = "accel")
//    public void accelToModel(Device device, Object value)
//    {
//        if (device == null)
//            return;
//        
//        if (value == null)
//        {
//            device.setHardAcceleration(SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting());
//        }
//            
//        if (value instanceof String)
//        {
//            device.setHardAcceleration(parseLevel(value));
//
//        }
//    }
//    @ConvertColumnToField(columnName = "brake")
//    public void brakeToModel(Device device, Object value)
//    {
//        if (device == null)
//            return;
//        
//        if (value == null)
//        {
//            device.setHardBrake(SensitivityType.HARD_BRAKE_SETTING.getDefaultSetting());
//        }
//
//        if (value instanceof String)
//        {
//            device.setHardBrake(parseLevel(value));
//
//        }
//    }
//    @ConvertColumnToField(columnName = "turn")
//    public void turnToModel(Device device, Object value)
//    {
//        if (device == null)
//            return;
//        
//        if (value == null)
//        {
//            device.setHardTurn(SensitivityType.HARD_TURN_SETTING.getDefaultSetting());
//        }
//
//        if (value instanceof String)
//        {
//            device.setHardTurn(parseLevel(value));
//
//        }
//    }
//    @ConvertColumnToField(columnName = "vert")
//    public void vertToModel(Device device, Object value)
//    {
//        if (device == null)
//            return;
//        
//        if (value == null)
//        {
//            device.setHardVertical(SensitivityType.HARD_VERT_SETTING.getDefaultSetting());
//        }
//
//        if (value instanceof String)
//        {
//            device.setHardVertical(parseLevel(value));
//
//        }
//    }
//
//    public Integer parseLevel(Object value)
//    {
//        String[] comp = ((String) value).split(" ");
//        Integer level = null;
//        if (comp.length == 3)
//        {
//            try
//            {
//                level = Integer.valueOf(comp[2]);
//            }
//            catch (NumberFormatException ex)
//            {
//                level = SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting();
//            }
//        }
//        else
//        {
//            level = SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting();
//        }
//        return level;
//    }
//
//    @ConvertColumnToField(columnName = "speedSet")
//    public void speedSetToModel(Device device, Object value)
//    {
//        if (device == null || value == null)
//            return;
//
//        if (value instanceof String)
//        {
//            String[] speeds = ((String)value).split(" ");
//            Integer[] speedSettings = new Integer[Device.NUM_SPEEDS];
//            for (int i = 0; i < speeds.length; i++)
//            {
//                try
//                {
//                    speedSettings[i] = new Integer(speeds[i]);
//                }
//                catch (NumberFormatException e)
//                {
//                    speedSettings[i] = Device.DEFAULT_SPEED_SETTING;
//                }
//            }
//            for (int i = speeds.length; i < Device.NUM_SPEEDS; i++)
//            {
//                speedSettings[i] = Device.DEFAULT_SPEED_SETTING;
//            }
//            
//            device.setSpeedSettings(speedSettings);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    @ConvertFieldToColumn(fieldName = "speedSet")
//    public void speedSetToColumn(Device device, Object value)
//    {
//        if (Map.class.isInstance(value))
//        {
//            ((Map<String, Object>)value).put("speedSet", speedSettingsArrayToSpeedSetStr(device.getSpeedSettings()));
//        }
//    }
//    
//    public String speedSettingsArrayToSpeedSetStr(Integer[] speedSettings)
//    {
//        if ((speedSettings == null) || (speedSettings.length == 0) || speedSettings.length != Device.NUM_SPEEDS)
//            return Device.DEFAULT_SPEED_SET;
//        
//        StringBuilder sb = new StringBuilder();
//        for (Integer speed : speedSettings)
//        {
//            if (sb.length() > 0)
//                sb.append(' ');
//            sb.append(speed);
//        }
//        return sb.toString();
//    }

    
 
}
