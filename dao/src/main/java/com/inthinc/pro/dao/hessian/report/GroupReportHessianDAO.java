package com.inthinc.pro.dao.hessian.report;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.report.GroupReportDAO;
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
            return mapper.convertToModelObject(reportService.getGDScoreByGT(groupID, duration.getDvqCode()), Score.class);
        } catch (EmptyResultSetException e) {
            return null;
        }
    }

    @Override
    public List<GroupTrendWrapper> getSubGroupsAggregateDriverTrends(Integer groupID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getSDTrendsByGTC(groupID, duration.getDvqCode(), duration.getDvqCount()), GroupTrendWrapper.class);
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
            return mapper.convertToModelObject(reportService.getDVScoresByGT(groupID, duration.getDvqCode()), DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DriverVehicleScoreWrapper> getVehicleScores(Integer groupID, Duration duration) {
        try {
            return mapper.convertToModelObject(reportService.getVDScoresByGT(groupID, duration.getDvqCode()), DriverVehicleScoreWrapper.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

}
