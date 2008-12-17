package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.DriveQMap;

public interface DriveQMapDAO extends GenericDAO<DriveQMap, Integer>
{
    DriveQMap getDScoreByDM(Integer driverID, Integer mileage);

    DriveQMap getDScoreByDT(Integer driverID, Integer duration);
    
    List<DriveQMap> getDTrendByDTC(Integer driverID, Integer duration, Integer count);
    
    List<DriveQMap> getDTrendByDMC(Integer driverID, Integer mileage, Integer count);
    
    DriveQMap getVScoreByVM(Integer vehicleID, Integer mileage);
    
    DriveQMap getVScoreByVT(Integer vehicleID, Integer duration);
    
    List<DriveQMap> getVTrendByVMC(Integer vehicleID, Integer mileage, Integer count);
    
    List<DriveQMap> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count);
    
    DriveQMap getGDScoreByGT(Integer groupID, Integer duration);
    
    DriveQMap getGDTrendByGTC(Integer groupID, Integer duration);
}