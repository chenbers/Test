package com.inthinc.pro.dao.report;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.MaintenanceReportItem;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public interface MaintenanceReportsDAO {
//    public List<TrailerReportItem> getTrailerReportItemByGroupPaging(Integer acctID, List<Integer> groupIDList, PageParams pageParams);
//    public Integer getTrailerReportCount(Integer acctID, List<Integer> groupIDList, List<TableFilterField> tableFilterFieldList);
//    public Boolean isValidTrailer(Integer acctID, String trailerName);
    
    public List<MaintenanceReportItem> getVehiclesWithThreshold(List<Integer> groupIDList);

    Integer getMilesDriven(Integer vehicleID);

    Map<Integer, Integer> getMilesDriven(Set<Integer> vehicleIDs);

    Map<Integer, Integer> getEngineHours(List<Integer> vehicleIDs);
    
}
