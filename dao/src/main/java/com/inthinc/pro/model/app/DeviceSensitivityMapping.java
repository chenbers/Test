package com.inthinc.pro.model.app;

import java.util.List;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.SensitivityForwardCommandMapping;


public class DeviceSensitivityMapping implements BaseAppEntity
{
	private static final long serialVersionUID = 1L;
	
	private List<SensitivityForwardCommandMapping> sensitivityMapping;
	
	private DeviceDAO deviceDAO;

	public DeviceSensitivityMapping()
	{
		super();
	}
	
	public void init()
	{
		sensitivityMapping = deviceDAO.getSensitivityForwardCommandMapping();
	}

	public ForwardCommand getForwardCommand(Integer setting, Integer level)
	{
		SensitivityForwardCommandMapping mapping = sensitivityMapping.get(setting);

        return new ForwardCommand(0, mapping.getFwdCmd(), level, ForwardCommandStatus.STATUS_QUEUED);
    }

	public Integer getForwardCommandCmdID(Integer setting)
    {
        SensitivityForwardCommandMapping mapping = sensitivityMapping.get(setting);
        return mapping.getFwdCmd();
    }
/*    
    public Integer getIndex(Integer setting, String level)
    {
        // return index 1 to 5
        SensitivityForwardCommandMapping mapping = sensitivityMapping.get(setting);
        for (Integer index = 0; index < mapping.getValues().size(); index++)
        {
            if (level >= mapping.getValues().get(index))
            {
                return index+1;
            }
        }
        return 1;
    }
    public Integer getLevel(Integer setting, Integer index)
    {
        // map index 1 to 5 to it's level
        SensitivityForwardCommandMapping mapping = sensitivityMapping.get(setting);
        if (index < 1 || index > 5)
        {
            return mapping.getValues().get(0);
        }
        return mapping.getValues().get(index-1);
    }
*/

    public DeviceDAO getDeviceDAO()
    {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO)
    {
        this.deviceDAO = deviceDAO;
    }    
}
