package com.inthinc.pro.dao;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.aggregation.Speed;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * Gets the maximum speeding value for the past <b>numDays</b> days.
     * Gets the total speeding time in seconds for the past <b>numDays</b> days.
     *
     * @param measurementType measurement type
     * @param driverID driver id
     * @param numDays number of days to include in calculation
     * @param includeForgiven if it includes forgiven or not (leave null for default '1')
     * @param endDate end date to search to (leave null for default 'now')
     * @return calculated value
     */
    public Speed getSpeedInfoForPastDays(Integer driverID, MeasurementType measurementType, Integer numDays, Integer includeForgiven, Date endDate);

    /**
     * Gets speed info for all persons and number of days.
     *
     * @param persons persons
     * @param numberOfDays number of days
     */
    public Map<Integer, Speed> getSpeedInfoForPersons(List<Person> persons, Integer numberOfDays);

    /**
     * Gets total mileage for all trips between startDate and endDate fora driver given by driverID.
     *
     * @param driverID driver id
     * @param startDate start date
     * @param endDate end date
     * @return total trip mileage
     */
    public Integer getTripMileageCountForDriver(Integer driverID, Date startDate, Date endDate);
}
