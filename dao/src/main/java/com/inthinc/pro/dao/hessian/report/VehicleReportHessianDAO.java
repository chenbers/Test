package com.inthinc.pro.dao.hessian.report;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.report.VehicleReportDAO;
import com.inthinc.pro.model.CustomDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

public class VehicleReportHessianDAO extends AbstractReportHessianDAO implements VehicleReportDAO {

    @Override
    public Score getScore(Integer vehicleID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getVScoreByVT(vehicleID, duration.getDvqCode()), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Score getScore(Integer vehicleID, CustomDuration customDuration) {
        try {
            return mapper.convertToModelObject(reportService.getVScoreByVT(vehicleID, customDuration.getDvqCode()), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Trend> getTrend(Integer vehicleID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getVTrendByVTC(vehicleID, duration.getCode(), duration.getDvqCount()), Trend.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Trend> getTrend(Integer vehicleID, CustomDuration customDuration) {
        try {
            return mapper.convertToModelObject(reportService.getVTrendByVTC(vehicleID, customDuration.getCode(), customDuration.getDvqCount()), Trend.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
}
