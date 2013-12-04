package com.inthinc.pro.dao.service.mapper;

import com.inthinc.pro.dao.service.dto.Vehicle;

public class VehicleMapper implements GenericMapper <com.inthinc.pro.dao.service.dto.Vehicle, com.inthinc.pro.model.Vehicle> {

    @Override
    public Vehicle mapToDTO(com.inthinc.pro.model.Vehicle vehicle) {
        Vehicle dtoVehicle = new Vehicle();
        dtoVehicle.setVehicleID(vehicle.getVehicleID());
        return dtoVehicle;
    }

    @Override
    public com.inthinc.pro.model.Vehicle mapFromDTO(Vehicle dtoVehicle) {
        
        com.inthinc.pro.model.Vehicle vehicle = new com.inthinc.pro.model.Vehicle();
        vehicle.setVehicleID(dtoVehicle.getVehicleID());
        return vehicle;
    }

}
