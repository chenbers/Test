package com.inthinc.pro.dao.report;

import java.util.List;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;

public interface GroupReportDAO {

    Score getAggregateDriverScore(Integer groupID, Duration duration);

    List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration);

    List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration);

    List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration);

    Percentage getDriverPercentage(Integer groupID, Duration duration);

}
