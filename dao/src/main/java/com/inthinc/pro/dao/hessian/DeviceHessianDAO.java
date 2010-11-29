package com.inthinc.pro.dao.hessian;

import java.util.Collections;
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
import com.inthinc.pro.model.ForwardCommandStatus;

@SuppressWarnings("serial")
public class DeviceHessianDAO extends GenericHessianDAO<Device, Integer> implements DeviceDAO, FindByKey<Device>
{
    private static final Logger logger = Logger.getLogger(DeviceHessianDAO.class);
    private static final String CENTRAL_ID_KEY = "imei";
    private static final String SERIAL_NUM_KEY = "serialNum";

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
            return deviceList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    
    @Override
    public Device findByKey(String key)
    {
        Device device = findByIMEI(key);
        if (device==null)
        	device = findBySerialNum(key);
        return device;
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
            
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
    @Override
    public Device findBySerialNum(String serialNum)
    {
        
        try
        {
            Map<String, Object> returnMap = getSiloService().getID(SERIAL_NUM_KEY, serialNum);
            Integer deviceId = getCentralId(returnMap);
            return findByID(deviceId);
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
