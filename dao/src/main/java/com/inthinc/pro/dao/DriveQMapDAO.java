package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.DriveQMap;

public interface DriveQMapDAO extends GenericDAO<DriveQMap, Integer>
{

    DriveQMap getDScoreByDT(Integer driverID, Integer duration);
    
    List<DriveQMap> getDTrendByDTC(Integer driverID, Integer duration, Integer count);
    
    DriveQMap getVScoreByVT(Integer vehicleID, Integer duration);
    
    List<DriveQMap> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count);
    
    DriveQMap getGDScoreByGT(Integer groupID, Integer duration);
    
    List<DriveQMap> getGDTrendByGTC(Integer groupID, Integer duration, Integer count);
    
    DriveQMap getGDScoreByGSE(Integer groupID, Date start, Date end);

    DriveQMap getGVScoreByGSE(Integer groupID, Date start, Date end);

}