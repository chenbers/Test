package com.inthinc.pro.dao.service.mapper;

import com.inthinc.pro.dao.service.dto.Driver;

public class DriverMapper implements GenericMapper <com.inthinc.pro.dao.service.dto.Driver, com.inthinc.pro.model.Driver> {

    @Override
    public Driver mapToDTO(com.inthinc.pro.model.Driver driver) {
        Driver dtoDriver = new Driver();
        dtoDriver.setDriverID(driver.getDriverID());
        return dtoDriver;
    }

    @Override
    public com.inthinc.pro.model.Driver mapFromDTO(Driver dtoDriver) {
        
        com.inthinc.pro.model.Driver driver = new com.inthinc.pro.model.Driver();
        driver.setDriverID(dtoDriver.getDriverID());
        return driver;
    }

}
