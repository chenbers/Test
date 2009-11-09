package com.inthinc.pro.dao.report;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.MetricType;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.ScoreWrapper;
import com.inthinc.pro.model.aggregation.Trend;

public interface GroupReportDAO {

    Score getAggregateDriverScore(Integer groupID, Date startTime, Date endTime);

    Score getAggregateDriverScore(Integer groupID, Duration duration);

    List<ScoreWrapper> getDriverScores(Integer groupID, Duration duration);

    List<ScoreWrapper> getVehicleScores(Integer groupID, Duration duration);

    List<Trend> getAggregateDriverTrend(Integer groupID, Duration duration, MetricType metricType);

    Percentage getDriverPercentage(Integer groupID, Duration duration, MetricType metricType);

}
