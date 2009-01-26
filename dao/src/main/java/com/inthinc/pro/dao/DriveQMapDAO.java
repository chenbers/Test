package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.DriveQMap;

public interface DriveQMapDAO extends GenericDAO<DriveQMap, Integer>
{

    DriveQMap getDScoreByDT(Integer driverID, Integer duration);
    
    List<DriveQMap> getDTrendByDTC(Integer driverID, Integer duration, Integer count);
    
    DriveQMap getVScoreByVT(Integer vehicleID, Integer duration);
    
    List<DriveQMap> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count);
    
    DriveQMap getGDScoreByGT(Integer groupID, Integer duration);
    
    DriveQMap getGDTrendByGTC(Integer groupID, Integer duration);
    
    DriveQMap getGDScoreByGSE(Integer groupID, Integer start, Integer end);

    DriveQMap getGVScoreByGSE(Integer groupID, Integer start, Integer end);

}