package com.inthinc.pro.dao.jdbc;

import java.io.Serializable;
import java.util.List;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.ForwardCommandSpool;

public class FwdCmdSpoolWS implements Serializable {
    private static final long serialVersionUID = 1L;
    private FwdCmdSpoolWSIridiumJDBCDAO fwdCmdSpoolWSIridiumJDBCDAO; 
    private FwdCmdSpoolWSHttpJDBCDAO fwdCmdSpoolWSHttpJDBCDAO;
    
    public Integer add(Device device, ForwardCommandSpool forwardCommandSpool) {
        if (isGPRSOnlyWaysmart(device)) {
            return fwdCmdSpoolWSHttpJDBCDAO.add(forwardCommandSpool);
        }
        return fwdCmdSpoolWSIridiumJDBCDAO.add(forwardCommandSpool);
    }

    public List<ForwardCommandSpool> getForDevice(Integer deviceID, List<Integer> cmdIDList) {
        List<ForwardCommandSpool> satList = fwdCmdSpoolWSIridiumJDBCDAO.getForDevice(deviceID, cmdIDList);
        List<ForwardCommandSpool> wifiList = fwdCmdSpoolWSHttpJDBCDAO.getForDevice(deviceID, cmdIDList);
        satList.addAll(wifiList);
        return satList;
    }

    public void update(Device device, Integer fwdID, Boolean processedSuccessfully) {
        if (isGPRSOnlyWaysmart(device)) {
            fwdCmdSpoolWSHttpJDBCDAO.update(fwdID, processedSuccessfully);
        }
        else fwdCmdSpoolWSIridiumJDBCDAO.update(fwdID, processedSuccessfully);
    }
    
    private boolean isGPRSOnlyWaysmart(Device device) {
        return device.isWaySmart() && device.isGPRSOnly();
    }

    public FwdCmdSpoolWSIridiumJDBCDAO getFwdCmdSpoolWSIridiumJDBCDAO() {
        return fwdCmdSpoolWSIridiumJDBCDAO;
    }

    public void setFwdCmdSpoolWSIridiumJDBCDAO(FwdCmdSpoolWSIridiumJDBCDAO fwdCmdSpoolWSIridiumJDBCDAO) {
        this.fwdCmdSpoolWSIridiumJDBCDAO = fwdCmdSpoolWSIridiumJDBCDAO;
    }

    public FwdCmdSpoolWSHttpJDBCDAO getFwdCmdSpoolWSHttpJDBCDAO() {
        return fwdCmdSpoolWSHttpJDBCDAO;
    }

    public void setFwdCmdSpoolWSHttpJDBCDAO(FwdCmdSpoolWSHttpJDBCDAO fwdCmdSpoolWSHttpJDBCDAO) {
        this.fwdCmdSpoolWSHttpJDBCDAO = fwdCmdSpoolWSHttpJDBCDAO;
    }

}


