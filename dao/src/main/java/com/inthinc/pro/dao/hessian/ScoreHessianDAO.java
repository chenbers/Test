package com.inthinc.pro.dao.hessian;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;

public class ScoreHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer> implements ScoreDAO
{
    private static final Logger logger = Logger.getLogger(ScoreHessianDAO.class);
    
    private ReportService reportService;

    public ReportService getReportService()
    {
        return reportService;
    }

    public void setReportService(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @Override
    public List<ScoreableEntity> getTopFiveScores(Integer groupID)
    {
        List<ScoreableEntity> scoreList = getMapper().convertToModelObject(reportService.getTopFiveScores(groupID), ScoreableEntity.class);
        return scoreList;
        // TODO Auto-generated method stub
    }

    @Override
    public List<ScoreableEntity> getBottomFiveScores(Integer groupID)
    {
        List<ScoreableEntity> scoreList = getMapper().convertToModelObject(reportService.getBottomFiveScores(groupID), ScoreableEntity.class);
        return scoreList;
    }

    @Override
    public ScoreableEntity getAverageScoreByType(Integer groupID, Integer startDate, Integer endDate, ScoreType st)
    {
        return getMapper().convertToModelObject(reportService.getAverageScoreByType(groupID, startDate, endDate, st), ScoreableEntity.class);
    }
    
    @Override
    public ScoreableEntity getAverageScoreByTypeAndMiles(Integer groupID, Integer milesBack, ScoreType st)
    {
        return getMapper().convertToModelObject(reportService.getAverageScoreByTypeAndMiles(groupID, milesBack, st), ScoreableEntity.class);
    }

    @Override
    public List<ScoreableEntity> getScores(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
//        logger.debug("getScores() groupID = " + groupID);
        List<ScoreableEntity> scoreList = getMapper().convertToModelObject(reportService.getScores(groupID, startDate, endDate, scoreType.getCode()), ScoreableEntity.class);
        return scoreList;
    }

    @Override
    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        return getMapper().convertToModelObject(reportService.getScoreBreakdown(groupID, startDate, endDate, scoreType.getCode()), ScoreableEntity.class);
    }

    @Override
    public List<ScoreableEntity> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, ScoreType scoreType)
    {
        return getMapper().convertToModelObject(reportService.getDriverScoreHistoryByMiles(driverID, milesBack, scoreType.getCode()), ScoreableEntity.class);
        
        
    }

    @Override
    public List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        List<ScoreTypeBreakdown> scoreList = getMapper().convertToModelObject(reportService.getScoreBreakdownByType(groupID, startDate, endDate, scoreType.getCode()), ScoreTypeBreakdown.class);
        return scoreList;
    }
}
