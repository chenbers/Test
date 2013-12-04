package com.inthinc.pro.dao.service.mapper;

import com.inthinc.pro.model.Trailer;

public class TrailerMapper implements GenericMapper <com.inthinc.pro.dao.service.dto.Trailer, com.inthinc.pro.model.Trailer> {
    
    private AccountMapper accountMapper;
    private DeviceMapper deviceMapper;
    private DriverMapper driverMapper;
    private GroupMapper groupMapper;
    private VehicleMapper vehicleMapper;
    
    private void init() {
        accountMapper = (AccountMapper) MapperFactory.getMapper("Account");
        deviceMapper = (DeviceMapper) MapperFactory.getMapper("Device");
        driverMapper = (DriverMapper) MapperFactory.getMapper("Driver");
        groupMapper = (GroupMapper) MapperFactory.getMapper("Group");
        vehicleMapper = (VehicleMapper) MapperFactory.getMapper("Vehicle");
    }
    
    public com.inthinc.pro.dao.service.dto.Trailer mapToDTO(Trailer trailer) {
        
        com.inthinc.pro.dao.service.dto.Trailer dtoTrailer = new com.inthinc.pro.dao.service.dto.Trailer();
        
        init();

        dtoTrailer.setAbsOdometer(trailer.getAbsOdometer());
        dtoTrailer.setAccount(accountMapper.mapToDTO(trailer.getAccount()));
        dtoTrailer.setColor(trailer.getColor());
        dtoTrailer.setDevice(deviceMapper.mapToDTO(trailer.getDevice()));
        dtoTrailer.setDriver(driverMapper.mapToDTO(trailer.getDriver()));
        dtoTrailer.setGroup(groupMapper.mapToDTO(trailer.getGroup()));
        dtoTrailer.setGroupPath(trailer.getGroupPath());
        dtoTrailer.setMake(trailer.getMake());
        dtoTrailer.setModel(trailer.getModel());
        dtoTrailer.setName(trailer.getName());
        dtoTrailer.setOdometer(trailer.getOdometer());
        dtoTrailer.setPairingDate(trailer.getPairingDate());
        dtoTrailer.setStateID(trailer.getStateID());
        dtoTrailer.setStatus(trailer.getStatus());
        dtoTrailer.setVehicle(vehicleMapper.mapToDTO(trailer.getVehicle()));
        dtoTrailer.setVin(trailer.getVin());
        dtoTrailer.setWeight(trailer.getWeight());
        dtoTrailer.setYear(trailer.getYear());
        
        return dtoTrailer;
    }
    
    public Trailer mapFromDTO(com.inthinc.pro.dao.service.dto.Trailer dtoTrailer) {
        
        Trailer trailer = new Trailer();
        
        init();

        trailer.setAbsOdometer(dtoTrailer.getAbsOdometer());
        trailer.setAccount(accountMapper.mapFromDTO(dtoTrailer.getAccount()));
        trailer.setColor(dtoTrailer.getColor());
        trailer.setDevice(deviceMapper.mapFromDTO(dtoTrailer.getDevice()));
        trailer.setDriver(driverMapper.mapFromDTO(dtoTrailer.getDriver()));
        trailer.setGroup(groupMapper.mapFromDTO(dtoTrailer.getGroup()));
        trailer.setGroupPath(dtoTrailer.getGroupPath());
        trailer.setMake(dtoTrailer.getMake());
        trailer.setModel(dtoTrailer.getModel());
        trailer.setName(dtoTrailer.getName());
        trailer.setOdometer(dtoTrailer.getOdometer());
        trailer.setPairingDate(dtoTrailer.getPairingDate());
        trailer.setStateID(dtoTrailer.getStateID());
        trailer.setStatus(dtoTrailer.getStatus());
        trailer.setTrailerID(dtoTrailer.getTrailerID());
        trailer.setVehicle(vehicleMapper.mapFromDTO(dtoTrailer.getVehicle()));
        trailer.setVin(dtoTrailer.getVin());
        trailer.setWeight(dtoTrailer.getWeight());
        trailer.setYear(dtoTrailer.getYear());
        
        return trailer;
    }

}
