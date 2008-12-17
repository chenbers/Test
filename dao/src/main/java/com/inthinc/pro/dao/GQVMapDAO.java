package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.GQVMap;

public interface GQVMapDAO extends GenericDAO<GQVMap, Integer>
{
    List<GQVMap> getSDScoresByGT(Integer groupID, Integer duration);
    
    List<GQVMap> getSDTrendsByGTC(Integer groupID, Integer duration, Integer metric);
}
