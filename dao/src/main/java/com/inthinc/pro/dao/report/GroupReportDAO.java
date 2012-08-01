package com.inthinc.pro.dao.report;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;

public interface GroupReportDAO {

    Score getAggregateDriverScore(Integer groupID, AggregationDuration duration, GroupHierarchy gh);

    Score getAggregateDriverScore(Integer groupID, Interval interval, GroupHierarchy gh);
    
    Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh);    

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration, GroupHierarchy gh);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval, GroupHierarchy gh);
    
    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime day, GroupHierarchy gh);

    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration, GroupHierarchy gh);
    
    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, int aggregationDurationCode, GroupHierarchy gh);
    
    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, AggregationDuration aggregationDuration, GroupHierarchy gh);
    
    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Interval interval, GroupHierarchy gh);
    
    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime day, GroupHierarchy gh);

    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, DateTime startTime, DateTime endTime, GroupHierarchy gh);

    List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration, GroupHierarchy gh);

    Percentage getDriverPercentage(Integer groupID, Duration duration, GroupHierarchy gh);

    List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration, GroupHierarchy gh);

}
