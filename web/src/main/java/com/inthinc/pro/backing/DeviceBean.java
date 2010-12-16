package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.Vehicle;

public class DeviceBean extends BaseBean implements IdentifiableEntityBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DeviceBean.class);

    private Integer deviceID;
    private Device device;
    private DeviceDAO deviceDAO;
    private Boolean waysmart;

    public DeviceBean() {
        super();
    }

    public DeviceBean(Device device) {
        this.deviceID = device.getDeviceID();
        this.device = device;
    }

    public DeviceBean(Integer deviceID) {
        if (null != deviceID)
            this.device = deviceDAO.findByID(deviceID);
    }

    public DeviceBean(Vehicle vehicle) {
        if (null != vehicle) {
            if (null != vehicle.getDeviceID()) {
                this.deviceID = vehicle.getDeviceID();
                this.device = deviceDAO.findByID(vehicle.getDeviceID());
            }
        }
    }

    public void loadDeviceBean() {
        if (null != this.deviceID) {
            this.device = deviceDAO.findByID(this.deviceID);
        }
    }

    public void loadDeviceBean(Vehicle vehicle) {
        if (null != vehicle) {
            if (null != vehicle.getDeviceID()) {
                this.deviceID = vehicle.getDeviceID();
                this.device = deviceDAO.findByID(vehicle.getDeviceID());
            }
        }
    }

    public void requestGPSLocation() {
        Integer deviceID = getDeviceID();
        Device device;
        if (null != deviceID) {
            device = deviceDAO.findByID(deviceID);
            if (null != device && device.isWaySmart()) {
                ForwardCommand fwdCmd = new ForwardCommand(0, ForwardCommandID.GET_GPS_GET_LOCATION, 0, ForwardCommandStatus.STATUS_QUEUED);
                Integer cmdCount = deviceDAO.queueForwardCommand(deviceID, fwdCmd);
                //TODO: alert page that there are <code>cmdCount</code> fwdCmds queued for the device in question???
            }
        } else {
            // TODO: alert page that there was a problem
        }
    }

    public boolean isWaysmart() {
        if(waysmart == null) {
            if (null != deviceID) {
                if(null == device){
                    device = deviceDAO.findByID(deviceID);
                }
                waysmart = (null != device && device.isWaySmart());
            }
        }
        if(waysmart == null) return false;
        return waysmart;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.device = deviceDAO.findByID(deviceID);
        this.deviceID = deviceID;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public DeviceDAO getDeviceDAO() {
        return deviceDAO;
    }

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENTITY_DEVICE;
    }

    @Override
    public Integer getId() {
        return getDeviceID();
    }

    @Override
    public String getName() {
        if (device != null) {
            return device.getName();
        } else {
            return null;
        }
    }

    @Override
    public void setId(Integer id) {
        setDeviceID(id);
    }

    @Override
    public Object getEntity() {
        return device;
    }

    @Override
    public String getLongName() {
        if (device != null) {
            return device.getName() + " - " + device.getFirmwareVersion();
            // TODO: jwimmer: not sure what intention of getLongName is, or IF/WHEN/WHERE it would get used ?
        }
        return null;
    }

    @Override
    public int compareTo(IdentifiableEntityBean o) {
        return o != null ? this.getLongName().compareTo(o.getLongName()) : 0;
    }
}
