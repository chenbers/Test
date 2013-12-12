package com.inthinc.pro.dao.service.mapper;

import com.inthinc.pro.model.Trailer;

public class TrailerMapper implements GenericMapper <com.inthinc.pro.dao.service.dto.Trailer, com.inthinc.pro.model.Trailer> {
    
    private AccountMapper accountMapper;
    private DeviceMapper deviceMapper;
    
    private void init() {
        accountMapper = (AccountMapper) MapperFactory.getMapper("Account");
        deviceMapper = (DeviceMapper) MapperFactory.getMapper("Device");
    }
    
    public com.inthinc.pro.dao.service.dto.Trailer mapToDTO(Trailer trailer) {
        
        com.inthinc.pro.dao.service.dto.Trailer dtoTrailer = new com.inthinc.pro.dao.service.dto.Trailer();
        
        init();

        dtoTrailer.setAbsOdometer(trailer.getAbsOdometer());
        dtoTrailer.setAccount(accountMapper.mapToDTO(trailer.getAccount()));
        dtoTrailer.setColor(trailer.getColor());
        dtoTrailer.setDevice(deviceMapper.mapToDTO(trailer.getDevice()));
        dtoTrailer.setMake(trailer.getMake());
        dtoTrailer.setModel(trailer.getModel());
        dtoTrailer.setName(trailer.getName());
        dtoTrailer.setOdometer(trailer.getOdometer());
        dtoTrailer.setPairingDate(trailer.getPairingDate());
        dtoTrailer.setStateID(trailer.getStateID());
        dtoTrailer.setStatus(trailer.getStatus());
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
        trailer.setMake(dtoTrailer.getMake());
        trailer.setModel(dtoTrailer.getModel());
        trailer.setName(dtoTrailer.getName());
        trailer.setOdometer(dtoTrailer.getOdometer());
        trailer.setPairingDate(dtoTrailer.getPairingDate());
        trailer.setStateID(dtoTrailer.getStateID());
        trailer.setStatus(dtoTrailer.getStatus());
        trailer.setTrailerID(dtoTrailer.getTrailerID());
        trailer.setVin(dtoTrailer.getVin());
        trailer.setWeight(dtoTrailer.getWeight());
        trailer.setYear(dtoTrailer.getYear());
        
        return trailer;
    }

}
