package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.ScoreItem;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TrendItem;

public class PerformanceDataBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8680004144158809996L;
//	private static final Logger logger = Logger.getLogger(PerformanceDataBean.class);
	protected ScoreDAO 			scoreDAO;

	Map<Duration, List<TrendItem>> trendCumulativeItemMap= new HashMap<Duration, List<TrendItem>>();
	Map<Duration, List<TrendItem>> trendItemMap= new HashMap<Duration, List<TrendItem>>();
	Map<Duration, List<ScoreItem>> avgItemMap= new HashMap<Duration, List<ScoreItem>>();
    
    Integer cachedID;
    EntityType cachedEntityType;
    
    
    // used for line graphs (uses the 7 day and 1 month bins
    public List<ScoreableEntity> getTrendCumulative(Integer id, EntityType entityType, Duration duration, ScoreType scoreType)
    {
    	
    	checkReInitCache(id, entityType);
    	
    	List<TrendItem> trendCumlativeItemList= trendCumulativeItemMap.get(duration);
    	
		if (trendCumlativeItemList == null) {
			trendCumlativeItemList = scoreDAO.getTrendCumulative(id, entityType, duration);
			trendCumulativeItemMap.put(duration, trendCumlativeItemList );
		}
		
		List<ScoreableEntity> list = new ArrayList<ScoreableEntity>();
		for (TrendItem item : trendCumlativeItemList)
		{
			if (item.getScoreType().equals(scoreType))
			{
				ScoreableEntity entity = new ScoreableEntity();
				entity.setScoreType(scoreType);
				entity.setEntityID(id);
				entity.setEntityType(EntityType.ENTITY_DRIVER);
				entity.setDate(item.getDate());
				entity.setScore(item.getScore());
				entity.setIdentifierNum(item.getDistance());
				list.add(entity);
			}
		}
		return list;
    }

    // used for mileage bar graphs (uses the 1 day and 1 month bins)
    public List<ScoreableEntity> getTrendDaily(Integer id, EntityType entityType, Duration duration, ScoreType scoreType)
    {
    	checkReInitCache(id, entityType);
    	
    	List<TrendItem> trendItemList= trendItemMap.get(duration);
    	
    	if (trendItemList == null) {
    		trendItemList = scoreDAO.getTrendScores(id, entityType, duration);
    		trendItemMap.put(duration, trendItemList);
    	}
    	
    	List<ScoreableEntity> list = new ArrayList<ScoreableEntity>();
    	for (TrendItem item : trendItemList)
    	{
    		if (item.getScoreType().equals(scoreType))
    		{
    			ScoreableEntity entity = new ScoreableEntity();
    			entity.setScoreType(scoreType);
    			entity.setEntityID(id);
    			entity.setEntityType(EntityType.ENTITY_DRIVER);
    			entity.setDate(item.getDate());
    			entity.setScore(item.getScore());
    			entity.setIdentifierNum(item.getDistance());
    			list.add(entity);
    		}
    	}
    	return list;

    }
    
    public Map<ScoreType, ScoreableEntity> getAverageScoreBreakdown(Integer id, EntityType entityType, Duration duration, ScoreType scoreType)
    {
    	checkReInitCache(id, entityType);
    	
    	List<ScoreItem> avgItemList= avgItemMap.get(duration);
    	
    	if (avgItemList == null) {
    		avgItemList = scoreDAO.getAverageScores(id, entityType, duration);
    		avgItemMap.put(duration, avgItemList);
    	}
    	
    	scoreType.getSubTypes();
    	Map<ScoreType, ScoreableEntity> breakdownMap  = new HashMap<ScoreType, ScoreableEntity>();
		for (ScoreType subType : scoreType.getSubTypes())
		{
			for (ScoreItem item : avgItemList)
			{
	    		if (item.getScoreType().equals(subType))
	    		{
	    			ScoreableEntity entity = new ScoreableEntity();
	    			entity.setScoreType(scoreType);
	    			entity.setEntityID(id);
	    			entity.setEntityType(EntityType.ENTITY_DRIVER);
	    			entity.setScore(item.getScore());
	    			breakdownMap.put(subType, entity);
	    		}
    		}
    	}
    	return breakdownMap;

    }

    public ScoreableEntity getAverageScore(Integer id, EntityType entityType, Duration duration, ScoreType scoreType)
    {
    	checkReInitCache(id, entityType);
    	
    	List<ScoreItem> avgItemList= avgItemMap.get(duration);
    	
    	if (avgItemList == null) {
    		avgItemList = scoreDAO.getAverageScores(id, entityType, duration);
    		avgItemMap.put(duration, avgItemList);
    	}
    	
    	for (ScoreItem item : avgItemList)
    	{
    		if (item.getScoreType().equals(scoreType))
    		{
    			ScoreableEntity entity = new ScoreableEntity();
    			entity.setScoreType(scoreType);
    			entity.setEntityID(id);
    			entity.setEntityType(EntityType.ENTITY_DRIVER);
    			entity.setScore(item.getScore());
    			return entity;
    		}
    	}
    	return null;
    	
    }
    
    
	private void checkReInitCache(Integer id, EntityType entityType) {
		if (cachedID == null || !id.equals(cachedID) ||
    		cachedEntityType == null || !entityType.equals(cachedEntityType))
    	{
    		trendCumulativeItemMap = new HashMap<Duration, List<TrendItem>>();
    		trendItemMap = new HashMap<Duration, List<TrendItem>>();
    		avgItemMap = new HashMap<Duration, List<ScoreItem>>();
    	}
    	cachedID = id;
    	cachedEntityType = entityType;
	}
    

    public ScoreDAO getScoreDAO() {
		return scoreDAO;
	}

	public void setScoreDAO(ScoreDAO scoreDAO) {
		this.scoreDAO = scoreDAO;
	}



}
