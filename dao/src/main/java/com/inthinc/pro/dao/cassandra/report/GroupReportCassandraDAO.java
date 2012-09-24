package com.inthinc.pro.dao.cassandra.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterRows;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Interval;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.cassandra.AggregationCassandraDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;

public class GroupReportCassandraDAO extends ReportCassandraDAO implements GroupReportDAO {

    protected ReportService reportService;
    protected Mapper mapper;
    private static final Logger logger = Logger.getLogger(GroupReportCassandraDAO.class);

    @Override
    public Score getAggregateDriverScore(Integer groupID, AggregationDuration duration, GroupHierarchy gh) {
        logger.debug("getAggregateDriverScore: " + groupID);
        return getScoreForGroup(groupID, duration.getCode(), gh, false);
    }

    @Override
    public Score getAggregateDriverScore(Integer groupID, Interval interval, GroupHierarchy gh) {
        logger.debug("getAggregateDriverScore Interval: " + groupID);
        try {

            DateTime intervalToUse = interval.getStart().toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
            return getAggregateDriverScore(groupID, intervalToUse, intervalToUse, gh);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
        logger.debug("getAggregateDriverScore start end: " + groupID);
        return getScoreForGroup(groupID, startTime.toDate(), endTime.toDate(), gh, false);
    }

    @Override
    public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration, GroupHierarchy gh) {
        logger.debug("getSubGroupsAggregateDriverTrends start end: " + groupID);
        List<GroupTrendWrapper> groupTrendWrapperList = new ArrayList<GroupTrendWrapper>();
        List<Integer> groupIDList = gh.getGroupIDList(groupID);
        for (Integer groupId : groupIDList) {
            GroupTrendWrapper groupTrendWrapper = new GroupTrendWrapper();
            groupTrendWrapper.setGroup(gh.getGroup(groupID));
            groupTrendWrapper.setTrendList(getTrendForGroup(groupId, duration, gh, false));
            groupTrendWrapperList.add(groupTrendWrapper);
        }

        return groupTrendWrapperList;
    }

    @Override
    public List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
        logger.debug("getSubGroupsAggregateDriverTrends duration: " + groupID);
        List<GroupScoreWrapper> groupScoreWrapperList = new ArrayList<GroupScoreWrapper>();
        List<Integer> groupIDList = gh.getGroupIDList(groupID);
        for (Integer groupId : groupIDList) {
            GroupScoreWrapper groupScoreWrapper = new GroupScoreWrapper();
            groupScoreWrapper.setGroup(gh.getGroup(groupID));
            groupScoreWrapper.setScore(getScoreForGroup(groupId, duration.getCode(), gh, false));
            groupScoreWrapperList.add(groupScoreWrapper);
        }

