package com.inthinc.pro.dao.report;

import java.util.List;

import com.inthinc.pro.model.CustomDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

public interface VehicleReportDAO {

    Score getScore(Integer vehicleID, Duration duration);

    Score getScore(Integer vehicleID, CustomDuration customDuration);

    List<Trend> getTrend(Integer vehicleID, Duration duration);

    List<Trend> getTrend(Integer vehicleID, CustomDuration customDuration);
}
