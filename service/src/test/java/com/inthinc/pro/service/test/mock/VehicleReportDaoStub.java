package com.inthinc.pro.service.test.mock;

import java.util.List;

import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.report.VehicleReportDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

@Component
public class VehicleReportDaoStub implements VehicleReportDAO {

    /**
     * @see com.inthinc.pro.dao.report.VehicleReportDAO#getScore(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    @Override
    public Score getScore(Integer vehicleID, Duration duration) {
        return null;
    }

    /**
     * @see com.inthinc.pro.dao.report.VehicleReportDAO#getTrend(java.lang.Integer, com.inthinc.pro.model.Duration)
     */
    @Override
    public List<Trend> getTrend(Integer vehicleID, Duration duration) {
        return null;
    }
}
