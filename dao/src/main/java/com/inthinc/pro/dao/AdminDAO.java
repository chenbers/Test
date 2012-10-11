package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public interface AdminDAO extends GenericDAO<Object, Integer> {

    /**
     * Gets a count of persons for the specified group and filters
     * 
     * @param groupID
     *              The groupID (deep) to retrieve.
     * @param filters
     *              Filters for data set            
     * @return Count of persons in group that meet filtering criteria.
     */
    Integer getPersonCount(Integer groupID, List<TableFilterField> filters);


    /**
     * Gets a list of persons for the specified group and filters
     * 
     * @param groupID
     *              The groupID (deep) to retrieve.
     * @param pageParams
     *              Defines the page to return -- start,end rows, filter criteria and sort criteria            
     * @return List of persons in group that meet filtering criteria, sorted by sort criteria.
     */
    List<Person> getPersonPage(Integer groupID, PageParams pageParams);

    Integer getVehicleCount(Integer groupID, List<TableFilterField> filters);
    List<Vehicle> getVehiclePage(Integer groupID, PageParams pageParams);

    Integer getDeviceCount(Integer groupID, List<TableFilterField> filters);
    List<Device> getDevicePage(Integer groupID, PageParams pageParams);
}