        return groupScoreWrapperList;
    }

    @Override
    public Percentage getDriverPercentage(Integer groupID, Duration duration, GroupHierarchy gh) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
        logger.debug("getDriverScores groupID: " + groupID);
        List<DriverVehicleScoreWrapper> driverVehicleScoreWrapperList = new ArrayList<DriverVehicleScoreWrapper>();
        List<Driver> driverList = getDriverDAO().getAllDrivers(groupID);
        for (Driver driver : driverList) {
            Score score = getScoreForAsset(driver.getDriverID(), EntityType.ENTITY_DRIVER, aggregationDurationCode);

            if (score != null) {
                Vehicle vehicle = getVehicleDAO().findByDriverID(driver.getDriverID());

                // List<Composite> rowKeys = createDateIDKeys(endDate, driver.getDriverID(), aggregationDurationCode, 1);
                DriverVehicleScoreWrapper driverVehicleScoreWrapper = new DriverVehicleScoreWrapper();
                driverVehicleScoreWrapper.setDriver(driver);
                driverVehicleScoreWrapper.setVehicle(vehicle);
                driverVehicleScoreWrapper.setScore(score);

                driverVehicleScoreWrapperList.add(driverVehicleScoreWrapper);
            }
        }
        return driverVehicleScoreWrapperList;

    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
        logger.debug("getDriverScoresw groupID: " + groupID);
        List<DriverVehicleScoreWrapper> driverVehicleScoreWrapperList = new ArrayList<DriverVehicleScoreWrapper>();
        List<Driver> driverList = getDriverDAO().getAllDrivers(groupID);
        for (Driver driver : driverList) {
            Score score = getScoreForAsset(driver.getDriverID(), EntityType.ENTITY_DRIVER, startTime.toDate(), endTime.toDate());
            if (score != null) {
                Vehicle vehicle = getVehicleDAO().findByDriverID(driver.getDriverID());

                DriverVehicleScoreWrapper driverVehicleScoreWrapper = new DriverVehicleScoreWrapper();
                driverVehicleScoreWrapper.setDriver(driver);
                driverVehicleScoreWrapper.setVehicle(vehicle);
                driverVehicleScoreWrapper.setScore(score);

                driverVehicleScoreWrapperList.add(driverVehicleScoreWrapper);
            }
        }
        return driverVehicleScoreWrapperList;
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh) {
        return getDriverScores(groupID, aggregationDuration.getCode(), gh);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
        return getDriverScores(groupID, duration.getDvqCode(), gh);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime day, GroupHierarchy gh) {
        logger.debug("getDriverScores DateTime: " + day);
        // The hessian method being called requires two params, both should be the same midnight value of the day you are trying to indicate.

        // get the timezone of the startDateTime and store the offset
        int offset = day.getZone().getOffset(day);
        logger.debug("getDriverScores offset: " + offset);

        // apply the offset to the startDateTime's underlying millis. Then change the timezone to UTC. Then adjust the millis to the Midnight value.
        DateTime intervalToUse = day.plusMillis(offset).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
        logger.debug("getDriverScores intervalToUse: " + intervalToUse);

        return getDriverScores(groupID, intervalToUse, intervalToUse, gh);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval, GroupHierarchy gh) {
        // The hessian method being called requires two params, which should be the midnight value of the interval
        // you are trying to indicate.

        // broken up into multiple lines for ease of reading

        // find the days in the interval
        Days days = Days.daysBetween(interval.getStart(), interval.getEnd());
        int daysBetween = days.getDays();

        // get the intervals start DateTime
        DateTime startDateTime = interval.getStart();

        // get the timezone of the startDateTime and store the offset
        int offset = startDateTime.getZone().getOffset(startDateTime);

        // apply the offset to the startDateTime's underlying millis.
        // Then change the timezone to UTC. Then adjust the millis to the Midnight value.
        DateTime intervalToUse = startDateTime.plusMillis(offset).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();

        return getDriverScores(groupID, intervalToUse, intervalToUse.plusDays(daysBetween), gh);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration, GroupHierarchy gh) {
        return getVehicleScores(groupID, duration.getCode(), gh);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh) {
        return getVehicleScores(groupID, aggregationDuration.getCode(), gh);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
        logger.debug("getVehicleScores: " + groupID);
        /*
         * try { return mapper.convertToModelObject(reportService.getVDScoresByGT(groupID, aggregationDurationCode), DriverVehicleScoreWrapper.class); } catch
         * (EmptyResultSetException e) { return Collections.emptyList(); }
         */
        List<DriverVehicleScoreWrapper> driverVehicleScoreWrapperList = new ArrayList<DriverVehicleScoreWrapper>();
        List<Vehicle> vehicleList = getVehicleDAO().getVehiclesInGroupHierarchy(groupID);
        for (Vehicle vehicle : vehicleList) {
            Score score = getScoreForAsset(vehicle.getVehicleID(), EntityType.ENTITY_VEHICLE, aggregationDurationCode);
            if (score != null) {
                // No 'findDriverForVehicle' so use the last location for now
                LastLocation ll = getVehicleDAO().getLastLocation(vehicle.getVehicleID());
                Driver driver = (ll != null) ? getDriverDAO().findByID(ll.getDriverID()) : null;

                DriverVehicleScoreWrapper driverVehicleScoreWrapper = new DriverVehicleScoreWrapper();
                driverVehicleScoreWrapper.setDriver(driver);
                driverVehicleScoreWrapper.setVehicle(vehicle);
                driverVehicleScoreWrapper.setScore(score);
                driverVehicleScoreWrapperList.add(driverVehicleScoreWrapper);
            }
        }
        return driverVehicleScoreWrapperList;
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Interval interval, GroupHierarchy gh) {
        // The hessian method being called requires two params, which should be the midnight value of the interval
        // you are trying to indicate.

        // broken up into multiple lines for ease of reading

        // find the days in the interval
        Days days = Days.daysBetween(interval.getStart(), interval.getEnd());
        int daysBetween = days.getDays();

        // get the intervals start DateTime
        DateTime startDateTime = interval.getStart();

        // get the timezone of the startDateTime and store the offset
        int offset = startDateTime.getZone().getOffset(startDateTime);

        // apply the offset to the startDateTime's underlying millis.
        // Then change the timezone to UTC. Then adjust the millis to the Midnight value.
        DateTime intervalToUse = startDateTime.plusMillis(offset).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();

        return getVehicleScores(groupID, intervalToUse, intervalToUse.plusDays(daysBetween), gh);
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
        logger.debug("getVehicleScores2: " + groupID);
        List<DriverVehicleScoreWrapper> driverVehicleScoreWrapperList = new ArrayList<DriverVehicleScoreWrapper>();
        List<Vehicle> vehicleList = getVehicleDAO().getVehiclesInGroupHierarchy(groupID);
        for (Vehicle vehicle : vehicleList) {
            Score score = getScoreForAsset(vehicle.getVehicleID(), EntityType.ENTITY_VEHICLE, startTime.toDate(), endTime.toDate());
            if (score != null) {
                // No 'findDriverForVehicle' so use the last location for now
                LastLocation ll = getVehicleDAO().getLastLocation(vehicle.getVehicleID());
                Driver driver = (ll != null) ? getDriverDAO().findByID(ll.getDriverID()) : null;

                // List<Composite> rowKeys = createDateIDKeys(endDate, driver.getDriverID(), aggregationDurationCode, 1);
                DriverVehicleScoreWrapper driverVehicleScoreWrapper = new DriverVehicleScoreWrapper();
                driverVehicleScoreWrapper.setDriver(driver);
                driverVehicleScoreWrapper.setVehicle(vehicle);
                driverVehicleScoreWrapper.setScore(score);
                driverVehicleScoreWrapperList.add(driverVehicleScoreWrapper);
            }
        }
        return driverVehicleScoreWrapperList;

    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime day, GroupHierarchy gh) {
        // The hessian method being called requires two params, both should be the same midnight value of the day you are trying to indicate.

        // get the timezone of the startDateTime and store the offset
        int offset = day.getZone().getOffset(day);

        // apply the offset to the startDateTime's underlying millis. Then change the timezone to UTC. Then adjust the millis to the Midnight value.
        DateTime intervalToUse = day.plusMillis(offset).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();

        return getVehicleScores(groupID, intervalToUse, intervalToUse, gh);
    }

}
