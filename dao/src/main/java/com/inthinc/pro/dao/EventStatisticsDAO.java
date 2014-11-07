package com.inthinc.pro.dao;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

import java.util.Date;
import java.util.List;

/**
 * DAO for statistical information relating to events.
 */
public interface EventStatisticsDAO extends GenericDAO<Event, Integer> {
    public static final Integer EXCLUDE_FORGIVEN = 0;
    public static final Integer INCLUDE_FORGIVEN = 1;


    /**
     * Gets the maximum speeding value for the past <b>numDays</b> days.
     *
     * @param driverID driver id
     * @param numDays number of days to include in calculation
     * @param includeForgiven if it includes forgiven or not (leave null for default '1')
     * @param endDate end date to search to (leave null for default 'now')
     * @return calculated value
     */
    public Integer getMaxSpeedForPastDays(Integer driverID, Integer numDays, Integer includeForgiven, Date endDate);


    /**
     * Gets the total speeding time in seconds for the past <b>numDays</b> days.
     *
     * @param driverID driver id
     * @param numDays number of days to include in calculation
     * @param includeForgiven if it includes forgiven or not (leave null for default '1')
     * @param endDate end date to search to (leave null for default 'now')
     * @return calculated value
     */
    public Integer getSpeedingTimeInSecondsForPastDays(Integer driverID, Integer numDays, Integer includeForgiven, Date endDate);
}
