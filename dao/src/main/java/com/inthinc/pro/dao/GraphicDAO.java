package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.ScoreableEntity;

public interface GraphicDAO extends GenericDAO<ScoreableEntity, Integer>
{
	List<ScoreableEntity>  getScores(Integer userID, Integer levelID, Integer startDate, Integer endDate);

}
