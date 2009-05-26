package com.inthinc.pro.backing;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;

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
