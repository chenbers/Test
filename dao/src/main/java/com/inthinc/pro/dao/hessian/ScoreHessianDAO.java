package com.inthinc.pro.dao.hessian;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;

public class ScoreHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer, CentralService> implements ScoreDAO
{
    private static final Logger logger = Logger.getLogger(ScoreHessianDAO.class);

    @Override
    public List<ScoreableEntity> getTopFiveScores(Integer groupID)
    {
        List<ScoreableEntity> scoreList = convertToModelObject(this.getSiloService().getTopFiveScores(groupID));
        return scoreList;
        // TODO Auto-generated method stub
    }

    @Override
    public List<ScoreableEntity> getBottomFiveScores(Integer groupID)
    {
        List<ScoreableEntity> scoreList = convertToModelObject(this.getSiloService().getBottomFiveScores(groupID));
        return scoreList;
    }

    @Override
    public ScoreableEntity getAverageScoreByType(Integer groupID, Integer startDate, Integer endDate, ScoreType st)
    {
        return convertToModelObject(this.getSiloService().getAverageScoreByType(groupID, startDate, endDate, st));
    }

    @Override
    public List<ScoreableEntity> getScores(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        logger.debug("getScores() groupID = " + groupID);
        List<ScoreableEntity> scoreList = convertToModelObject(this.getSiloService().getScores(groupID, startDate, endDate, scoreType.getCode()));
        return scoreList;
    }

    @Override
    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        return convertToModelObject(getSiloService().getScoreBreakdown(groupID, startDate, endDate, scoreType.getCode()));
    }
}
