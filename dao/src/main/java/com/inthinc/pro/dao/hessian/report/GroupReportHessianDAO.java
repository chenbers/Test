package com.inthinc.pro.dao.hessian.report;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.model.aggregation.Percentage;
import com.inthinc.pro.model.aggregation.Score;

public class GroupReportHessianDAO extends AbstractReportHessianDAO implements GroupReportDAO {

    @Override
    public Score getAggregateDriverScore(Integer groupID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getGDScoreByGT(groupID, duration.getCode()), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Score getAggregateDriverScore(Integer groupID, DateTime startTime, DateTime endTime) {
        try {
            return mapper.convertToModelObject(reportService.getGDScoreByGSE(groupID, startTime.getMillis(), endTime.getMillis()), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getSDTrendsByGTC(groupID, duration.getCode(), duration.getDvqCount()), GroupTrendWrapper.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<GroupScoreWrapper> getSubGroupsAggregateDriverScores(Integer groupID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getSDScoresByGT(groupID, duration.getCode()), GroupScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public Percentage getDriverPercentage(Integer groupID, Duration duration) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getDVScoresByGT(groupID, duration.getCode()), DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DriverVehicleScoreWrapper> getDriverScores(Integer groupID, DateTime startTime, DateTime endTime) {           
        try {
            return mapper.convertToModelObject(reportService.getDVScoresByGSE(groupID, DateUtil.convertDateToSeconds(startTime.toDate()), 
                    DateUtil.convertDateToSeconds(endTime.toDate())), DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getVDScoresByGT(groupID, duration.getCode()), DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

}
