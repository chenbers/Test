package com.inthinc.pro.dao.report;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.pro.model.AggregationDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;

public interface GroupReportDAO {

    Score getAggregateDriverScore(Integer groupID, AggregationDuration duration);

    Score getAggregateDriverScore(Integer groupID, Interval interval);
    
    Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime);    

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, int aggregationDurationCode);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, AggregationDuration aggregationDuration);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Interval interval);
    
    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime day);

    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration);
    
    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, int aggregationDurationCode);
    
    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, AggregationDuration aggregationDuration);

    List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration);

    Percentage getDriverPercentage(Integer groupID, Duration duration);

    List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration);

}
