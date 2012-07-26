package com.inthinc.pro.dao.cassandra;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.ScoreItem;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TrendItem;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.scoring.ScoringFormulas;

import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.CounterRows;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
import me.prettyprint.hector.api.query.QueryResult;

public class ScoreCassandraDAO extends AggregationCassandraDAO implements ScoreDAO
{
	private static final Logger logger = Logger.getLogger(ScoreCassandraDAO.class);
    private static final Integer NO_SCORE = -1;

    
    
    public static void main(String[] args)
    {
        SiloService siloService = new SiloServiceCreator("192.168.1.218", 8199).getService();

    	ScoreCassandraDAO dao = new ScoreCassandraDAO();
        
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
    	dao.setVehicleDAO(vehicleDAO);
        
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
    	dao.setDriverDAO(driverDAO);

        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
    	dao.setGroupDAO(groupDAO);
    	
        CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "localhost:9160", 10, false);
    	dao.setCassandraDB(cassandraDB);
/*    	
    	LastLocation ll = dao.getLastLocationForVehicle(52721);
		System.out.println("Location: " + ll);
    	
    	ll = dao.getLastLocationForDriver(66462);
		System.out.println("Location: " + ll);

		Trip trip =  dao.getLastTripForVehicle(52721);
		System.out.println("Trip: " + trip);
		
	    trip = dao.getLastTripForDriver(66462);
		System.out.println("Trip: " + trip);
*/    	
//    	List<DriverScore> driverScoreList = dao.getSortedDriverScoreList(5260, Duration.DAYS);    	

//    	List<TrendItem> trendItemList = dao.getTrendScores(66475, EntityType.ENTITY_DRIVER, Duration.DAYS);

