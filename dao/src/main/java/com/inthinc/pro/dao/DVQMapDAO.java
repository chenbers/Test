package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;

public interface DVQMapDAO extends GenericDAO<DVQMap, Integer>
{   
    List<DVQMap> getDVScoresByGT(Integer groupID, Integer duration);
    
    List<DVQMap> getVDScoresByGT(Integer groupID, Integer duration);
    
    List<DVQMap> getSDScoresByGT(Integer groupID, Integer duration);
    
    List<DVQMap> getSDTrendsByGTC(Integer groupID, Integer duration, Integer metric);
    
}
