package com.inthinc.pro.dao;

import java.util.List;
import java.util.Map;

// wrapper interface for hessian ReportService 
public interface RawScoreDAO {

    Map<String, Object> getDPctByGT(Integer groupID, Integer duration, Integer metric);

    Map<String, Object> getDScoreByDT(Integer driverID, Integer duration);

    List<Map<String, Object>> getDTrendByDTC(Integer driverID, Integer duration, Integer count);

    List<Map<String, Object>> getDVScoresByGSE(Integer groupID, Long startDate, Long endDate);

    List<Map<String, Object>> getDVScoresByGT(Integer groupID, Integer duration);

    Map<String, Object> getGDScoreByGSE(Integer groupID, Long startDate, Long endDate);

    Map<String, Object> getGDScoreByGT(Integer groupID, Integer duration);

    List<Map<String, Object>> getGDTrendByGTC(Integer groupID, Integer duration, Integer count);

    List<Map<String, Object>> getSDScoresByGT(Integer groupID, Integer duration);

    List<Map<String, Object>> getSDTrendsByGTC(Integer groupID, Integer duration, Integer count);

    List<Map<String, Object>> getVDScoresByGT(Integer groupID, Integer duration);

    List<Map<String, Object>> getVDScoresByGSE(Integer groupID, Long startDate, Long endDate);

    public Map<String, Object> getVScoreByVT(Integer vehicleID, Integer duration);

    public List<Map<String, Object>> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count);
}
