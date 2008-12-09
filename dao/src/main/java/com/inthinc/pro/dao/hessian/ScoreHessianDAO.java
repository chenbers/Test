package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;

public class ScoreHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer> implements ScoreDAO
{
    private static final Logger logger = Logger.getLogger(ScoreHessianDAO.class);

    private ReportService       reportService;

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
        try
        {
            List<ScoreableEntity> scoreList = getMapper().convertToModelObject(reportService.getTopFiveScores(groupID), ScoreableEntity.class);
            return scoreList;
            // TODO Auto-generated method stub
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }
    }

    @Override
    public List<ScoreableEntity> getBottomFiveScores(Integer groupID)
    {
        try
        {
            List<ScoreableEntity> scoreList = getMapper().convertToModelObject(reportService.getBottomFiveScores(groupID), ScoreableEntity.class);
            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }
    }

    @Override
    public ScoreableEntity getAverageScoreByType(Integer groupID, Integer startDate, Integer endDate, ScoreType st)
    {
        try
        {
            return getMapper().convertToModelObject(reportService.getAverageScoreByType(groupID, startDate, endDate, st), ScoreableEntity.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return null;
            else
                throw e;
        }
    }

    @Override
    public ScoreableEntity getAverageScoreByTypeAndMiles(Integer groupID, Integer milesBack, ScoreType st)
    {
        try
        {
            return getMapper().convertToModelObject(reportService.getAverageScoreByTypeAndMiles(groupID, milesBack, st), ScoreableEntity.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return null;
            else
                throw e;
        }
    }

    @Override
    public List<ScoreableEntity> getScores(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        // logger.debug("getScores() groupID = " + groupID);
        try
        {
            List<ScoreableEntity> scoreList = getMapper().convertToModelObject(reportService.getScores(groupID, startDate, endDate, scoreType.getCode()), ScoreableEntity.class);
            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }

    }

    @Override
    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        try
        {
            return getMapper().convertToModelObject(reportService.getScoreBreakdown(groupID, startDate, endDate, scoreType.getCode()), ScoreableEntity.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }
    }

    @Override
    public List<ScoreableEntity> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, ScoreType scoreType)
    {
        try
        {
            return getMapper().convertToModelObject(reportService.getDriverScoreHistoryByMiles(driverID, milesBack, scoreType.getCode()), ScoreableEntity.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }

    }

    @Override
    public List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType)
    {
        try
        {
            List<ScoreTypeBreakdown> scoreList = getMapper().convertToModelObject(reportService.getScoreBreakdownByType(groupID, duration.getCode(), scoreType.getCode()),
                    ScoreTypeBreakdown.class);
            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }
    }
}
