package com.inthinc.pro.dao.service.mapper;

public interface GenericMapper<DTO, DAO> {

    public DTO mapToDTO(DAO daoObject);
    public DAO mapFromDTO(DTO dtoObject);

}
