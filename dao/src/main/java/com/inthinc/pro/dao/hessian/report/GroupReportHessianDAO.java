package com.inthinc.pro.dao.hessian.report;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Interval;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;

public class GroupReportHessianDAO extends AbstractReportHessianDAO implements GroupReportDAO {
	private static final Logger logger = Logger.getLogger(GroupReportHessianDAO.class);

    @Override
    public Score getAggregateDriverScore(Integer groupID, AggregationDuration duration, GroupHierarchy gh) {
    	logger.debug("getAggregateDriverScore: " + groupID);
        try {
            return mapper.convertToModelObject(reportService.getGDScoreByGT(groupID, duration.getCode()), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Score getAggregateDriverScore(Integer groupID, Interval interval, GroupHierarchy gh) {
    	logger.debug("getAggregateDriverScore Interval: " + groupID);
        try {
            DateTime intervalToUse = interval.getStart().toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
            return getAggregateDriverScore(groupID,intervalToUse,intervalToUse, gh);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }
    
    @Override
    public Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
    	logger.debug("getAggregateDriverScore start end: " + groupID);
        try {
            return mapper.convertToModelObject(reportService.getGDScoreByGSE(groupID, 
                    DateUtil.convertDateToSeconds(startTime.toDate()), 
                    DateUtil.convertDateToSeconds(endTime.toDate())), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }    

    @Override
    public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration, GroupHierarchy gh) {
    	logger.debug("getSubGroupsAggregateDriverTrends start end: " + groupID);
        try {
            return mapper.convertToModelObject(reportService.getSDTrendsByGTC(groupID, duration.getCode(), duration.getDvqCount()), GroupTrendWrapper.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration, GroupHierarchy gh) {
    	logger.debug("getSubGroupsAggregateDriverTrends duration: " + groupID);
        try {
            return mapper.convertToModelObject(reportService.getSDScoresByGT(groupID, duration.getCode()), GroupScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Percentage getDriverPercentage(Integer groupID, Duration duration, GroupHierarchy gh) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh) {
    	logger.debug("getDriverScores: " + groupID);
        try {
            return mapper.convertToModelObject(reportService.getDVScoresByGT(groupID, aggregationDurationCode), DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
    	logger.debug("getDriverScores2: " + groupID);
        try {
            return mapper.convertToModelObject(reportService.getDVScoresByGSE(groupID, DateUtil.convertDateToSeconds(startTime.toDate()), DateUtil.convertDateToSeconds(endTime.toDate())),
                    DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
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
        //The hessian method being called requires two params, both should be the same midnight value of the day you are trying to indicate.

        //get the timezone of the startDateTime and store the offset
        int offset = day.getZone().getOffset(day);
        
        //apply the offset to the startDateTime's underlying millis. Then change the timezone to UTC. Then adjust the millis to the Midnight value.
        DateTime intervalToUse = day.plusMillis(offset).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
        
        return getDriverScores(groupID, intervalToUse, intervalToUse, gh);
    }    

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval, GroupHierarchy gh) {
        //The hessian method being called requires two params, which should be the midnight value of the interval
        //  you are trying to indicate.
        
        //broken up into multiple lines for ease of reading
        
        //find the days in the interval
        Days days = Days.daysBetween(interval.getStart(), interval.getEnd());
        int daysBetween = days.getDays();
        
        //get the intervals start DateTime
        DateTime startDateTime = interval.getStart();
        
        //get the timezone of the startDateTime and store the offset
        int offset = startDateTime.getZone().getOffset(startDateTime);
        
        //apply the offset to the startDateTime's underlying millis. 
        //  Then change the timezone to UTC. Then adjust the millis to the Midnight value.
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
        try {
            return mapper.convertToModelObject(reportService.getVDScoresByGT(groupID, aggregationDurationCode), DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Interval interval, GroupHierarchy gh) {
        //The hessian method being called requires two params, which should be the midnight value of the interval
        //  you are trying to indicate.
        
        //broken up into multiple lines for ease of reading
        
        //find the days in the interval
        Days days = Days.daysBetween(interval.getStart(), interval.getEnd());
        int daysBetween = days.getDays();
        
        //get the intervals start DateTime
        DateTime startDateTime = interval.getStart();
        
        //get the timezone of the startDateTime and store the offset
        int offset = startDateTime.getZone().getOffset(startDateTime);
        
        //apply the offset to the startDateTime's underlying millis. 
        //  Then change the timezone to UTC. Then adjust the millis to the Midnight value.
        DateTime intervalToUse = startDateTime.plusMillis(offset).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
        
        return getVehicleScores(groupID, intervalToUse, intervalToUse.plusDays(daysBetween-1), gh);
    }
    
    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh) {
    	logger.debug("getVehicleScores2: " + groupID);
        try {
            return mapper.convertToModelObject(reportService.getVDScoresByGSE(groupID, DateUtil.convertDateToSeconds(startTime.toDate()), DateUtil.convertDateToSeconds(endTime.toDate())),
                    DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime day, GroupHierarchy gh) {
        //The hessian method being called requires two params, both should be the same midnight value of the day you are trying to indicate.

        //get the timezone of the startDateTime and store the offset
        int offset = day.getZone().getOffset(day);
        
        //apply the offset to the startDateTime's underlying millis. Then change the timezone to UTC. Then adjust the millis to the Midnight value.
        DateTime intervalToUse = day.plusMillis(offset).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
        
        return getVehicleScores(groupID, intervalToUse, intervalToUse, gh);
    }  

}
