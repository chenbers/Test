package com.inthinc.pro.dao.hessian.report;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.MetricType;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.ScoreWrapper;
import com.inthinc.pro.model.aggregation.Trend;

public class GroupReportHessianDAO implements GroupReportDAO {

    @Override
    public Score getAggregateDriverScore(Integer groupID, Date startTime, Date endTime) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Score getAggregateDriverScore(Integer groupID, Duration duration) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Trend> getAggregateDriverTrend(Integer groupID, Duration duration, MetricType metricType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Percentage getDriverPercentage(Integer groupID, Duration duration, MetricType metricType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ScoreWrapper> getDriverScores(Integer groupID, Duration duration) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ScoreWrapper> getVehicleScores(Integer groupID, Duration duration) {
        // TODO Auto-generated method stub
        return null;
    }

}
