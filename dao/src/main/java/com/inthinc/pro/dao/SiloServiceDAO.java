package com.inthinc.pro.dao;

import java.util.List;
import java.util.Map;

// wrapper interface for all SiloService interface methods that do not have an equivalent implementation in the other DAOs

public interface SiloServiceDAO {


    List<Map<String, Object>> getGroupsByGroupIDDeep(Integer groupID);
    Map<String, Object> getID(String name, String value);

    Map<String, Object> getNextSilo();

    List<Map<String, Object>> getTrips(Integer id, Integer reqType, Long startDate, Long endDate);

    Map<String, Object> getLastTrip(Integer id, Integer reqType);

    Map<String, Object> getLastLoc(Integer id, Integer reqType);

    Map<String, Object> updateFwdCmd(Integer fwdID,Integer status);

}
