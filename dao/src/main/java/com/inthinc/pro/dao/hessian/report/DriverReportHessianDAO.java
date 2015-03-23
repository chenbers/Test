package com.inthinc.pro.dao.hessian.report;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.report.DriverReportDAO;
import com.inthinc.pro.model.CustomDuration;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Trend;

public class DriverReportHessianDAO extends AbstractReportHessianDAO implements DriverReportDAO {

    @Override
    public Score getScore(Integer driverID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getDScoreByDT(driverID, duration.getDvqCode()), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Score getScore(Integer driverID, CustomDuration customDuration) {
        try {
            return mapper.convertToModelObject(reportService.getDScoreByDT(driverID, customDuration.getDvqCode()), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<Trend> getTrend(Integer driverID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getDTrendByDTC(driverID, duration.getCode(), duration.getDvqCount()), Trend.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Trend> getTrend(Integer driverID, CustomDuration customDuration) {
        try {
            return mapper.convertToModelObject(reportService.getDTrendByDTC(driverID, customDuration.getCode(), customDuration.getDvqCount()), Trend.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
}