	    dao.shutdown();
    }
    
    
	@Override
	public ScoreableEntity findByID(Integer id) {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public Integer create(Integer id, ScoreableEntity entity) {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public Integer update(ScoreableEntity entity) {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
	}

	@Override
	public Integer deleteByID(Integer id) {
        throw new UnsupportedOperationException("This method is not supported in this DAO");
	}
    
    @Override
    public List<DriverScore> getSortedDriverScoreList(Integer groupID, Duration duration, GroupHierarchy gh)
    {
    	/* try
        {
	        List<DVQMap> dvqList = getMapper().convertToModelObject(reportService.getDVScoresByGT(groupID, duration.getCode()), DVQMap.class);
	        List<DriverScore> scoreList = new ArrayList<DriverScore>();
	        for (DVQMap dvq : dvqList)
	        {
	            DriverScore driverScore = new DriverScore();
	            driverScore.setDriver(dvq.getDriver());
	            driverScore.setVehicle(dvq.getVehicle());
	            driverScore.setMilesDriven(dvq.getDriveQ().getEndingOdometer() == null ? 0 : dvq.getDriveQ().getEndingOdometer().longValue() / 100);
	            driverScore.setScore(dvq.getDriveQ().getOverall() != null ? dvq.getDriveQ().getOverall() : NO_SCORE);
	            scoreList.add(driverScore);
	        }
	        Collections.sort(scoreList);
	        return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        */

    	logger.debug("getSortedDriverScoreList groupID: " + groupID);
        List<DriverScore> scoreList = new ArrayList<DriverScore>();
        List<Driver> driverList = getDriverDAO().getAllDrivers(groupID);
        String endDate = getToday(TimeZone.getDefault()); //TO DO: set correct timezone
        for (Driver driver : driverList)
        {
        	Vehicle vehicle = getVehicleDAO().findByDriverID(driver.getDriverID());
        	
	        List<Composite> rowKeys = createDateIDKeys(endDate, driver.getDriverID(), duration.getCode(), 1); 
	
	        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverAggCF(duration.getCode()), rowKeys);
	        Map<String, Map<String, Long>> driverMap = summarizeByID(rows, 1);
	        
	        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
	        {
	        	Map<String, Long> columnMap = entry.getValue();
	        	DriverScore driverScore = new DriverScore();
	            driverScore.setDriver(driver);
	            driverScore.setVehicle(vehicle);
	            driverScore.setMilesDriven(getValue(columnMap,ODOMETER6)/100);
	            driverScore.setScore(getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
	            scoreList.add(driverScore);
	        }

        }
        Collections.sort(scoreList);
        return scoreList;
    }

    
    @Override
    public ScoreableEntity getAverageScoreByType(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getAverageScoreByType groupID: " + groupID);
/*        try
        {

            Map<String, Object> returnMap = reportService.getGDScoreByGT(groupID, duration.getCode());
            DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);

            ScoreableEntity scoreableEntity = new ScoreableEntity();
            scoreableEntity.setEntityID(groupID);
            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
            scoreableEntity.setScoreType(scoreType);
            scoreableEntity.setScore(dqMap.getScoreMap().get(scoreType));
            scoreableEntity.setDate(dqMap.getEndingDate());
            return scoreableEntity;

        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
*/
    	Integer binSize = Duration.BINSIZE_1_MONTH;
    	if (duration.equals(Duration.DAYS)) {
    		binSize = Duration.BINSIZE_7_DAY;
    	}

        ScoreableEntity scoreableEntity = null;
        List<Integer> groupIDList = gh.getGroupIDList(groupID);
//        groupIDList.add(groupID);
//        groupIDList.addAll(getSubgroups(groupID));

        List<Composite> rowKeys = new ArrayList<Composite>(); 
        for (Integer groupId : groupIDList)
        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, binSize, duration.getDvqCount()));
        	
        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(duration.getCode()), rowKeys);
    	logger.debug("getAverageScoreByType row count: " + rows.getCount());
        Map<String, Map<String, Long>> scoreMap = summarize(rows, 1);
    	logger.debug("getAverageScoreByType scoreMap: " + scoreMap);
        
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
            scoreableEntity = new ScoreableEntity();
        	Map<String, Long> columnMap = entry.getValue();

        	logger.debug("getAverageScoreByType columnMap: " + columnMap);
            
        	scoreableEntity.setEntityID(groupID);
            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
            scoreableEntity.setScoreType(scoreType);
            
            
            scoreableEntity.setScore(getResultForScoreType(scoreType, columnMap));
            scoreableEntity.setDate(new Date());
        }
    	
    	return scoreableEntity;    	
    }

    @Override
    public ScoreableEntity getSummaryScore(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getSummaryScore groupID: " + groupID);
/*    	
      	Integer binSize = duration.getAggregationBinSize();
    	if (duration.equals(Duration.DAYS)) {
    		binSize = 1;
    	}
        List<Map<String, Object>> topGroupMap = null;
        try {
        	topGroupMap = reportService.getGDTrendByGTC(groupID, binSize, 1);
        }
        catch (EmptyResultSetException e) {
        	return null;
        }
        
        List<DriveQMap> topGroupList = getMapper().convertToModelObject(topGroupMap, DriveQMap.class);
        
        // should only be one item in the list
        for (DriveQMap driveQMap : topGroupList)
        {
            ScoreableEntity entity = new ScoreableEntity();
            entity.setEntityID(groupID);
            entity.setEntityType(EntityType.ENTITY_GROUP);
            entity.setScore(driveQMap.getOverall() == null ? NO_SCORE : driveQMap.getOverall());
            entity.setScoreType(ScoreType.SCORE_OVERALL);
            entity.setDate(driveQMap.getEndingDate());
            return entity;
        }
        return null;
*/      
    	return getAverageScoreByType(groupID, duration, ScoreType.SCORE_OVERALL, gh);    	//Duration should be set to either one or one month.  That's why we are returning from here a ScoreableEntity rather than List<ScoreableEntity> 
    }
    
    @Override
    public List<ScoreableEntity> getScores(Integer topGroupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getScores groupID: " + topGroupID);
/*
    	try
        {
        	Integer binSize = Duration.BINSIZE_1_MONTH;
        	if (duration.equals(Duration.DAYS)) {
        		binSize = Duration.BINSIZE_7_DAY;
        	}
            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, binSize, 1);
            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
            for (GQVMap gqv : gqvList) {
	
	            	if (gqv.getDriveQV().size() == 0)
	            		continue;
	            	
	            	DriveQMap driveQ = gqv.getDriveQV().get(0);
	
	                ScoreableEntity scoreableEntity = new ScoreableEntity();
	                scoreableEntity.setEntityID(gqv.getGroup().getGroupID());
	                scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
	                scoreableEntity.setIdentifier(gqv.getGroup().getName() == null ? "unknown" : gqv.getGroup().getName());
	                scoreableEntity.setScoreType(scoreType);
	                scoreableEntity.setDate(driveQ.getEndingDate());
	                Integer score = driveQ.getScoreMap().get(scoreType);
	                scoreableEntity.setScore((score == null) ? NO_SCORE : score);
	                scoreList.add(scoreableEntity);
	
            }
            return scoreList;

        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
*/
    	Integer binSize = Duration.BINSIZE_1_MONTH;
    	if (duration.equals(Duration.DAYS)) {
    		binSize = Duration.BINSIZE_7_DAY;
    	}
    
    	
        List<ScoreableEntity> scoreableEntityList = new ArrayList<ScoreableEntity>();
        List<Integer> groupIDList = gh.getGroupIDList(topGroupID);
        
       
        List<Composite> rowKeys = new ArrayList<Composite>(); 
        for (Integer groupId : groupIDList)
        {
        	if (groupId.intValue() != topGroupID.intValue())
        	{
	        	logger.debug("groupId: " + groupId);
	        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, binSize, duration.getDvqCount()));
        	}
        }
        	
    	logger.debug("rowKeys: " + rowKeys);
        
        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(binSize), rowKeys);
    	logger.debug("rows: " + rows.getCount());
        Map<String, Map<String, Long>> scoreMap = summarizeByID(rows, groupIDList.size());
    	logger.debug("scoreMape: " + scoreMap);
        
        Date currentDate = new Date();
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	ScoreableEntity scoreableEntity = new ScoreableEntity();
        	Map<String, Long> columnMap = entry.getValue();
        	int id = Integer.parseInt(entry.getKey());
            scoreableEntity.setEntityID(id);
            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
            scoreableEntity.setScoreType(scoreType);
            
            String groupName = gh.getGroup(id).getName();
        	logger.debug("getTrendSummaryScore group: " + id + " " + groupName);
            scoreableEntity.setIdentifier((groupName == null || groupName.isEmpty()) ? "unknown" : groupName);
            
            scoreableEntity.setScore(getResultForScoreType(scoreType, columnMap));
            scoreableEntity.setDate(currentDate);
            
            scoreableEntityList.add(scoreableEntity);
        }
        return scoreableEntityList;
    }
    
    @Override
	//NOT USED
    public ScoreableEntity getTrendSummaryScore(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getTrendSummaryScore groupID: " + groupID);

    	return this.getAverageScoreByType(groupID, duration, scoreType, gh);
    }

    @Override
    public Map<Integer, List<ScoreableEntity>> getTrendScores(Integer topGroupID, Duration duration, GroupHierarchy gh)
    {
    	logger.debug("getTrendScores groupID: " + topGroupID);
/*
        try
        {
        	// subgroups
        	Integer binSize = Duration.BINSIZE_1_MONTH;
        	if (duration.equals(Duration.DAYS)) 
        		binSize = Duration.BINSIZE_7_DAY;

        	List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, binSize, duration.getDvqCount());
            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);
            
            
            Map<Integer, List<ScoreableEntity>> returnMap = new HashMap<Integer, List<ScoreableEntity>>();
            for (GQVMap gqv : gqvList)
            {
                List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
                for (DriveQMap driveQMap : gqv.getDriveQV())
                {
                    ScoreableEntity entity = new ScoreableEntity();
                    entity.setEntityID(gqv.getGroup().getGroupID());
                    entity.setEntityType(EntityType.ENTITY_GROUP);
                    entity.setScore(driveQMap.getOverall() == null ? NO_SCORE : driveQMap.getOverall());
                    entity.setScoreType(ScoreType.SCORE_OVERALL);
                    entity.setDate(driveQMap.getEndingDate());
                    scoreList.add(entity);
                    
                }
                returnMap.put(gqv.getGroup().getGroupID(), scoreList);

            }
            returnMap.put(groupID, getTopGroupScoreList(groupID, duration));
            return returnMap;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyMap();
        }
*/        
    	Integer binSize = Duration.BINSIZE_1_MONTH;
    	if (duration.equals(Duration.DAYS)) 
    		binSize = Duration.BINSIZE_7_DAY;

    	Map<Integer, List<ScoreableEntity>> returnMap = new HashMap<Integer, List<ScoreableEntity>>();
        List<Integer> groupIDList = gh.getGroupIDList(topGroupID);
	
	    List<Composite> rowKeys = new ArrayList<Composite>(); 
	    for (Integer groupId : groupIDList)
	    	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, binSize, duration.getDvqCount()));
 logger.debug("rowKeys: " + rowKeys);
	    	
	    CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(binSize), rowKeys);
