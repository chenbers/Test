package com.inthinc.pro.dao.report;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.model.MaintenanceReportItem;
import com.inthinc.pro.model.TrailerReportItem;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

/**
 * DAO specific to the Maintenance Reports.
 * Separated out for performance concerns
 *
 */
public interface MaintenanceReportsDAO {
    /**
     * @param groupIDList
     * @return
     */
    public List<MaintenanceReportItem> getVehiclesWithThreshold(List<Integer> groupIDList);

    /**
     * @param vehicleID
     * @return
     */
    Integer getMilesDriven(Integer vehicleID);

    /**
     * @param vehicleIDs
     * @return
     */
    Map<Integer, Integer> getMilesDriven(Set<Integer> vehicleIDs);

    /**
     * @param vehicleIDs
     * @return
     */
    Map<Integer, Integer> getEngineHours(List<Integer> vehicleIDs);

    /**
     * @param vehicleIDs
     * @return
     */
    Map<Integer, Integer> getBaseOdometer(List<Integer> vehicleIDs);

    /**
     * @param groupIDs
     * @param startDate
     * @param endDate
     * @return
     */
    List<MaintenanceReportItem> getMaintenanceEventsByGroupIDs(List<Integer> groupIDs, Date startDate, Date endDate);
}
