package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.DeviceMapper;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.SensitivityForwardCommandMapping;
import com.inthinc.pro.model.SensitivityType;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;

public class DeviceHessianDAO extends GenericHessianDAO<Device, Integer> implements DeviceDAO, FindByKey<Device>
{
    private static final Logger logger = Logger.getLogger(DeviceHessianDAO.class);
    private static final String CENTRAL_ID_KEY = "mcmid";

    private DeviceMapper deviceMapper;
    private VehicleDAO vehicleDAO;
    public DeviceHessianDAO()
    {
        super();
        deviceMapper = new DeviceMapper();
        super.setMapper(deviceMapper);
    }
    
    @Override
    public List<Device> getDevicesByAcctID(Integer accountID)
    {
        try
        {
            List<Device> deviceList = getMapper().convertToModelObject(getSiloService().getDevicesByAcctID(accountID), Device.class);
            for (Device device : deviceList)
            {
                checkForPendingDeviceSensitivity(device);
            }
            return deviceList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    @Override
    public Device findByID(Integer id)
    {
        Device device = super.findByID(id);
        if (device != null)
        {
            checkForPendingDeviceSensitivity(device);
        }
        return device;
    }
    
    @Override
    public Device findByKey(String key)
    {
        return findByIMEI(key);
    }
    
    @Override
    public Device findByIMEI(String imei)
    {
        
        // TODO: it can take up to 5 minutes from when a user record is added until
        // it can be accessed via getID().   Should this method account for that?
        try
        {
            Map<String, Object> returnMap = getSiloService().getID(CENTRAL_ID_KEY, imei);
            Integer deviceId = getCentralId(returnMap);
            return findByID(deviceId);
            
//            the following was invoking checkForPendingDeviceSensitivity a second time. It was previously invoked form the findByID method

//            Device device =  findByID(deviceId);
//            if (device != null)
//            {
//                checkForPendingDeviceSensitivity(device);
//            }
//            return device;
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
    @Override
    public List<ForwardCommand> getForwardCommands(Integer deviceID, ForwardCommandStatus status)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getFwdCmds(deviceID, status.getCode()), ForwardCommand.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public Integer queueForwardCommand(Integer deviceID, ForwardCommand forwardCommand)
    {
        logger.debug("queueForwardCommand deviceID: " + deviceID + " fwdcmdID =" + forwardCommand.getCmd() + " data = " + ((forwardCommand.getData() == null) ? "<null>" : forwardCommand.getData().toString()));
        return getChangedCount(getSiloService().queueFwdCmd(deviceID, getMapper().convertToMap(forwardCommand)));
    }
    
    @Override
    public Map<SensitivityType, SensitivityForwardCommandMapping> getSensitivityForwardCommandMapping()
    {
        Map<SensitivityType, SensitivityForwardCommandMapping> returnMap = new HashMap<SensitivityType, SensitivityForwardCommandMapping>();
        List<SensitivityForwardCommandMapping> list = getMapper().convertToModelObject(getSiloService().getSensitivityMaps(), SensitivityForwardCommandMapping.class);
        for (SensitivityForwardCommandMapping s : list)
        {
            returnMap.put(s.getSetting(), s);
        }
        return returnMap;
    }
    
    @Override
    public Integer create(Integer id, Device device)
    {
        
         Integer deviceID = super.create(id, device);
         
         // sensitivity
         if (device.getHardAcceleration() != null)
         {
             queueForwardCommand(deviceID, DeviceSensitivityMapping.getForwardCommand(SensitivityType.HARD_ACCEL_SETTING, device.getHardAcceleration()));
         }
         if (device.getHardBrake() != null)
         {
             queueForwardCommand(deviceID, DeviceSensitivityMapping.getForwardCommand(SensitivityType.HARD_BRAKE_SETTING, device.getHardBrake()));
         }
         if (device.getHardTurn() != null)
         {
             queueForwardCommand(deviceID, DeviceSensitivityMapping.getForwardCommand(SensitivityType.HARD_TURN_SETTING, device.getHardTurn()));
         }
         if (device.getHardVertical() != null)
         {
             queueForwardCommand(deviceID, DeviceSensitivityMapping.getForwardCommand(SensitivityType.HARD_VERT_SETTING, device.getHardVertical()));
         }
         
         // speed settings
         if (device.getSpeedSettings() != null)
         {
             queueForwardCommand(deviceID, new ForwardCommand(0, ForwardCommandID.SET_SPEED_ALARMS, speedSettingsStr(device.getSpeedSettings()), ForwardCommandStatus.STATUS_QUEUED));
         }
         
         // ECALL
         if (device.getEphone() != null)
         {
             queueForwardCommand(deviceID, new ForwardCommand(0, ForwardCommandID.SET_CALL_NUMBER, filterPhoneNumber(device.getEphone()), ForwardCommandStatus.STATUS_QUEUED));
             queueForwardCommand(deviceID, new ForwardCommand(0, ForwardCommandID.ADD_VALID_CALLER, "1 " + filterPhoneNumber(device.getEphone()), ForwardCommandStatus.STATUS_QUEUED));
         }
         
         
         return deviceID;
    }

    @Override
    public Integer update(Device device)
    {
        
        // overridden so we can queue up correct forward commands
        try
        {
            Device originalDevice = findByID(device.getDeviceID());
         
            // sensitivity
            if (!originalDevice.getHardAcceleration().equals(device.getHardAcceleration()))
            {
                queueForwardCommand(device.getDeviceID(), DeviceSensitivityMapping.getForwardCommand(SensitivityType.HARD_ACCEL_SETTING, device.getHardAcceleration()));
            }
            if (!originalDevice.getHardBrake().equals(device.getHardBrake()))
            {
                queueForwardCommand(device.getDeviceID(), DeviceSensitivityMapping.getForwardCommand(SensitivityType.HARD_BRAKE_SETTING, device.getHardBrake()));
            }
            if (!originalDevice.getHardTurn().equals(device.getHardTurn()))
            {
                queueForwardCommand(device.getDeviceID(), DeviceSensitivityMapping.getForwardCommand(SensitivityType.HARD_TURN_SETTING, device.getHardTurn()));
            }
            if (!originalDevice.getHardVertical().equals(device.getHardVertical()))
            {
                queueForwardCommand(device.getDeviceID(), DeviceSensitivityMapping.getForwardCommand(SensitivityType.HARD_VERT_SETTING, device.getHardVertical()));
            }
            
            // speed settings
            Integer originalSettings[] = originalDevice.getSpeedSettings();
            Integer newSettings[] = device.getSpeedSettings();
            if (speedSettingDiffer(originalSettings, newSettings))
            {
                queueForwardCommand(device.getDeviceID(), new ForwardCommand(0, ForwardCommandID.SET_SPEED_ALARMS, speedSettingsStr(device.getSpeedSettings()), ForwardCommandStatus.STATUS_QUEUED));
            }
            
            // ECALL
            if (!filterPhoneNumber(originalDevice.getEphone()).equals(filterPhoneNumber(device.getEphone())))
            {
                queueForwardCommand(device.getDeviceID(), new ForwardCommand(0, ForwardCommandID.SET_CALL_NUMBER, filterPhoneNumber(device.getEphone()), ForwardCommandStatus.STATUS_QUEUED));
                queueForwardCommand(device.getDeviceID(), new ForwardCommand(0, ForwardCommandID.ADD_VALID_CALLER, "1 " + filterPhoneNumber(device.getEphone()), ForwardCommandStatus.STATUS_QUEUED));
            }
            
        }
        catch (Exception e)
        {
            logger.error("Unable to queue forward commands for deviceID: " + device.getDeviceID() + " on update", e);
        }
        
        return super.update(device);
    }

    private String speedSettingsStr(Integer[] speedSettings)
    {
        int baseSpeed = 5;
        StringBuilder sb = new StringBuilder();
        for (Integer speed : speedSettings)
        {
            if(speed == null)
                speed = 0;
            if (sb.length() > 0)
                sb.append(' ');
            sb.append(baseSpeed+speed);
            baseSpeed+=5;
        }
        return sb.toString();
    }

    private String filterPhoneNumber(String s)
    {
        if (s == null)
            return "";
        StringBuilder digitsOnly = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++)
        {
            c = s.charAt(i);
            if (Character.isDigit(c))
            {
                digitsOnly.append(c);
            }
        }
        return digitsOnly.toString();
    }

    private boolean speedSettingDiffer(Integer[] originalSpeedSettings, Integer[] speedSettings)
    {
        if (speedSettings.length != originalSpeedSettings.length)
            return true;
        for (int i = 0; i < speedSettings.length; i++)
        {
            if (!speedSettings[i].equals(originalSpeedSettings[i]))
                return true;
        }
        return false;
    }

    // checks for queued sensitivity forward commands and sets the device record from these
    private void checkForPendingDeviceSensitivity(Device device)
    {
        List<ForwardCommand> fwdCmdQueue = getForwardCommands(device.getDeviceID(), ForwardCommandStatus.STATUS_QUEUED);
        
        for (ForwardCommand fwdCmd : fwdCmdQueue)
        {
            SensitivityType sensitivityType = DeviceSensitivityMapping.getSensitivityType(fwdCmd);
            if (sensitivityType == null)
                continue;
            
            if (sensitivityType.equals(SensitivityType.HARD_ACCEL_SETTING))
            {
                device.setHardAcceleration(deviceMapper.parseLevel(fwdCmd.getData()));
            }
            if (sensitivityType.equals(SensitivityType.HARD_BRAKE_SETTING))
            {
                device.setHardBrake(deviceMapper.parseLevel(fwdCmd.getData()));
            }
            if (sensitivityType.equals(SensitivityType.HARD_TURN_SETTING))
            {
                device.setHardTurn(deviceMapper.parseLevel(fwdCmd.getData()));
            }
            if (sensitivityType.equals(SensitivityType.HARD_VERT_SETTING))
            {
                device.setHardVertical(deviceMapper.parseLevel(fwdCmd.getData()));
            }
        }

    }
    
    @Override
    public Integer deleteByID(Integer id)
    {
        Device device = findByID(id);
        if(device.getVehicleID() != null)
            vehicleDAO.clearVehicleDevice(device.getVehicleID(), id);
        return super.deleteByID(id);
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO)
    {
        this.vehicleDAO = vehicleDAO;
    }

    public VehicleDAO getVehicleDAO()
    {
        return vehicleDAO;
    }

}
