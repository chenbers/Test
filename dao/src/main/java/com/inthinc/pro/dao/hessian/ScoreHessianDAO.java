package com.inthinc.pro.dao.hessian;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;


//public class GraphicHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer, CentralService> implements GraphicDAO{
public class ScoreHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer, CentralService> implements ScoreDAO{

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

private static final Logger logger = Logger.getLogger(ScoreHessianDAO.class);
	 	
//  @Override
//  public List<ScoreableEntity>  getScores(Integer userID, Integer levelID, Integer startDate, Integer endDate) {
//	  logger.debug("in getscores graphichessiandao");
//  
//	  List<ScoreableEntity> l = convertToModelObject(getService().getScores(userID,levelID,startDate,endDate));
//	  logger.debug("l found, size: " + l.size());
//	  return l;
//  }

    @Override
    public ScoreableEntity getOverallScore(Integer groupID, Integer startDate, Integer endDate)
    {
        return convertToModelObject(this.getSiloService().getOverallScore(groupID,startDate,endDate));
    }
    
    @Override
    public List<ScoreableEntity> getScores(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        logger.debug("getScores() groupID = " + groupID);
        List<ScoreableEntity> scoreList = convertToModelObject(this.getSiloService().getScores(groupID,startDate,endDate, scoreType.getCode()));
        return scoreList;
    }		

    @Override
    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, ScoreType scoreType)
    {
        return convertToModelObject(getSiloService().getScoreBreakdown(groupID,startDate,endDate, scoreType.getCode()));
    }
}
