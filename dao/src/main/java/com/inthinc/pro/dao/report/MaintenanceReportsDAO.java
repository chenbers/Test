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
    public List<MaintenanceReportItem> findVehiclesWithThreshold(List<Integer> groupIDList);

    /**
     * @param vehicleID
     * @return
     */
    Integer findMilesDriven(Integer vehicleID);

    /**
     * @param vehicleIDs
     * @return
     */
    Map<Integer, Integer> findMilesDriven(Set<Integer> vehicleIDs);

    /**
     * @param vehicleIDs
     * @return
     */
    Map<Integer, Integer> findEngineHours(List<Integer> vehicleIDs);

    /**
     * @param vehicleIDs
     * @return
     */
    Map<Integer, Integer> findBaseOdometer(List<Integer> vehicleIDs);

    /**
     * Find the odometer values for the given set of vehicleIDs (TiwiSpecific)
     * @param vehilceIDs
     * @return a map with 
     *          key<Integer> = vehicleID
     *          value<Integer> = odometer
     */
    Map<Integer, Integer> findTiwiOdometer(Set<Integer> vehicleIDs);
    /**
     * @param groupIDs
     * @param startDate
     * @param endDate
     * @return
     */
    List<MaintenanceReportItem> findMaintenanceEventsByGroupIDs(List<Integer> groupIDs, Date startDate, Date endDate);
}
