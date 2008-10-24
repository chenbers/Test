package com.inthinc.pro.dao.hessian;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.GraphicDAO;
import com.inthinc.pro.dao.service.CentralService;
import com.inthinc.pro.model.ScoreableEntity;

public class GraphicHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer, CentralService> implements GraphicDAO{
  private static final Logger logger = Logger.getLogger(GraphicHessianDAO.class);
	 	
  @Override
  public List getScores(Integer userID, Integer levelID, Integer startDate, Integer endDate) {
	  logger.debug("in getscores graphichessiandao");
  
	  List<ScoreableEntity> l = getService().getScores(userID,levelID,startDate,endDate);
	  logger.debug("l found, size: " + l.size());
	  return l;
  }		
	
}
