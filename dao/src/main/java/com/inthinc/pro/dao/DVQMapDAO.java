package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.QuintileMap;

public interface DVQMapDAO extends GenericDAO<DVQMap, Integer>
{   
    List<DVQMap> getDVScoresByGT(Integer groupID, Integer duration);
    
    List<DVQMap> getVDScoresByGT(Integer groupID, Integer duration);
    
    List<DVQMap> getSDScoresByGT(Integer groupID, Integer duration);
        
    QuintileMap getDPctByGT(Integer groupID, Integer duration, Integer metric);
    
    List<DVQMap> getDVScoresByGSE(Integer groupID, Date start, Date end);
}