logger.debug("row returned: " + rows.getCount());
	
	    //Calculate score tends for top group (District)
	    Map<String, Map<String, Long>> scoreMap = (binSize == Duration.BINSIZE_1_MONTH) ? summarizeByDate(rows, duration.getDvqCount()) : summarizeByWeek(rows, duration.getDvqCount());
logger.debug("scoreMap: " + scoreMap);
	    
	    List<ScoreableEntity> scoreableEntityList = new ArrayList<ScoreableEntity>();
	    for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
	    {
	    	ScoreableEntity scoreableEntity = new ScoreableEntity();
	    	Map<String, Long> columnMap = entry.getValue();
	        scoreableEntity.setEntityID(topGroupID);
	        scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
	        scoreableEntity.setScoreType(ScoreType.SCORE_OVERALL);
	        scoreableEntity.setScore((columnMap.size() == 0) ? -1 : getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
	        Date date = getDatefromString(entry.getKey());
	        logger.debug("Date: " + date);
	        scoreableEntity.setDate(date);
	        scoreableEntityList.add(scoreableEntity);
	    }
	    returnMap.put(topGroupID, scoreableEntityList);
        logger.debug("topGroupID scoreableEntityList.size(): " + returnMap.get(topGroupID).size());

	    //Calculate score tends for each subgroup (Team)
	    scoreMap = (binSize == Duration.BINSIZE_1_MONTH) ? summarizeByDateID(rows, duration.getDvqCount()) : summarizeByWeekID(rows, duration.getDvqCount());
	    scoreMap.remove(String.valueOf(topGroupID));
	    for (int currentGroupId : groupIDList)
	    {
	    	if (currentGroupId != topGroupID)
	    	{
				scoreableEntityList = new ArrayList<ScoreableEntity>();
		        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
		        {
		        	int subGroupId = Integer.parseInt(getKeyPart(entry.getKey(), 2));
		        	if (currentGroupId == subGroupId)
		        	{
			        	ScoreableEntity scoreableEntity = new ScoreableEntity();
			        	Map<String, Long> columnMap = entry.getValue();
			            scoreableEntity.setEntityID(subGroupId);
			            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
			            scoreableEntity.setScoreType(ScoreType.SCORE_OVERALL);
			            scoreableEntity.setScore((columnMap.size() == 0) ? -1 : getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
			            scoreableEntity.setDate(getDatefromString(entry.getKey()));
			            scoreableEntityList.add(scoreableEntity);
		        	}
		        }
		        returnMap.put(currentGroupId, scoreableEntityList);
	    	}
	    }
        logger.debug("topGroupID scoreableEntityList.size()2: " + returnMap.get(topGroupID).size());

        return returnMap;
    }
    	

      @Override
    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
      	logger.debug("getScoreBreakdown groupID: " + groupID + " Duration: " + duration);
		//Create quintileList
		List<ScoreableEntity> quintileScoreList = new ArrayList<ScoreableEntity>();
		for (int i = 0; i < 5; i++)
		{
		    ScoreableEntity entity = new ScoreableEntity();
		    entity.setEntityID(groupID);
		    entity.setEntityType(EntityType.ENTITY_GROUP);
		    entity.setScoreType(scoreType);
		    entity.setScore(0);
		    quintileScoreList.add(entity);
		}

        List<Driver> driverList = getDriverDAO().getAllDrivers(groupID);
        logger.debug("driverList.size(): " + driverList.size());
        for (Driver driver : driverList)
        {
          	logger.debug("getScoreBreakdown driverID: " + driver.getDriverID());

          	List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getTimeZone("UTC")), driver.getDriverID(), duration.getDvqCode(), duration.getDvqCount()); //TO DO: set correct timezone
          	logger.debug("getScoreBreakdown rowKeys: " + rowKeys);
	
	        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverAggCF(duration.getCode()), rowKeys);
	        Map<String, Map<String, Long>> driverMap = summarizeByID(rows, 1);
	        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
	        {
	          	logger.debug("getScoreBreakdownkey value: " + entry.getKey() + " " + entry.getValue());
	        	int quintile = getScoreQuintile(getResultForScoreType(scoreType, entry.getValue()));
	          	logger.debug("quintile: " + quintile);
	        	ScoreableEntity entity = quintileScoreList.get(quintile);
	          	logger.debug("quintile entity: " + entity);
	        	entity.setScore(entity.getScore()+1);
	        }
        }
	    return quintileScoreList;
    }
	
  	@Override
    public List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
      	logger.debug("getScoreBreakdownByType groupID: " + groupID);
        try
        {

            List<ScoreTypeBreakdown> scoreTypeBreakdownList = new ArrayList<ScoreTypeBreakdown>();
            List<ScoreType> subTypeList = scoreType.getSubTypes();

            for (ScoreType subType : subTypeList)
            {
                ScoreTypeBreakdown scoreTypeBreakdown = new ScoreTypeBreakdown();

                scoreTypeBreakdown.setScoreType(subType);
                scoreTypeBreakdown.setPercentageList(getScoreBreakdown(groupID, (subType.getDuration() == null) ? duration : subType.getDuration(), subType, gh));
                scoreTypeBreakdownList.add(scoreTypeBreakdown);
            }
            return scoreTypeBreakdownList;

        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
	
	@Override
    public List<TrendItem> getTrendCumulative(Integer id, EntityType entityType, Duration duration)
    {
      	logger.debug("getTrendCumulative ID: " + id);
/*		
        try
        {
        	Integer binSize = Duration.BINSIZE_1_MONTH;
        	if (duration.equals(Duration.DAYS)) 
        		binSize = Duration.BINSIZE_7_DAY;

        	List<Map<String, Object>> cumulativList = null;
            if (entityType.equals(EntityType.ENTITY_DRIVER))
            {
            	cumulativList = reportService.getDTrendByDTC(id, binSize, duration.getDvqCount());
            }
            else
            {
            	cumulativList = reportService.getVTrendByVTC(id, binSize, duration.getDvqCount());
            }
            List<DriveQMap> driveQList = getMapper().convertToModelObject(cumulativList, DriveQMap.class);
            
            List<TrendItem> trendItemList = new ArrayList<TrendItem>();
            for (DriveQMap driveQMap : driveQList)
            {
            	Map<ScoreType, Integer> scoreMap = driveQMap.getScoreMap();
            	
                for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
                	TrendItem item = new TrendItem();
                    item.setScoreType(scoreType);
                    item.setScore(scoreMap.get(scoreType));
                    item.setDate(driveQMap.getEndingDate());
                    if(scoreType == ScoreType.SCORE_SPEEDING_21_30)
                    	item.setDistance(driveQMap.getOdometer1());
                    else if(scoreType == ScoreType.SCORE_SPEEDING_31_40)
                    	item.setDistance(driveQMap.getOdometer2());
                    else if(scoreType == ScoreType.SCORE_SPEEDING_41_54)
                    	item.setDistance(driveQMap.getOdometer3());
                    else if(scoreType == ScoreType.SCORE_SPEEDING_55_64)
                    	item.setDistance(driveQMap.getOdometer4());
                    else if(scoreType == ScoreType.SCORE_SPEEDING_65_80)
                    	item.setDistance(driveQMap.getOdometer5());
                    else
                    	item.setDistance(driveQMap.getOdometer());
                	trendItemList.add(item);
            	}
            }
            return trendItemList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
  */
    
    	Integer binSize = Duration.BINSIZE_1_MONTH;
    	if (duration.equals(Duration.DAYS)) 
    		binSize = Duration.BINSIZE_7_DAY;
    	
    	return getTrendForAsset(id, entityType, binSize, duration.getDvqCount());

    }
    
	
//    private void dumpMap(Map<String, Object> map) {
//    	for (String key : map.keySet())
//    	{
//    		System.out.println(key + " = " + map.get(key));
//    	}
//	}
	
	@Override
    public List<TrendItem> getTrendScores(Integer id, EntityType entityType, Duration duration)
    {
      	logger.debug("getTrendScores ID: " + id);
		/*
        try
        {
        	List<Map<String, Object>> dailyList = null; 
            if (entityType.equals(EntityType.ENTITY_DRIVER))
            {
            	dailyList = reportService.getDTrendByDTC(id, duration.getAggregationBinSize(), duration.getDvqCount());
            }
            else
            {
            	dailyList = reportService.getVTrendByVTC(id, duration.getAggregationBinSize(), duration.getDvqCount());
            }
            List<DriveQMap> driveQList = getMapper().convertToModelObject(dailyList, DriveQMap.class);
            
            List<TrendItem> trendItemList = new ArrayList<TrendItem>();
            for (DriveQMap driveQMap : driveQList)
            {
            	Map<ScoreType, Integer> scoreMap = driveQMap.getScoreMap();
            	
                for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
                	TrendItem item = new TrendItem();
                    item.setScoreType(scoreType);
                    item.setScore(scoreMap.get(scoreType));
                    item.setDate(driveQMap.getEndingDate());
                    if(scoreType == ScoreType.SCORE_SPEEDING_21_30)
                    	item.setDistance(driveQMap.getOdometer1());
                    else if(scoreType == ScoreType.SCORE_SPEEDING_31_40)
                    	item.setDistance(driveQMap.getOdometer2());
                    else if(scoreType == ScoreType.SCORE_SPEEDING_41_54)
                    	item.setDistance(driveQMap.getOdometer3());
                    else if(scoreType == ScoreType.SCORE_SPEEDING_55_64)
                    	item.setDistance(driveQMap.getOdometer4());
                    else if(scoreType == ScoreType.SCORE_SPEEDING_65_80)
                    	item.setDistance(driveQMap.getOdometer5());
                    else
                    	item.setDistance(driveQMap.getOdometer());
                	trendItemList.add(item);
            	}
            }
            return trendItemList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        */
        
    	return getTrendForAsset(id, entityType, duration.getAggregationBinSize(), duration.getDvqCount());

    }

    @Override
    public List<VehicleReportItem> getVehicleReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap)
    {

        List<VehicleReportItem> vehicleReportItemList = new ArrayList<VehicleReportItem>();
        List<Vehicle> vehicleList = getVehicleDAO().getVehiclesInGroupHierarchy(groupID);
        for (Vehicle vehicle : vehicleList)
        {
	        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), vehicle.getVehicleID(), duration.getDvqCode(), 1); //TO DO: set correct timezone
	        CounterRows<Composite, String> rows = fetchAggsForKeys(getVehicleAggCF(duration.getDvqCode()), rowKeys);
	        Map<String, Map<String, Long>> vehicleMap = summarizeByID(rows, 1);
    
	        VehicleReportItem vehicleReportItem = new VehicleReportItem();
        	Group group = groupMap.get(vehicle.getGroupID());
            vehicleReportItem.setGroupName((group != null) ? group.getName() : "");
            vehicleReportItem.setGroupID(vehicle.getGroupID());
        	vehicleReportItem.setVehicleName(vehicle.getName() != null ? vehicle.getName() : "");
        	vehicleReportItem.setVehicleID(vehicle.getVehicleID());
        	vehicleReportItem.setVehicleYMM(vehicle.getFullName());
            vehicleReportItem.setOdometer(vehicle.getOdometer() != null ? vehicle.getOdometer() : 0);

            
            if (vehicleMap.size() > 0)
	        {
            	
            	Driver driver = getDriverDAO().findByID(vehicle.getDriverID());

                // May or may not have a driver assigned
                if (driver != null)
                {
                	vehicleReportItem.setDriverID(driver.getDriverID());
                	vehicleReportItem.setDriverName((driver.getPerson() != null) ? driver.getPerson().getFullName() : null);
                }

                
    	        for (Map.Entry<String, Map<String, Long>> entry : vehicleMap.entrySet())
    	        {
    	        	Map<String, Long> columnMap = entry.getValue();
    	        	vehicleReportItem.setMilesDriven(getValue(columnMap,ODOMETER6));
    	        	vehicleReportItem.setOverallScore(getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
    	        	vehicleReportItem.setSpeedScore(getResultForScoreType(ScoreType.SCORE_SPEEDING, columnMap));
    	        	vehicleReportItem.setStyleScore(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE, columnMap));
    	        }
	        }
	        else
	        {
	        	vehicleReportItem.setMilesDriven(0);
	        	vehicleReportItem.setOverallScore(NO_SCORE);
	        	vehicleReportItem.setSpeedScore(NO_SCORE);
	        	vehicleReportItem.setStyleScore(NO_SCORE);
	        	
	        }
            vehicleReportItemList.add(vehicleReportItem);
        }

        Collections.sort(vehicleReportItemList);
        return vehicleReportItemList;
    	
    }

    @Override
    public List<DriverReportItem> getDriverReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap)
    {
        List<DriverReportItem> driverReportItemList = new ArrayList<DriverReportItem>();
        List<Driver> driverList = getDriverDAO().getAllDrivers(groupID);
        for (Driver driver : driverList)
        {
	        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), driver.getDriverID(), duration.getDvqCode(), 1); //TO DO: set correct timezone
	
	        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverAggCF(duration.getDvqCode()), rowKeys);
	        Map<String, Map<String, Long>> driverMap = summarizeByID(rows, 1);
        	DriverReportItem driverReportItem = new DriverReportItem();
        	Group group = groupMap.get(driver.getGroupID());
            driverReportItem.setGroupName((group != null) ? group.getName() : "");
            driverReportItem.setGroupID(driver.getGroupID());
        	driverReportItem.setDriverID(driver.getDriverID());
        	driverReportItem.setDriverName((driver.getPerson() != null) ? driver.getPerson().getFullName() : null);
            driverReportItem.setEmployeeID((driver.getPerson() != null) ? driver.getPerson().getEmpid() : "");
	        if (driverMap.size() > 0)
	        {
            	Vehicle vehicle = getVehicleDAO().findByDriverID(driver.getDriverID());

                // May or may not have a vehicle assigned
                if (vehicle != null)
                {
                    driverReportItem.setVehicleID(vehicle.getVehicleID());
                    driverReportItem.setVehicleName(vehicle.getName() != null ? vehicle.getName() : "");
                }

                
    	        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
    	        {
    	        	Map<String, Long> columnMap = entry.getValue();
	                driverReportItem.setMilesDriven(getValue(columnMap,ODOMETER6));
	                driverReportItem.setOverallScore(getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
	                driverReportItem.setSpeedScore(getResultForScoreType(ScoreType.SCORE_SPEEDING, columnMap));
	                driverReportItem.setStyleScore(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE, columnMap));
	                driverReportItem.setSeatbeltScore(getResultForScoreType(ScoreType.SCORE_SEATBELT, columnMap));
    	        }
	        }
	        else
	        {
                driverReportItem.setMilesDriven(0);
                driverReportItem.setOverallScore(NO_SCORE);
                driverReportItem.setSpeedScore(NO_SCORE);
                driverReportItem.setStyleScore(NO_SCORE);
                driverReportItem.setSeatbeltScore(NO_SCORE);
	        	
	        }
            driverReportItemList.add(driverReportItem);
        }

        Collections.sort(driverReportItemList);
        return driverReportItemList;
    }

    @Override
    public List<ScoreItem> getAverageScores(Integer id, EntityType entityType, Duration duration)
    {
/*        try
        {
            DriveQMap driveQMap = null;
            if (entityType.equals(EntityType.ENTITY_DRIVER))
            {
            	driveQMap = getMapper().convertToModelObject(reportService.getDScoreByDT(id, duration.getDvqCode()), DriveQMap.class);
            }
            else
            {
            	driveQMap = getMapper().convertToModelObject(reportService.getVScoreByVT(id, duration.getDvqCode()), DriveQMap.class);
            }
            
            List<ScoreItem> scoreItemList = new ArrayList<ScoreItem>();
        	Map<ScoreType, Integer> scoreMap = driveQMap.getScoreMap();
        	
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
            	ScoreItem item = new ScoreItem();
                item.setScoreType(scoreType);
                item.setScore(scoreMap.get(scoreType));
            	scoreItemList.add(item);
        	}
            return scoreItemList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
*/
        List<ScoreItem> scoreItemList = new ArrayList<ScoreItem>();
    	
        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), id, duration.getDvqCode(), 1); //TO DO: set correct timezone

        String cf = (entityType.equals(EntityType.ENTITY_DRIVER)) ? getDriverAggCF(duration.getDvqCode()) : getVehicleAggCF(duration.getDvqCode()); 
        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
        Map<String, Map<String, Long>> driverMap = summarize(rows, 1);
        
        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
        {
        	Map<String, Long> columnMap = entry.getValue();
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
            	ScoreItem item = new ScoreItem();
                item.setScoreType(scoreType);
                item.setScore(getResultForScoreType(scoreType, columnMap));
            	scoreItemList.add(item);
        	}
        }
        return scoreItemList;
    
    }

    @Override
	public CrashSummary getGroupCrashSummaryData(Integer groupID, GroupHierarchy gh) {
      	logger.debug("getGroupCrashSummaryData groupID: " + groupID);
    	
/*		try {
			
	        Map<String, Object> returnMap = reportService.getGDScoreByGT(groupID, Duration.TWELVE.getCode());
	        DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);
	        
	        CrashSummary crashSummary = new CrashSummary(
	        					dqMap.getCrashEvents() == null ? 0 : dqMap.getCrashEvents(), 
	        					dqMap.getCrashTotal() == null ? 0 : dqMap.getCrashTotal(), 
	        					dqMap.getCrashDays() == null ? 0 : dqMap.getCrashDays(),
	        					dqMap.getOdometer() == null ? 0 : dqMap.getOdometer().doubleValue()/100.0, 
	        					dqMap.getCrashOdometer() == null ? 0 : dqMap.getCrashOdometer().doubleValue()/100.0);
	        return crashSummary;
		}
		catch (EmptyResultSetException e)
        {
	        return new CrashSummary(0,0,0,0,0);
        }
*/        
        return new CrashSummary(0,0,0,0,0);
	}

	@Override
	public CrashSummary getDriverCrashSummaryData(Integer driverID) {
/*
		try {
	        Map<String, Object> returnMap = reportService.getDScoreByDT(driverID, Duration.TWELVE.getCode());
	        DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);
	        CrashSummary crashSummary = new CrashSummary(
					dqMap.getCrashEvents() == null ? 0 : dqMap.getCrashEvents(), 
					dqMap.getCrashTotal() == null ? 0 : dqMap.getCrashTotal(), 
					dqMap.getCrashDays() == null ? 0 : dqMap.getCrashDays(),
					dqMap.getOdometer() == null ? 0 : dqMap.getOdometer().doubleValue()/100.0, 
					dqMap.getCrashOdometer() == null ? 0 : dqMap.getCrashOdometer().doubleValue()/100.0);
	         
	        return crashSummary;
		}
		catch (EmptyResultSetException e)
	    {
	        return new CrashSummary(0,0,0,0,0);
	    }
*/
        return new CrashSummary(0,0,0,0,0);

	}

	@Override
	public CrashSummary getVehicleCrashSummaryData(Integer vehicleID) {
/*
		try {
	        Map<String, Object> returnMap = reportService.getVScoreByVT(vehicleID, Duration.TWELVE.getCode());
	        DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);
	        CrashSummary crashSummary = new CrashSummary(
					dqMap.getCrashEvents() == null ? 0 : dqMap.getCrashEvents(), 
					dqMap.getCrashTotal() == null ? 0 : dqMap.getCrashTotal(), 
					dqMap.getCrashDays() == null ? 0 : dqMap.getCrashDays(),
					dqMap.getOdometer() == null ? 0 : dqMap.getOdometer().doubleValue()/100.0, 
					dqMap.getCrashOdometer() == null ? 0 : dqMap.getCrashOdometer().doubleValue()/100.0);
	               
	        return crashSummary;
		}
		catch (EmptyResultSetException e)
	    {
	        return new CrashSummary(0,0,0,0,0);
	    }
*/
        return new CrashSummary(0,0,0,0,0);

	}

	@Override
	public List<SpeedPercentItem> getSpeedPercentItems(Integer groupID, Duration duration, GroupHierarchy gh) {
  /*      try
        {
            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, duration.getAggregationBinSize(), duration.getDvqCount());
            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);

            List<SpeedPercentItem> speedPercentItemList = new ArrayList<SpeedPercentItem>();
            boolean first = true;
            for (GQVMap gqv : gqvList)
            {
                for (DriveQMap driveQMap : gqv.getDriveQV())
                {
                	Long distance = (driveQMap.getOdometer() == null) ? 0l : driveQMap.getOdometer().longValue();
                	Long speeding = (driveQMap.getSpeedOdometer()== null) ? 0 : driveQMap.getSpeedOdometer().longValue();
                	Date date = driveQMap.getEndingDate();
                	if (first)
                	{
                		speedPercentItemList.add(new SpeedPercentItem(distance, speeding, date));
                	}
                	else
                	{
                   		if (date == null)
                			continue;
	            		for (SpeedPercentItem item : speedPercentItemList)
                		{
	                   		if (item.getDate() == null)
	                			continue;
	            			if (item.getDate().equals(date))
                			{
                				item.setMiles(distance + item.getMiles().longValue());
                				item.setMilesSpeeding(speeding + item.getMilesSpeeding().longValue());
                				break;
                			}
                		}
                	}
                }
                
                first = false;
            }
            
            Collections.sort(speedPercentItemList);
            return speedPercentItemList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
*/        
        Date currentDate = new Date();
        List<SpeedPercentItem> speedPercentItemList = new ArrayList<SpeedPercentItem>();
        List<Integer> groupIDList = gh.getGroupIDList(groupID);

        List<Composite> rowKeys = new ArrayList<Composite>(); 
        for (Integer groupId : groupIDList)
        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, duration.getAggregationBinSize(), duration.getDvqCount()));
        	
        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(duration.getAggregationBinSize()), rowKeys);
        Map<String, Map<String, Long>> scoreMap = (duration.getAggregationBinSize() == Duration.BINSIZE_7_DAY) ? summarizeByWeek(rows, duration.getDvqCount()) : summarizeByDate(rows, duration.getDvqCount());
        
        long totalDistance = 0L;
        long totalSpeeding = 0L;
        
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	Date endDate = getDatefromString(entry.getKey());  //TODO fix timezone
        	Map<String, Long> columnMap = entry.getValue();
        	Long distance = getValue(columnMap,ODOMETER6);
        	Long speeding = getValue(columnMap,SPEEDODOMETER1) + getValue(columnMap,SPEEDODOMETER2) + getValue(columnMap,SPEEDODOMETER3) + getValue(columnMap,SPEEDODOMETER4) + getValue(columnMap,SPEEDODOMETER5);

        	logger.debug("speeding1: " + getValue(columnMap,SPEEDODOMETER1));
        	logger.debug("speeding2: " + getValue(columnMap,SPEEDODOMETER2));
        	logger.debug("speeding3: " + getValue(columnMap,SPEEDODOMETER3));
        	logger.debug("speeding4: " + getValue(columnMap,SPEEDODOMETER4));
        	logger.debug("speeding5: " + getValue(columnMap,SPEEDODOMETER5));
        	
        	logger.debug("distance: " + distance);
        	logger.debug("speeding: " + speeding);
        	speedPercentItemList.add(new SpeedPercentItem(distance, speeding, endDate));
        }
    	logger.debug("total distance: " + totalDistance);
    	logger.debug("total speeding: " + totalSpeeding);
        Collections.sort(speedPercentItemList);
        return speedPercentItemList;
	}

	@Override
	public List<IdlePercentItem> getIdlePercentItems(Integer groupID, Duration duration, GroupHierarchy gh) {
/*        try
        {
            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, duration.getAggregationBinSize(), duration.getDvqCount());
            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);
            List<IdlePercentItem> idlePercentItemList = new ArrayList<IdlePercentItem>();
            
            boolean first = true;
            for (GQVMap gqv : gqvList)
            {
                for (DriveQMap driveQMap : gqv.getDriveQV())
                {
                	Long driveTime = (driveQMap.getEmuRpmDriveTime() != null) ? driveQMap.getEmuRpmDriveTime().longValue() : 0l; 
                	Long idleTime = (driveQMap.getIdleHi() != null) ? driveQMap.getIdleHi().longValue() : 0l;
                	idleTime += (driveQMap.getIdleLo() != null) ? driveQMap.getIdleLo().longValue(): 0l; 
                	Integer numVehicles = (driveQMap.getnVehicles() != null) ? driveQMap.getnVehicles() : 0;
                	Integer numEMUVehicles = (driveQMap.getEmuRpmVehicles() != null) ? driveQMap.getEmuRpmVehicles() : 0;
                	Date date = driveQMap.getEndingDate();
                	if (first)
                	{
                    	idlePercentItemList.add(new IdlePercentItem(driveTime, idleTime, date, numVehicles, numEMUVehicles));
                	}
                	else
                	{
                		if (date == null)
                			continue;
	                	for (IdlePercentItem item : idlePercentItemList)
	                	{
	                		if (item.getDate() == null)
	                			continue;
	                		if (item.getDate().equals(date))
	                		{
	                			item.setDrivingTime(driveTime + item.getDrivingTime());
	                			item.setIdlingTime(idleTime + item.getIdlingTime()); 
	                			item.setNumVehicles(numVehicles + item.getNumVehicles());
	                			item.setNumEMUVehicles(numEMUVehicles + item.getNumEMUVehicles());
	                			break;
	                		}
	                	}
                	}
                }
                first = false;

            }
            Collections.sort(idlePercentItemList);
            
            return idlePercentItemList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
       
*/
        Date currentDate = new Date();
        List<IdlePercentItem> idlePercentItemList = new ArrayList<IdlePercentItem>();
        List<Integer> groupIDList = gh.getGroupIDList(groupID);
        List<Composite> rowKeys = new ArrayList<Composite>(); 
        for (Integer groupId : groupIDList)
        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, duration.getAggregationBinSize(), duration.getDvqCount()));
        	

        String cf = getDriverGroupAggCF(duration.getAggregationBinSize());
        logger.debug("CF Idle: " + cf);        	

        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
        Map<String, Map<String, Long>> scoreMap = (duration.getAggregationBinSize() == Duration.BINSIZE_7_DAY) ? summarizeByWeek(rows, duration.getDvqCount()) : summarizeByDate(rows, duration.getDvqCount());

        logger.debug("scoreMapIdle: " + scoreMap);        	
        
        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
        {
        	Date endDate = getDatefromString(entry.getKey());  //TODO fix timezone
        	Map<String, Long> columnMap = entry.getValue();

        	Long driveTime = getValue(columnMap,DRIVETIME); 
logger.debug("driveTime2: " + driveTime);        	
        	Long idleTime = getValue(columnMap,IDLELO) + getValue(columnMap,IDLEHI);
        	Integer numVehicles = 0;  //TODO
        	Integer numEMUVehicles = 0; //TODO
           	idlePercentItemList.add(new IdlePercentItem(driveTime, idleTime, endDate, numVehicles, numEMUVehicles));
        }
        Collections.sort(idlePercentItemList);
        return idlePercentItemList;

	}



}
