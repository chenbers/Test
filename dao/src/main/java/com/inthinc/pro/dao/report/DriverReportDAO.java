package com.inthinc.pro.dao.report;

import java.util.List;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

public interface DriverReportDAO {

    Score getScore(Integer driverID, Duration duration);

    List<Trend> getTrend(Integer driverID, Duration duration);
}
