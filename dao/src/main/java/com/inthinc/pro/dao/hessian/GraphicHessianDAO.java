package com.inthinc.pro.dao.hessian;

import org.apache.log4j.Logger;

import java.util.List;

import com.inthinc.pro.dao.GraphicDAO;
import com.inthinc.pro.dao.annotations.Converter;
import com.inthinc.pro.dao.service.CentralService;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;


//public class GraphicHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer, CentralService> implements GraphicDAO{
public class GraphicHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer, CentralService> implements GraphicDAO{
  private static final Logger logger = Logger.getLogger(GraphicHessianDAO.class);
	 	
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
	
    
    // TODO: Look at a better way to do these enum converters because it is pretty standard across the board
    @Converter(columnName = "entityType")
    public void setEntityType(ScoreableEntity scoreableEntity, Object value)
    {
      if (scoreableEntity == null || value == null)
        return;

      if (value instanceof Integer)
      {
          scoreableEntity.setEntityType(EntityType.getEntityType((Integer)value));
      }
    }
    @Converter(columnName = "scoreType")
    public void setScoreType(ScoreableEntity scoreableEntity, Object value)
    {
      if (scoreableEntity == null || value == null)
        return;

      if (value instanceof Integer)
      {
          scoreableEntity.setScoreType(ScoreType.getScoreType((Integer)value));
      }
    }

}
