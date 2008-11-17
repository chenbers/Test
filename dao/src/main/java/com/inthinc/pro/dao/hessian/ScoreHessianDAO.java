package com.inthinc.pro.dao.hessian;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public class ScoreHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer> implements ScoreDAO
{
    private static final Logger logger = Logger.getLogger(ScoreHessianDAO.class);

    @Override
    public List<ScoreableEntity> getTopFiveScores(Integer groupID)
    {
        List<ScoreableEntity> scoreList = getMapper().convertToModelObject(this.getSiloService().getTopFiveScores(groupID), ScoreableEntity.class);
        return scoreList;
        // TODO Auto-generated method stub
    }

    @Override
    public List<ScoreableEntity> getBottomFiveScores(Integer groupID)
    {
        List<ScoreableEntity> scoreList = getMapper().convertToModelObject(this.getSiloService().getBottomFiveScores(groupID), ScoreableEntity.class);
        return scoreList;
    }

    @Override
    public ScoreableEntity getAverageScoreByType(Integer groupID, Integer startDate, Integer endDate, ScoreType st)
    {
        return getMapper().convertToModelObject(this.getSiloService().getAverageScoreByType(groupID, startDate, endDate, st), ScoreableEntity.class);
    }

    @Override
    public List<ScoreableEntity> getScores(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        logger.debug("getScores() groupID = " + groupID);
        List<ScoreableEntity> scoreList = getMapper().convertToModelObject(this.getSiloService().getScores(groupID, startDate, endDate, scoreType.getCode()), ScoreableEntity.class);
        return scoreList;
    }

    @Override
    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        return getMapper().convertToModelObject(getSiloService().getScoreBreakdown(groupID, startDate, endDate, scoreType.getCode()), ScoreableEntity.class);
    }

    @Override
    public List<ScoreableEntity> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, ScoreType scoreType)
    {
        return getMapper().convertToModelObject(getSiloService().getDriverScoreHistoryByMiles(driverID, milesBack, scoreType.getCode()), ScoreableEntity.class);
        
        
    }
}
