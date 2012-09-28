package com.inthinc.pro.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;


public interface ReportDAO extends GenericDAO<Object, Integer> {

    /**
     * Gets a count of driver report items for the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param filters
     * 				Filters for data set            
     * @return Count of drivers in group that meet filtering criteria.
     */
	Integer getDriverReportCount(Integer groupID, List<TableFilterField> filters);


    /**
     * Gets a count of driver report items for the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param pageParams
     * 				Defines the page to return -- start,end rows, filter criteria and sort criteria            
     * @return List of driverReportItems in group that meet filtering criteria, sorted by sort criteria.
     */
	List<DriverReportItem> getDriverReportPage(Integer groupID, PageParams pageParams);


    /**
     * Gets a count of vehicle report items for the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param filters
     * 				Filters for data set            
     * @return Count of vehicles in group that meet filtering criteria.
     */
	Integer getVehicleReportCount(Integer groupID, List<TableFilterField> filters);


    /**
     * Gets a count of vehicle report items for the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param pageParams
     * 				Defines the page to return -- start,end rows, filter criteria and sort criteria            
     * @return List of vehicleReportItems in group that meet filtering criteria, sorted by sort criteria.
     */
	List<VehicleReportItem> getVehicleReportPage(Integer groupID, PageParams pageParams);


    /**
     * Gets a count of device report items for the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param filters
     * 				Filters for data set            
     * @return Count of devices in group that meet filtering criteria.
     */
	Integer getDeviceReportCount(Integer groupID, List<TableFilterField> filters);


    /**
     * Gets a count of device report items for the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param pageParams
     * 				Defines the page to return -- start,end rows, filter criteria and sort criteria            
     * @return List of deviceReportItems in group that meet filtering criteria, sorted by sort criteria.
     */
	List<DeviceReportItem> getDeviceReportPage(Integer groupID, PageParams pageParams);
	
    /**
     * Gets a count of idling report items for the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param filters
     * 				Filters for data set            
     * @return Count of idling report items in group that meet filtering criteria.
     */
	Integer getIdlingReportCount(Integer groupID, Interval interval, List<TableFilterField> filters);

    /**
     * Gets a total count of idling report items for the specified group and filters (includes drivers that
     * have driven a vehicle that supports idle stats).
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param filters
     * 				Filters for data set            
     * @return Count of idling report items in group that meet filtering criteria.
     */
	Integer getIdlingReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters);

    /**
     * Gets a count of idling report items for the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param pageParams
     * 				Defines the page to return -- start,end rows, filter criteria and sort criteria            
     * @return List of idlingReportItems in group that meet filtering criteria, sorted by sort criteria.
     */
	List<IdlingReportItem> getIdlingReportPage(Integer groupID, Interval interval, PageParams pageParams);
	
    /**
     * Gets a count of idling report items for Vehicles in the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param filters
     * 				Filters for data set            
     * @return Count of idling report items in group that meet filtering criteria.
     */
	Integer getIdlingVehicleReportCount(Integer groupID, Interval interval, List<TableFilterField> filters);
	
    /**
     * Gets a count of idling report items for Vehicles in the specified group and filters
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param pageParams
     * 				Defines the page to return -- start,end rows, filter criteria and sort criteria            
     * @return List of idlingReportItems in group that meet filtering criteria, sorted by sort criteria.
     */
	List<IdlingReportItem> getIdlingVehicleReportPage(Integer groupID, Interval interval, PageParams pageParams);	

    /**
     * Gets a total count of idling report items for Vehicles the specified group and filters (includes drivers that
     * have driven a vehicle that supports idle stats).
     * 
     * @param groupID
     *            	The groupID (deep) to retrieve.
     * @param filters
     * 				Filters for data set            
     * @return Count of idling report items in group that meet filtering criteria.
     */
	Integer getIdlingVehicleReportSupportsIdleStatsCount(Integer groupID, Interval interval, List<TableFilterField> filters);


}
