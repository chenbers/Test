package com.inthinc.pro.backing;

import org.springframework.security.AccessDeniedException;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.util.MessageUtil;

public class DriverBean extends BaseBean
{

    private Integer driverID;
    private Driver driver;
    private DriverDAO driverDAO;

    public Integer getDriverID()
    {
        return driverID;
    }

    public void setDriverID(Integer driverID)
    {
        driver = driverDAO.findByID(driverID);
        if (driver == null || getGroupHierarchy().getGroup(driver.getGroupID()) == null)
            throw new AccessDeniedException(MessageUtil.getMessageString("exception_accessDenied", getUser().getLocale()));
        this.driverID = driverID;
    }

    public Driver getDriver()
    {
        return driver;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public DriverDAO getDriverDAO()
    {
        return driverDAO;
    }

    public void setDriverDAO(DriverDAO driverDAO)
    {
        this.driverDAO = driverDAO;
    }
}
