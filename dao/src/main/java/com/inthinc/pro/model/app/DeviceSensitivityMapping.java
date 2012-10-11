package com.inthinc.pro.model.app;

import java.util.Map;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.SensitivityForwardCommandMapping;
import com.inthinc.pro.model.configurator.SettingType;


public class DeviceSensitivityMapping implements BaseAppEntity
{
	private static final long serialVersionUID = 1L;
	
	private static Map<SettingType, SensitivityForwardCommandMapping> sensitivityMapping;
	
	private DeviceDAO deviceDAO;

	public DeviceSensitivityMapping()
	{
		super();
	}
	
	public void init()
	{
//		sensitivityMapping = deviceDAO.getSensitivityForwardCommandMapping();
	}

	public Integer getForwardCommandCmdID(SettingType setting)
    {
        SensitivityForwardCommandMapping mapping = sensitivityMapping.get(setting);
        return mapping.getFwdCmd();
    }
	
	public static ForwardCommand getForwardCommand(SettingType setting, Integer level)
	{
	    String levelStr = level.toString();
        SensitivityForwardCommandMapping mapping = sensitivityMapping.get(setting);
        
        if(SettingType.SEVERE_PEAK_2_PEAK == setting)
            return new ForwardCommand(0,mapping.getFwdCmd(),Integer.valueOf(mapping.getValues().get(level-1)),ForwardCommandStatus.STATUS_QUEUED);
        
        for (String value : mapping.getValues())
        {
            if (value.endsWith(levelStr))
            {
                return new ForwardCommand(0, mapping.getFwdCmd(), value, ForwardCommandStatus.STATUS_QUEUED);
            }
        }
        return null;
	}

	public static SettingType getSensitivityType(ForwardCommand fwdCmd)
    {
        for (SensitivityForwardCommandMapping mapping : sensitivityMapping.values())
        {
            if (mapping.getFwdCmd().equals(fwdCmd.getCmd()))
            {
                return mapping.getSetting();
            }
        }
        return null;
    }

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }    
}
