package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Trip;

public interface TripDAO extends GenericDAO<Trip, Integer>
{
    /**
     * Retrieve a list of trips between two dates.
     * 
     * @param driverID
     * @param startDate
     * @param endDate
     * @return
     */
    List<Trip> getTrips(Integer driverID, Integer startDate, Integer endDate);
}
