package com.inthinc.device.cassandras;
//
//import java.math.BigInteger;
//import java.nio.ByteBuffer;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.EnumSet;
//import java.util.GregorianCalendar;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.TimeZone;
//
//import org.apache.log4j.Logger;
//
//import com.inthinc.pro.dao.DriverDAO;
//import com.inthinc.pro.dao.GroupDAO;
//import com.inthinc.pro.dao.ScoreDAO;
//import com.inthinc.pro.dao.VehicleDAO;
//import com.inthinc.pro.dao.hessian.DriverHessianDAO;
//import com.inthinc.pro.dao.hessian.GroupHessianDAO;
//import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
//import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
//import com.inthinc.pro.dao.hessian.proserver.SiloService;
//import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
//import com.inthinc.pro.model.CrashSummary;
//import com.inthinc.pro.model.Driver;
//import com.inthinc.pro.model.DriverReportItem;
//import com.inthinc.pro.model.DriverScore;
//import com.inthinc.pro.model.Duration;
//import com.inthinc.pro.model.EntityType;
//import com.inthinc.pro.model.Group;
//import com.inthinc.pro.model.IdlePercentItem;
//import com.inthinc.pro.model.ScoreItem;
//import com.inthinc.pro.model.ScoreType;
//import com.inthinc.pro.model.ScoreTypeBreakdown;
//import com.inthinc.pro.model.ScoreableEntity;
//import com.inthinc.pro.model.SpeedPercentItem;
//import com.inthinc.pro.model.TrendItem;
//import com.inthinc.pro.model.Vehicle;
//import com.inthinc.pro.model.VehicleReportItem;
//import com.inthinc.pro.scoring.ScoringFormulas;
//
//import me.prettyprint.hector.api.beans.Composite;
//import me.prettyprint.hector.api.beans.CounterRow;
//import me.prettyprint.hector.api.beans.CounterRows;
//import me.prettyprint.hector.api.beans.CounterSlice;
//import me.prettyprint.hector.api.beans.HCounterColumn;
//import me.prettyprint.hector.api.factory.HFactory;
//import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
//import me.prettyprint.hector.api.query.QueryResult;
//
//public class ScoreCassandraDAO extends GenericCassandraDAO implements ScoreDAO
//{
//
//	//COLUMN FAMILY NAMES
//	public final static String AGGDAY_CF = "agg";
//	public final static String VEHICLEAGGINDEX_CF = "vehicleAggIndex";
//	public final static String DRIVERAGGINDEX_CF = "driverAggIndex";
//	public final static String AGGDRIVER_CF = "aggDriver";
//	public final static String AGGVEHICLE_CF = "aggVehicle";
//	public final static String AGGDRIVERMONTH_CF = "aggDriverMonth";
//	public final static String AGGVEHICLEMONTH_CF = "aggVehicleMonth";
//	public final static String AGGDRIVERGROUP_CF = "aggDriverGroup";
//	public final static String AGGDRIVERGROUPMONTH_CF = "aggDriverGroupMonth";
//
//	//COLUMN NAMES
//	public static final String DRIVETIME = "driveTime";
//	public static final String SPEEDPENALTY1 = "speedPenalty1";
//	public static final String SPEEDPENALTY2 = "speedPenalty2";
//	public static final String SPEEDPENALTY3 = "speedPenalty3";
//	public static final String SPEEDPENALTY4 = "speedPenalty4";
//	public static final String SPEEDPENALTY5 = "speedPenalty5";
//	public static final String SPEEDEVENTS1 = "speedEvents1";
//	public static final String SPEEDEVENTS2 = "speedEvents2";
//	public static final String SPEEDEVENTS3 = "speedEvents3";
//	public static final String SPEEDEVENTS4 = "speedEvents4";
//	public static final String SPEEDEVENTS5 = "speedEvents5";
//	public static final String SPEEDODOMETER1 = "speedOdometer1";
//	public static final String SPEEDODOMETER2 = "speedOdometer2";
//	public static final String SPEEDODOMETER3 = "speedOdometer3";
//	public static final String SPEEDODOMETER4 = "speedOdometer4";
//	public static final String SPEEDODOMETER5 = "speedOdometer5";
//
//	public static final String SPEEDEVENTS_1TO7_OVER = "speedEvents1To7MphOver";
//	public static final String SPEEDEVENTS_8TO14_OVER = "speedEvents8To14MphOver";
//	public static final String SPEEDEVENTS_15PLUS_OVER = "speedEvents15PlusMphOver";
//	public static final String SPEEDEVENTS_OVER_80 = "speedEventsOver80Mph";
//
//	public static final String SPEEDOVER = "speedOver";
//	public static final String SPEEDCOACHING1 = "speedCoaching1";	
//	public static final String SPEEDCOACHING2 = "speedCoaching2";	
//	public static final String SPEEDCOACHING3 = "speedCoaching3";	
//	public static final String SPEEDCOACHING4 = "speedCoaching4";	
//	public static final String SPEEDCOACHING5 = "speedCoaching5";	
//	public static final String SEATBELTPENALTY = "seatbeltPenalty";
//	public static final String SEATBELTEVENTS = "seatbeltEvents";
//	public static final String IDLELO = "idleLo";
//	public static final String IDLELOEVENTS = "idleLoEvents";
//	public static final String IDLEHI = "idleHi";
//	public static final String IDLEHIEVENTS = "idleHiEvents";
//	public static final String CRASHEVENTS = "crashEvents";
//	public static final String RPMEVENTS = "rpmEvents";
//	public static final String AGGRESSIVEBRAKEPENALTY = "aggressiveBrakePenalty";
//	public static final String AGGRESSIVEBRAKEEVENTS = "aggressiveBrakeEvents";
//	public static final String AGGRESSIVEACCELPENALTY = "aggressiveAccelPenalty";
//	public static final String AGGRESSIVEACCELEVENTS = "aggressiveAccelEvents";
//	public static final String AGGRESSIVELEFTPENALTY = "aggressiveLeftPenalty";
//	public static final String AGGRESSIVELEFTEVENTS = "aggressiveLeftEvents";
//	public static final String AGGRESSIVERIGHTPENALTY = "aggressiveRightPenalty";
//	public static final String AGGRESSIVERIGHTEVENTS = "aggressiveRightEvents";
//	public static final String AGGRESSIVEBUMPPENALTY = "aggressiveBumpPenalty";
//	public static final String AGGRESSIVEBUMPEVENTS = "aggressiveBumpEvents";
//	public static final String EFFICIENCYPENALTY = "efficiencyPenalty";
//	public static final String PENALTY = "penalty";
//	public final static String ODOMETER = "odometer";
//	public final static String ODOMETER6 = "odometer6";
//	public final static String MPGODOMETER = "mpgOdometer";	
//	public final static String MPGGAL = "mpgGal";
//	public final static String EMUFEATUREMASK = "emuFeatureMask";
//
//	
//	
//	private static final Logger logger = Logger.getLogger(ScoreCassandraDAO.class);
//    private static final Integer NO_SCORE = -1;
//
//    private GroupDAO groupDAO;
//    private VehicleDAO vehicleDAO;
//    private DriverDAO driverDAO;
//    
//    
//    public static void main(String[] args)
//    {
//        SiloService siloService = new SiloServiceCreator("192.168.1.218", 8199).getService();
//
//    	ScoreCassandraDAO dao = new ScoreCassandraDAO();
//        
//        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
//        vehicleDAO.setSiloService(siloService);
//    	dao.setVehicleDAO(vehicleDAO);
//        
//        DriverHessianDAO driverDAO = new DriverHessianDAO();
//        driverDAO.setSiloService(siloService);
//    	dao.setDriverDAO(driverDAO);
//
//        GroupHessianDAO groupDAO = new GroupHessianDAO();
//        groupDAO.setSiloService(siloService);
//    	dao.setGroupDAO(groupDAO);
//    	
//        CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "localhost:9160", 10, false);
//    	dao.setCassandraDB(cassandraDB);
///*    	
//    	LastLocation ll = dao.getLastLocationForVehicle(52721);
//		System.out.println("Location: " + ll);
//    	
//    	ll = dao.getLastLocationForDriver(66462);
//		System.out.println("Location: " + ll);
//
//		Trip trip =  dao.getLastTripForVehicle(52721);
//		System.out.println("Trip: " + trip);
//		
//	    trip = dao.getLastTripForDriver(66462);
//		System.out.println("Trip: " + trip);
//*/    	
//    	List<DriverScore> driverScoreList = dao.getSortedDriverScoreList(5260, Duration.DAYS);    	
//
////    	List<TrendItem> trendItemList = dao.getTrendScores(66475, EntityType.ENTITY_DRIVER, Duration.DAYS);
//
//	    dao.shutdown();
//    }
//    
//
//    public GroupDAO getGroupDAO() {
//		return groupDAO;
//	}
//
//    public void setGroupDAO(GroupDAO groupDAO) {
//		this.groupDAO = groupDAO;
//	}
//    
//
//	public VehicleDAO getVehicleDAO() {
//		return vehicleDAO;
//	}
//
//	public void setVehicleDAO(VehicleDAO vehicleDAO) {
//		this.vehicleDAO = vehicleDAO;
//	}
//
//	public DriverDAO getDriverDAO() {
//		return driverDAO;
//	}
//
//	public void setDriverDAO(DriverDAO driverDAO) {
//		this.driverDAO = driverDAO;
//	}
//
//    
//	@Override
//	public ScoreableEntity findByID(Integer id) {
//        throw new UnsupportedOperationException("This method is not supported in this DAO");
//	}
//
//	@Override
//	public Integer create(Integer id, ScoreableEntity entity) {
//        throw new UnsupportedOperationException("This method is not supported in this DAO");
//	}
//
//	@Override
//	public Integer update(ScoreableEntity entity) {
//        throw new UnsupportedOperationException("This method is not supported in this DAO");
//	}
//
//	@Override
//	public Integer deleteByID(Integer id) {
//        throw new UnsupportedOperationException("This method is not supported in this DAO");
//	}
//    
//    @Override
//    public List<DriverScore> getSortedDriverScoreList(Integer groupID, Duration duration)
//    {
//    	/* try
//        {
//	        List<DVQMap> dvqList = getMapper().convertToModelObject(reportService.getDVScoresByGT(groupID, duration.getCode()), DVQMap.class);
//	        List<DriverScore> scoreList = new ArrayList<DriverScore>();
//	        for (DVQMap dvq : dvqList)
//	        {
//	            DriverScore driverScore = new DriverScore();
//	            driverScore.setDriver(dvq.getDriver());
//	            driverScore.setVehicle(dvq.getVehicle());
//	            driverScore.setMilesDriven(dvq.getDriveQ().getEndingOdometer() == null ? 0 : dvq.getDriveQ().getEndingOdometer().longValue() / 100);
//	            driverScore.setScore(dvq.getDriveQ().getOverall() != null ? dvq.getDriveQ().getOverall() : NO_SCORE);
//	            scoreList.add(driverScore);
//	        }
//	        Collections.sort(scoreList);
//	        return scoreList;
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyList();
//        }
//        */
//        
//        List<DriverScore> scoreList = new ArrayList<DriverScore>();
//        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
//        String endDate = getToday(TimeZone.getDefault()); //TO DO: set correct timezone
//        for (Driver driver : driverList)
//        {
//        	Vehicle vehicle = vehicleDAO.findByDriverID(driver.getDriverID());
//        	
//	        List<Composite> rowKeys = createDateIDKeys(endDate, driver.getDriverID(), duration.getCode(), 1); 
//	
//	        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverAggCF(duration.getCode()), rowKeys);
//	        Map<String, Map<String, Long>> driverMap = summarizeByID(rows);
//	        
//	        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
//	        {
//	        	Map<String, Long> columnMap = entry.getValue();
//	        	DriverScore driverScore = new DriverScore();
//	            driverScore.setDriver(driver);
//	            driverScore.setVehicle(vehicle);
//	            driverScore.setMilesDriven(getValue(columnMap,ODOMETER6)/100);
//	            driverScore.setScore(getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
//	            scoreList.add(driverScore);
//	        }
//
//        }
//        Collections.sort(scoreList);
//        return scoreList;
//    }
//
//    
//    @Override
//    public ScoreableEntity getAverageScoreByType(Integer groupID, Duration duration, ScoreType scoreType)
//    {
///*        try
//        {
//
//            Map<String, Object> returnMap = reportService.getGDScoreByGT(groupID, duration.getCode());
//            DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);
//
//            ScoreableEntity scoreableEntity = new ScoreableEntity();
//            scoreableEntity.setEntityID(groupID);
//            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
//            scoreableEntity.setScoreType(scoreType);
//            scoreableEntity.setScore(dqMap.getScoreMap().get(scoreType));
//            scoreableEntity.setDate(dqMap.getEndingDate());
//            return scoreableEntity;
//
//        }
//        catch (EmptyResultSetException e)
//        {
//            return null;
//        }
//*/
//    	Integer binSize = Duration.BINSIZE_1_MONTH;
//    	if (duration.equals(Duration.DAYS)) {
//    		binSize = Duration.BINSIZE_7_DAY;
//    	}
//
//        ScoreableEntity scoreableEntity = null;
//        List<Integer> groupIDList = new ArrayList<Integer>();
//        groupIDList.add(groupID);
//        groupIDList.addAll(getSubgroups(groupID));
//
//        List<Composite> rowKeys = new ArrayList<Composite>(); 
//        for (Integer groupId : groupIDList)
//        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, binSize, 1));
//        	
//        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(duration.getCode()), rowKeys);
//        Map<String, Map<String, Long>> scoreMap = summarizeByID(rows);
//        
//        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
//        {
//            scoreableEntity = new ScoreableEntity();
//        	Map<String, Long> columnMap = entry.getValue();
//            scoreableEntity.setEntityID(groupID);
//            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
//            scoreableEntity.setScoreType(scoreType);
//            
//            
//            scoreableEntity.setScore(getResultForScoreType(scoreType, columnMap));
//            scoreableEntity.setDate(new Date());
//            
//            break;
//        }
//    	
//    	return scoreableEntity;    	
//    }
//
//    @Override
//    public ScoreableEntity getSummaryScore(Integer groupID, Duration duration, ScoreType scoreType)
//    {
///*    	
//      	Integer binSize = duration.getAggregationBinSize();
//    	if (duration.equals(Duration.DAYS)) {
//    		binSize = 1;
//    	}
//        List<Map<String, Object>> topGroupMap = null;
//        try {
//        	topGroupMap = reportService.getGDTrendByGTC(groupID, binSize, 1);
//        }
//        catch (EmptyResultSetException e) {
//        	return null;
//        }
//        
//        List<DriveQMap> topGroupList = getMapper().convertToModelObject(topGroupMap, DriveQMap.class);
//        
//        // should only be one item in the list
//        for (DriveQMap driveQMap : topGroupList)
//        {
//            ScoreableEntity entity = new ScoreableEntity();
//            entity.setEntityID(groupID);
//            entity.setEntityType(EntityType.ENTITY_GROUP);
//            entity.setScore(driveQMap.getOverall() == null ? NO_SCORE : driveQMap.getOverall());
//            entity.setScoreType(ScoreType.SCORE_OVERALL);
//            entity.setDate(driveQMap.getEndingDate());
//            return entity;
//        }
//        return null;
//*/      
//    	return getAverageScoreByType(groupID, duration, ScoreType.SCORE_OVERALL);    	//Duration should be set to either one or one month.  That's why we are returning from here a ScoreableEntity rather than List<ScoreableEntity> 
//    }
//    
//    @Override
//    public List<ScoreableEntity> getScores(Integer groupID, Duration duration, ScoreType scoreType)
//    {
///*
//    	try
//        {
//        	Integer binSize = Duration.BINSIZE_1_MONTH;
//        	if (duration.equals(Duration.DAYS)) {
//        		binSize = Duration.BINSIZE_7_DAY;
//        	}
//            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, binSize, 1);
//            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);
//            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
//            for (GQVMap gqv : gqvList) {
//	
//	            	if (gqv.getDriveQV().size() == 0)
//	            		continue;
//	            	
//	            	DriveQMap driveQ = gqv.getDriveQV().get(0);
//	
//	                ScoreableEntity scoreableEntity = new ScoreableEntity();
//	                scoreableEntity.setEntityID(gqv.getGroup().getGroupID());
//	                scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
//	                scoreableEntity.setIdentifier(gqv.getGroup().getName() == null ? "unknown" : gqv.getGroup().getName());
//	                scoreableEntity.setScoreType(scoreType);
//	                scoreableEntity.setDate(driveQ.getEndingDate());
//	                Integer score = driveQ.getScoreMap().get(scoreType);
//	                scoreableEntity.setScore((score == null) ? NO_SCORE : score);
//	                scoreList.add(scoreableEntity);
//	
//            }
//            return scoreList;
//
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyList();
//        }
//*/
//    	Integer binSize = Duration.BINSIZE_1_MONTH;
//    	if (duration.equals(Duration.DAYS)) {
//    		binSize = Duration.BINSIZE_7_DAY;
//    	}
//    
//    	
//        List<ScoreableEntity> scoreableEntityList = new ArrayList<ScoreableEntity>();
//        List<Integer> groupIDList = new ArrayList<Integer>();
////        groupIDList.add(groupID);
//        groupIDList.addAll(getSubgroups(groupID));
//
//        List<Composite> rowKeys = new ArrayList<Composite>(); 
//        for (Integer groupId : groupIDList)
//        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, binSize, 1));
//        	
//        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(binSize), rowKeys);
//        Map<String, Map<String, Long>> scoreMap = summarizeByID(rows);
//        
//        Date currentDate = new Date();
//        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
//        {
//        	ScoreableEntity scoreableEntity = new ScoreableEntity();
//        	Map<String, Long> columnMap = entry.getValue();
//            scoreableEntity.setEntityID(Integer.parseInt(entry.getKey()));
//            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
//            scoreableEntity.setScoreType(scoreType);
//            
//            
//            scoreableEntity.setScore(getResultForScoreType(scoreType, columnMap));
//            scoreableEntity.setDate(currentDate);
//            
//            scoreableEntityList.add(scoreableEntity);
//        }
//        return scoreableEntityList;
//    }
//    
//    @Override
//	//NOT USED
//    public ScoreableEntity getTrendSummaryScore(Integer groupID, Duration duration, ScoreType scoreType)
//    {
//    	return this.getAverageScoreByType(groupID, duration, scoreType);
//    }
//
//    @Override
//    public Map<Integer, List<ScoreableEntity>> getTrendScores(Integer groupID, Duration duration)
//    {
///*
//        try
//        {
//        	// subgroups
//        	Integer binSize = Duration.BINSIZE_1_MONTH;
//        	if (duration.equals(Duration.DAYS)) 
//        		binSize = Duration.BINSIZE_7_DAY;
//
//        	List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, binSize, duration.getDvqCount());
//            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);
//            
//            
//            Map<Integer, List<ScoreableEntity>> returnMap = new HashMap<Integer, List<ScoreableEntity>>();
//            for (GQVMap gqv : gqvList)
//            {
//                List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
//                for (DriveQMap driveQMap : gqv.getDriveQV())
//                {
//                    ScoreableEntity entity = new ScoreableEntity();
//                    entity.setEntityID(gqv.getGroup().getGroupID());
//                    entity.setEntityType(EntityType.ENTITY_GROUP);
//                    entity.setScore(driveQMap.getOverall() == null ? NO_SCORE : driveQMap.getOverall());
//                    entity.setScoreType(ScoreType.SCORE_OVERALL);
//                    entity.setDate(driveQMap.getEndingDate());
//                    scoreList.add(entity);
//                    
//                }
//                returnMap.put(gqv.getGroup().getGroupID(), scoreList);
//
//            }
//            returnMap.put(groupID, getTopGroupScoreList(groupID, duration));
//            return returnMap;
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyMap();
//        }
//*/        
//    	Integer binSize = Duration.BINSIZE_1_MONTH;
//    	if (duration.equals(Duration.DAYS)) 
//    		binSize = Duration.BINSIZE_7_DAY;
//
//    	Map<Integer, List<ScoreableEntity>> returnMap = new HashMap<Integer, List<ScoreableEntity>>();
//		List<Integer> groupIDList = new ArrayList<Integer>();
//		//	        groupIDList.add(groupID);
//	    groupIDList.addAll(getSubgroups(groupID));
//	
//	    List<Composite> rowKeys = new ArrayList<Composite>(); 
//	    for (Integer groupId : groupIDList)
//	    	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, binSize, duration.getDvqCount()));
//	    	
//	    CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(binSize), rowKeys);
//	
//	    //Calculate score tends for top group (District)
//	    Map<String, Map<String, Long>> scoreMap = (binSize == Duration.BINSIZE_1_MONTH) ? summarizeByDate(rows) : summarizeByWeek(rows, getDateStringfromDate(new Date()));
//		List<ScoreableEntity> scoreableEntityList = new ArrayList<ScoreableEntity>();
//	    for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
//	    {
//	    	ScoreableEntity scoreableEntity = new ScoreableEntity();
//	    	Map<String, Long> columnMap = entry.getValue();
//	        scoreableEntity.setEntityID(groupID);
//	        scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
//	        scoreableEntity.setScoreType(ScoreType.SCORE_OVERALL);
//	        
//	        
//	        scoreableEntity.setScore(getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
//	        scoreableEntity.setDate(getDatefromString(entry.getKey(), TimeZone.getDefault()));
//	        scoreableEntityList.add(scoreableEntity);
//	    }
//	    returnMap.put(groupID, scoreableEntityList);
//	    
//	    //Calculate score tends for each subgroup (Team)
//	    scoreMap = (binSize == Duration.BINSIZE_1_MONTH) ? summarizeByDateID(rows) : summarizeByWeekID(rows, getDateStringfromDate(new Date()));
//	    for (int currentGroupId : groupIDList)
//	    {
//			scoreableEntityList = new ArrayList<ScoreableEntity>();
//	        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
//	        {
//	        	int subGroupId = Integer.parseInt(getKeyPart(entry.getKey(), 2));
//	        	if (currentGroupId == subGroupId)
//	        	{
//		        	ScoreableEntity scoreableEntity = new ScoreableEntity();
//		        	Map<String, Long> columnMap = entry.getValue();
//		            scoreableEntity.setEntityID(subGroupId);
//		            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
//		            scoreableEntity.setScoreType(ScoreType.SCORE_OVERALL);
//		            scoreableEntity.setScore(getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
//		            scoreableEntity.setDate(getDatefromString(entry.getKey(), TimeZone.getDefault()));
//		            scoreableEntityList.add(scoreableEntity);
//	        	}
//	        }
//	        returnMap.put(groupID, scoreableEntityList);
//	    }
//    	return returnMap;
//    }
//    	
//
//      @Override
//    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Duration duration, ScoreType scoreType)
//    {
//		//Create quintileList
//		List<ScoreableEntity> quintileScoreList = new ArrayList<ScoreableEntity>();
//		for (int i = 0; i < 5; i++)
//		{
//		    ScoreableEntity entity = new ScoreableEntity();
//		    entity.setEntityID(groupID);
//		    entity.setEntityType(EntityType.ENTITY_GROUP);
//		    entity.setScoreType(scoreType);
//		    quintileScoreList.add(entity);
//		}
//
//        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
//        for (Driver driver : driverList)
//        {
//	        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), driver.getDriverID(), duration.getCode(), 1); //TO DO: set correct timezone
//	
//	        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverAggCF(duration.getCode()), rowKeys);
//	        Map<String, Map<String, Long>> driverMap = summarizeByID(rows);
//	        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
//	        {
//	        	int quintile = getScoreQuintile(getResultForScoreType(scoreType, entry.getValue()));
//	        	ScoreableEntity entity = quintileScoreList.get(quintile);
//	        	entity.setScore(entity.getScore()+1);
//	        }
//        }
//	    return quintileScoreList;
//    }
//	
//  	@Override
//    public List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType)
//    {
//        try
//        {
//
//            List<ScoreTypeBreakdown> scoreTypeBreakdownList = new ArrayList<ScoreTypeBreakdown>();
//            List<ScoreType> subTypeList = scoreType.getSubTypes();
//
//            for (ScoreType subType : subTypeList)
//            {
//                ScoreTypeBreakdown scoreTypeBreakdown = new ScoreTypeBreakdown();
//
//                scoreTypeBreakdown.setScoreType(subType);
//                scoreTypeBreakdown.setPercentageList(getScoreBreakdown(groupID, (subType.getDuration() == null) ? duration : subType.getDuration(), subType));
//                scoreTypeBreakdownList.add(scoreTypeBreakdown);
//            }
//            return scoreTypeBreakdownList;
//
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyList();
//        }
//    }
//	
//	@Override
//    public List<TrendItem> getTrendCumulative(Integer id, EntityType entityType, Duration duration)
//    {
///*		
//        try
//        {
//        	Integer binSize = Duration.BINSIZE_1_MONTH;
//        	if (duration.equals(Duration.DAYS)) 
//        		binSize = Duration.BINSIZE_7_DAY;
//
//        	List<Map<String, Object>> cumulativList = null;
//            if (entityType.equals(EntityType.ENTITY_DRIVER))
//            {
//            	cumulativList = reportService.getDTrendByDTC(id, binSize, duration.getDvqCount());
//            }
//            else
//            {
//            	cumulativList = reportService.getVTrendByVTC(id, binSize, duration.getDvqCount());
//            }
//            List<DriveQMap> driveQList = getMapper().convertToModelObject(cumulativList, DriveQMap.class);
//            
//            List<TrendItem> trendItemList = new ArrayList<TrendItem>();
//            for (DriveQMap driveQMap : driveQList)
//            {
//            	Map<ScoreType, Integer> scoreMap = driveQMap.getScoreMap();
//            	
//                for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
//                	TrendItem item = new TrendItem();
//                    item.setScoreType(scoreType);
//                    item.setScore(scoreMap.get(scoreType));
//                    item.setDate(driveQMap.getEndingDate());
//                    if(scoreType == ScoreType.SCORE_SPEEDING_21_30)
//                    	item.setDistance(driveQMap.getOdometer1());
//                    else if(scoreType == ScoreType.SCORE_SPEEDING_31_40)
//                    	item.setDistance(driveQMap.getOdometer2());
//                    else if(scoreType == ScoreType.SCORE_SPEEDING_41_54)
//                    	item.setDistance(driveQMap.getOdometer3());
//                    else if(scoreType == ScoreType.SCORE_SPEEDING_55_64)
//                    	item.setDistance(driveQMap.getOdometer4());
//                    else if(scoreType == ScoreType.SCORE_SPEEDING_65_80)
//                    	item.setDistance(driveQMap.getOdometer5());
//                    else
//                    	item.setDistance(driveQMap.getOdometer());
//                	trendItemList.add(item);
//            	}
//            }
//            return trendItemList;
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyList();
//        }
//  */
//    
//    	Integer binSize = Duration.BINSIZE_1_MONTH;
//    	if (duration.equals(Duration.DAYS)) 
//    		binSize = Duration.BINSIZE_7_DAY;
//    	
//    	return getTrendForAsset(id, entityType, binSize, duration.getDvqCount());
//
//    }
//    
//	
////    private void dumpMap(Map<String, Object> map) {
////    	for (String key : map.keySet())
////    	{
////    		System.out.println(key + " = " + map.get(key));
////    	}
////	}
//	
//	@Override
//    public List<TrendItem> getTrendScores(Integer id, EntityType entityType, Duration duration)
//    {
//		/*
//        try
//        {
//        	List<Map<String, Object>> dailyList = null; 
//            if (entityType.equals(EntityType.ENTITY_DRIVER))
//            {
//            	dailyList = reportService.getDTrendByDTC(id, duration.getAggregationBinSize(), duration.getDvqCount());
//            }
//            else
//            {
//            	dailyList = reportService.getVTrendByVTC(id, duration.getAggregationBinSize(), duration.getDvqCount());
//            }
//            List<DriveQMap> driveQList = getMapper().convertToModelObject(dailyList, DriveQMap.class);
//            
//            List<TrendItem> trendItemList = new ArrayList<TrendItem>();
//            for (DriveQMap driveQMap : driveQList)
//            {
//            	Map<ScoreType, Integer> scoreMap = driveQMap.getScoreMap();
//            	
//                for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
//                	TrendItem item = new TrendItem();
//                    item.setScoreType(scoreType);
//                    item.setScore(scoreMap.get(scoreType));
//                    item.setDate(driveQMap.getEndingDate());
//                    if(scoreType == ScoreType.SCORE_SPEEDING_21_30)
//                    	item.setDistance(driveQMap.getOdometer1());
//                    else if(scoreType == ScoreType.SCORE_SPEEDING_31_40)
//                    	item.setDistance(driveQMap.getOdometer2());
//                    else if(scoreType == ScoreType.SCORE_SPEEDING_41_54)
//                    	item.setDistance(driveQMap.getOdometer3());
//                    else if(scoreType == ScoreType.SCORE_SPEEDING_55_64)
//                    	item.setDistance(driveQMap.getOdometer4());
//                    else if(scoreType == ScoreType.SCORE_SPEEDING_65_80)
//                    	item.setDistance(driveQMap.getOdometer5());
//                    else
//                    	item.setDistance(driveQMap.getOdometer());
//                	trendItemList.add(item);
//            	}
//            }
//            return trendItemList;
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyList();
//        }
//        */
//        
//    	return getTrendForAsset(id, entityType, duration.getAggregationBinSize(), duration.getDvqCount());
//
//    }
//
//    private List<TrendItem> getTrendForAsset(Integer id, EntityType entityType, Integer binSize, Integer count)
//    {
//    	List<TrendItem> trendItemList = new ArrayList<TrendItem>();
//    	
//        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), id, binSize, count); //TO DO: set correct timezone
//
//        String cf = (entityType.equals(EntityType.ENTITY_DRIVER)) ? getDriverAggCF(binSize) : getVehicleAggCF(binSize); 
//        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
//        Map<String, Map<String, Long>> driverMap = (binSize == Duration.BINSIZE_1_MONTH) ? summarizeByDate(rows) : summarizeByWeekID(rows, getDateStringfromDate(new Date()));;
//        
//        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
//        {
//        	Map<String, Long> columnMap = entry.getValue();
//            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
//            	TrendItem item = new TrendItem();
//                item.setScoreType(scoreType);
//                item.setScore(getResultForScoreType(scoreType, columnMap));
//                item.setDate(getDatefromString(entry.getKey(), TimeZone.getDefault()));
//                if(scoreType == ScoreType.SCORE_SPEEDING_21_30)
//                	item.setDistance(getValue(columnMap,SPEEDODOMETER1));
//                else if(scoreType == ScoreType.SCORE_SPEEDING_31_40)
//                	item.setDistance(getValue(columnMap,SPEEDODOMETER2));
//                else if(scoreType == ScoreType.SCORE_SPEEDING_41_54)
//                	item.setDistance(getValue(columnMap,SPEEDODOMETER3));
//                else if(scoreType == ScoreType.SCORE_SPEEDING_55_64)
//                	item.setDistance(getValue(columnMap,SPEEDODOMETER4));
//                else if(scoreType == ScoreType.SCORE_SPEEDING_65_80)
//                	item.setDistance(getValue(columnMap,SPEEDODOMETER5));
//                else
//                	item.setDistance(getValue(columnMap,ODOMETER6));
//            	trendItemList.add(item);
//        	}
//        }
//        return trendItemList;
//    	
//    }
//
//	
//    @Override
//    public List<VehicleReportItem> getVehicleReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap)
//    {
//
//        List<VehicleReportItem> vehicleReportItemList = new ArrayList<VehicleReportItem>();
//        List<Vehicle> vehicleList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
//        for (Vehicle vehicle : vehicleList)
//        {
//	        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), vehicle.getVehicleID(), duration.getDvqCode(), 1); //TO DO: set correct timezone
//	        CounterRows<Composite, String> rows = fetchAggsForKeys(getVehicleAggCF(duration.getDvqCode()), rowKeys);
//	        Map<String, Map<String, Long>> vehicleMap = summarizeByID(rows);
//    
//	        VehicleReportItem vehicleReportItem = new VehicleReportItem();
//        	Group group = groupMap.get(vehicle.getGroupID());
//            vehicleReportItem.setGroupName((group != null) ? group.getName() : "");
//            vehicleReportItem.setGroupID(vehicle.getGroupID());
//        	vehicleReportItem.setVehicleName(vehicle.getName() != null ? vehicle.getName() : "");
//        	vehicleReportItem.setVehicleID(vehicle.getVehicleID());
//        	vehicleReportItem.setVehicleYMM(vehicle.getFullName());
//            vehicleReportItem.setOdometer(vehicle.getOdometer() != null ? vehicle.getOdometer() : 0);
//
//            
//            if (vehicleMap.size() > 0)
//	        {
//            	
//            	Driver driver = driverDAO.findByID(vehicle.getDriverID());
//
//                // May or may not have a driver assigned
//                if (driver != null)
//                {
//                	vehicleReportItem.setDriverID(driver.getDriverID());
//                	vehicleReportItem.setDriverName((driver.getPerson() != null) ? driver.getPerson().getFullName() : null);
//                }
//
//                
//    	        for (Map.Entry<String, Map<String, Long>> entry : vehicleMap.entrySet())
//    	        {
//    	        	Map<String, Long> columnMap = entry.getValue();
//    	        	vehicleReportItem.setMilesDriven(getValue(columnMap,ODOMETER6));
//    	        	vehicleReportItem.setOverallScore(getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
//    	        	vehicleReportItem.setSpeedScore(getResultForScoreType(ScoreType.SCORE_SPEEDING, columnMap));
//    	        	vehicleReportItem.setStyleScore(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE, columnMap));
//    	        }
//	        }
//	        else
//	        {
//	        	vehicleReportItem.setMilesDriven(0);
//	        	vehicleReportItem.setOverallScore(NO_SCORE);
//	        	vehicleReportItem.setSpeedScore(NO_SCORE);
//	        	vehicleReportItem.setStyleScore(NO_SCORE);
//	        	
//	        }
//            vehicleReportItemList.add(vehicleReportItem);
//        }
//
//        Collections.sort(vehicleReportItemList);
//        return vehicleReportItemList;
//    	
//    }
//
//    @Override
//    public List<DriverReportItem> getDriverReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap)
//    {
//        List<DriverReportItem> driverReportItemList = new ArrayList<DriverReportItem>();
//        List<Driver> driverList = driverDAO.getAllDrivers(groupID);
//        for (Driver driver : driverList)
//        {
//	        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), driver.getDriverID(), duration.getDvqCode(), 1); //TO DO: set correct timezone
//	
//	        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverAggCF(duration.getDvqCode()), rowKeys);
//	        Map<String, Map<String, Long>> driverMap = summarizeByID(rows);
//        	DriverReportItem driverReportItem = new DriverReportItem();
//        	Group group = groupMap.get(driver.getGroupID());
//            driverReportItem.setGroupName((group != null) ? group.getName() : "");
//            driverReportItem.setGroupID(driver.getGroupID());
//        	driverReportItem.setDriverID(driver.getDriverID());
//        	driverReportItem.setDriverName((driver.getPerson() != null) ? driver.getPerson().getFullName() : null);
//            driverReportItem.setEmployeeID((driver.getPerson() != null) ? driver.getPerson().getEmpid() : "");
//	        if (driverMap.size() > 0)
//	        {
//            	Vehicle vehicle = vehicleDAO.findByDriverID(driver.getDriverID());
//
//                // May or may not have a vehicle assigned
//                if (vehicle != null)
//                {
//                    driverReportItem.setVehicleID(vehicle.getVehicleID());
//                    driverReportItem.setVehicleName(vehicle.getName() != null ? vehicle.getName() : "");
//                }
//
//                
//    	        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
//    	        {
//    	        	Map<String, Long> columnMap = entry.getValue();
//	                driverReportItem.setMilesDriven(getValue(columnMap,ODOMETER6));
//	                driverReportItem.setOverallScore(getResultForScoreType(ScoreType.SCORE_OVERALL, columnMap));
//	                driverReportItem.setSpeedScore(getResultForScoreType(ScoreType.SCORE_SPEEDING, columnMap));
//	                driverReportItem.setStyleScore(getResultForScoreType(ScoreType.SCORE_DRIVING_STYLE, columnMap));
//	                driverReportItem.setSeatbeltScore(getResultForScoreType(ScoreType.SCORE_SEATBELT, columnMap));
//    	        }
//	        }
//	        else
//	        {
//                driverReportItem.setMilesDriven(0);
//                driverReportItem.setOverallScore(NO_SCORE);
//                driverReportItem.setSpeedScore(NO_SCORE);
//                driverReportItem.setStyleScore(NO_SCORE);
//                driverReportItem.setSeatbeltScore(NO_SCORE);
//	        	
//	        }
//            driverReportItemList.add(driverReportItem);
//        }
//
//        Collections.sort(driverReportItemList);
//        return driverReportItemList;
//    }
//
//    @Override
//    public List<ScoreItem> getAverageScores(Integer id, EntityType entityType, Duration duration)
//    {
///*        try
//        {
//            DriveQMap driveQMap = null;
//            if (entityType.equals(EntityType.ENTITY_DRIVER))
//            {
//            	driveQMap = getMapper().convertToModelObject(reportService.getDScoreByDT(id, duration.getDvqCode()), DriveQMap.class);
//            }
//            else
//            {
//            	driveQMap = getMapper().convertToModelObject(reportService.getVScoreByVT(id, duration.getDvqCode()), DriveQMap.class);
//            }
//            
//            List<ScoreItem> scoreItemList = new ArrayList<ScoreItem>();
//        	Map<ScoreType, Integer> scoreMap = driveQMap.getScoreMap();
//        	
//            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
//            	ScoreItem item = new ScoreItem();
//                item.setScoreType(scoreType);
//                item.setScore(scoreMap.get(scoreType));
//            	scoreItemList.add(item);
//        	}
//            return scoreItemList;
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyList();
//        }
//*/
//        List<ScoreItem> scoreItemList = new ArrayList<ScoreItem>();
//    	
//        List<Composite> rowKeys = createDateIDKeys(getToday(TimeZone.getDefault()), id, duration.getDvqCode(), 1); //TO DO: set correct timezone
//
//        String cf = (entityType.equals(EntityType.ENTITY_DRIVER)) ? getDriverAggCF(duration.getDvqCode()) : getVehicleAggCF(duration.getDvqCode()); 
//        CounterRows<Composite, String> rows = fetchAggsForKeys(cf, rowKeys);
//        Map<String, Map<String, Long>> driverMap = summarize(rows);
//        
//        for (Map.Entry<String, Map<String, Long>> entry : driverMap.entrySet())
//        {
//        	Map<String, Long> columnMap = entry.getValue();
//            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
//            	ScoreItem item = new ScoreItem();
//                item.setScoreType(scoreType);
//                item.setScore(getResultForScoreType(scoreType, columnMap));
//            	scoreItemList.add(item);
//        	}
//        }
//        return scoreItemList;
//    
//    }
//
//    @Override
//	public CrashSummary getGroupCrashSummaryData(Integer groupID) {
///*		try {
//			
//	        Map<String, Object> returnMap = reportService.getGDScoreByGT(groupID, Duration.TWELVE.getCode());
//	        DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);
//	        
//	        CrashSummary crashSummary = new CrashSummary(
//	        					dqMap.getCrashEvents() == null ? 0 : dqMap.getCrashEvents(), 
//	        					dqMap.getCrashTotal() == null ? 0 : dqMap.getCrashTotal(), 
//	        					dqMap.getCrashDays() == null ? 0 : dqMap.getCrashDays(),
//	        					dqMap.getOdometer() == null ? 0 : dqMap.getOdometer().doubleValue()/100.0, 
//	        					dqMap.getCrashOdometer() == null ? 0 : dqMap.getCrashOdometer().doubleValue()/100.0);
//	        return crashSummary;
//		}
//		catch (EmptyResultSetException e)
//        {
//	        return new CrashSummary(0,0,0,0,0);
//        }
//*/        
//        return new CrashSummary(0,0,0,0,0);
//	}
//
//	@Override
//	public CrashSummary getDriverCrashSummaryData(Integer driverID) {
///*
//		try {
//	        Map<String, Object> returnMap = reportService.getDScoreByDT(driverID, Duration.TWELVE.getCode());
//	        DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);
//	        CrashSummary crashSummary = new CrashSummary(
//					dqMap.getCrashEvents() == null ? 0 : dqMap.getCrashEvents(), 
//					dqMap.getCrashTotal() == null ? 0 : dqMap.getCrashTotal(), 
//					dqMap.getCrashDays() == null ? 0 : dqMap.getCrashDays(),
//					dqMap.getOdometer() == null ? 0 : dqMap.getOdometer().doubleValue()/100.0, 
//					dqMap.getCrashOdometer() == null ? 0 : dqMap.getCrashOdometer().doubleValue()/100.0);
//	         
//	        return crashSummary;
//		}
//		catch (EmptyResultSetException e)
//	    {
//	        return new CrashSummary(0,0,0,0,0);
//	    }
//*/
//        return new CrashSummary(0,0,0,0,0);
//
//	}
//
//	@Override
//	public CrashSummary getVehicleCrashSummaryData(Integer vehicleID) {
///*
//		try {
//	        Map<String, Object> returnMap = reportService.getVScoreByVT(vehicleID, Duration.TWELVE.getCode());
//	        DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);
//	        CrashSummary crashSummary = new CrashSummary(
//					dqMap.getCrashEvents() == null ? 0 : dqMap.getCrashEvents(), 
//					dqMap.getCrashTotal() == null ? 0 : dqMap.getCrashTotal(), 
//					dqMap.getCrashDays() == null ? 0 : dqMap.getCrashDays(),
//					dqMap.getOdometer() == null ? 0 : dqMap.getOdometer().doubleValue()/100.0, 
//					dqMap.getCrashOdometer() == null ? 0 : dqMap.getCrashOdometer().doubleValue()/100.0);
//	               
//	        return crashSummary;
//		}
//		catch (EmptyResultSetException e)
//	    {
//	        return new CrashSummary(0,0,0,0,0);
//	    }
//*/
//        return new CrashSummary(0,0,0,0,0);
//
//	}
//
//	@Override
//	public List<SpeedPercentItem> getSpeedPercentItems(Integer groupID, Duration duration) {
//  /*      try
//        {
//            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, duration.getAggregationBinSize(), duration.getDvqCount());
//            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);
//
//            List<SpeedPercentItem> speedPercentItemList = new ArrayList<SpeedPercentItem>();
//            boolean first = true;
//            for (GQVMap gqv : gqvList)
//            {
//                for (DriveQMap driveQMap : gqv.getDriveQV())
//                {
//                	Long distance = (driveQMap.getOdometer() == null) ? 0l : driveQMap.getOdometer().longValue();
//                	Long speeding = (driveQMap.getSpeedOdometer()== null) ? 0 : driveQMap.getSpeedOdometer().longValue();
//                	Date date = driveQMap.getEndingDate();
//                	if (first)
//                	{
//                		speedPercentItemList.add(new SpeedPercentItem(distance, speeding, date));
//                	}
//                	else
//                	{
//                   		if (date == null)
//                			continue;
//	            		for (SpeedPercentItem item : speedPercentItemList)
//                		{
//	                   		if (item.getDate() == null)
//	                			continue;
//	            			if (item.getDate().equals(date))
//                			{
//                				item.setMiles(distance + item.getMiles().longValue());
//                				item.setMilesSpeeding(speeding + item.getMilesSpeeding().longValue());
//                				break;
//                			}
//                		}
//                	}
//                }
//                
//                first = false;
//            }
//            
//            Collections.sort(speedPercentItemList);
//            return speedPercentItemList;
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyList();
//        }
//        
//*/        
//        Date currentDate = new Date();
//        List<SpeedPercentItem> speedPercentItemList = new ArrayList<SpeedPercentItem>();
//        List<Integer> groupIDList = new ArrayList<Integer>();
////        groupIDList.add(groupID);
//        groupIDList.addAll(getSubgroups(groupID));
//
//        List<Composite> rowKeys = new ArrayList<Composite>(); 
//        for (Integer groupId : groupIDList)
//        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, duration.getAggregationBinSize(), duration.getDvqCount()));
//        	
//        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(duration.getAggregationBinSize()), rowKeys);
//        Map<String, Map<String, Long>> scoreMap = (duration.getAggregationBinSize() == Duration.BINSIZE_7_DAY) ? summarizeByWeek(rows, getDateStringfromDate(currentDate)) : summarizeByDate(rows);
//        
//        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
//        {
//        	Date endDate = getDatefromString(entry.getKey(), TimeZone.getDefault());  //TODO fix timezone
//        	Map<String, Long> columnMap = entry.getValue();
//        	Long distance = getValue(columnMap,ODOMETER6);
//        	Long speeding = getValue(columnMap,SPEEDODOMETER1) + getValue(columnMap,SPEEDODOMETER2) + getValue(columnMap,SPEEDODOMETER3) + getValue(columnMap,SPEEDODOMETER4) + getValue(columnMap,SPEEDODOMETER5);
//       		speedPercentItemList.add(new SpeedPercentItem(distance, speeding, endDate));
//        }
//        Collections.sort(speedPercentItemList);
//        return speedPercentItemList;
//	}
//
//	@Override
//	public List<IdlePercentItem> getIdlePercentItems(Integer groupID, Duration duration) {
///*        try
//        {
//            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, duration.getAggregationBinSize(), duration.getDvqCount());
//            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);
//            List<IdlePercentItem> idlePercentItemList = new ArrayList<IdlePercentItem>();
//            
//            boolean first = true;
//            for (GQVMap gqv : gqvList)
//            {
//                for (DriveQMap driveQMap : gqv.getDriveQV())
//                {
//                	Long driveTime = (driveQMap.getEmuRpmDriveTime() != null) ? driveQMap.getEmuRpmDriveTime().longValue() : 0l; 
//                	Long idleTime = (driveQMap.getIdleHi() != null) ? driveQMap.getIdleHi().longValue() : 0l;
//                	idleTime += (driveQMap.getIdleLo() != null) ? driveQMap.getIdleLo().longValue(): 0l; 
//                	Integer numVehicles = (driveQMap.getnVehicles() != null) ? driveQMap.getnVehicles() : 0;
//                	Integer numEMUVehicles = (driveQMap.getEmuRpmVehicles() != null) ? driveQMap.getEmuRpmVehicles() : 0;
//                	Date date = driveQMap.getEndingDate();
//                	if (first)
//                	{
//                    	idlePercentItemList.add(new IdlePercentItem(driveTime, idleTime, date, numVehicles, numEMUVehicles));
//                	}
//                	else
//                	{
//                		if (date == null)
//                			continue;
//	                	for (IdlePercentItem item : idlePercentItemList)
//	                	{
//	                		if (item.getDate() == null)
//	                			continue;
//	                		if (item.getDate().equals(date))
//	                		{
//	                			item.setDrivingTime(driveTime + item.getDrivingTime());
//	                			item.setIdlingTime(idleTime + item.getIdlingTime()); 
//	                			item.setNumVehicles(numVehicles + item.getNumVehicles());
//	                			item.setNumEMUVehicles(numEMUVehicles + item.getNumEMUVehicles());
//	                			break;
//	                		}
//	                	}
//                	}
//                }
//                first = false;
//
//            }
//            Collections.sort(idlePercentItemList);
//            
//            return idlePercentItemList;
//        }
//        catch (EmptyResultSetException e)
//        {
//            return Collections.emptyList();
//        }
//*/
//        Date currentDate = new Date();
//        List<IdlePercentItem> idlePercentItemList = new ArrayList<IdlePercentItem>();
//        List<Integer> groupIDList = new ArrayList<Integer>();
////        groupIDList.add(groupID);
//        groupIDList.addAll(getSubgroups(groupID));
//
//        List<Composite> rowKeys = new ArrayList<Composite>(); 
//        for (Integer groupId : groupIDList)
//        	rowKeys.addAll(createDateIDKeys(getToday(TimeZone.getDefault()), groupId, duration.getAggregationBinSize(), duration.getDvqCount()));
//        	
//        CounterRows<Composite, String> rows = fetchAggsForKeys(getDriverGroupAggCF(duration.getAggregationBinSize()), rowKeys);
//        Map<String, Map<String, Long>> scoreMap = (duration.getAggregationBinSize() == Duration.BINSIZE_7_DAY) ? summarizeByWeek(rows, getDateStringfromDate(currentDate)) : summarizeByDate(rows);
//        
//        for (Map.Entry<String, Map<String, Long>> entry : scoreMap.entrySet())
//        {
//        	Date endDate = getDatefromString(entry.getKey(), TimeZone.getDefault());  //TODO fix timezone
//        	Map<String, Long> columnMap = entry.getValue();
//
//        	Long driveTime = getValue(columnMap,DRIVETIME); 
//        	Long idleTime = getValue(columnMap,IDLELO) + getValue(columnMap,IDLEHI);
//        	Integer numVehicles = 0;  //TODO
//        	Integer numEMUVehicles = 0; //TODO
//           	idlePercentItemList.add(new IdlePercentItem(driveTime, idleTime, endDate, numVehicles, numEMUVehicles));
//        }
//        Collections.sort(idlePercentItemList);
//        return idlePercentItemList;
//
//	}
//
//
//
//    private Group getGroup(Integer groupID)
//	{
//		Group group = groupDAO.findByID(groupID);
//		return group;
//	}
//
//	private List<Integer> getSubgroups(Integer groupID)
//	{
//		List<Group> groupList = groupDAO.getGroupHierarchy(-1, groupID);
//		List<Integer> groupIDList = new ArrayList<Integer>();  
//		for (Group group : groupList)
//			groupIDList.add(group.getGroupID());
//
//		return groupIDList;
//	}
//
//	private List<Composite> createDateIDKeys(String aggDate, Integer ID, int binSize, int count)
//	{
//		List<Composite> dateIDList = new ArrayList<Composite>();
//		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
//	    int year = Integer.parseInt(aggDate.substring(0,4));
//	    int month = Integer.parseInt(aggDate.substring(5,7));
//	    int day = Integer.parseInt(aggDate.substring(8,10));
//	    cal.set(year, month-1, day, 0, 0, 0);
//
//
//        int period = Duration.getCalendarPeriod(binSize);
//        int amount = -1;
//        switch (binSize)
//        {
//	        case Duration.BINSIZE_1_DAY:
//	        	amount = (1*count)-1;
//	        	break;
//	        case Duration.BINSIZE_7_DAY:
//	        	amount = (7*count)-1;
//	        	break;
//	        case Duration.BINSIZE_30_DAYS:
//	        	amount = (30*count)-1;
//	        	break;
//
//	        case Duration.BINSIZE_1_MONTH:
//	        	amount = (1*count)-1;
//	        	break;
//	        case Duration.BINSIZE_3_MONTHS:
//	        	amount = (3*count)-1;
//	        	break;
//	        case Duration.BINSIZE_6_MONTHS:
//	        	amount = (6*count)-1;
//	        	break;
//	        case Duration.BINSIZE_12_MONTHS:
//	        	amount = (12*count)-1;
//	        	break;
//        }
//
//        
//        if (amount != -1)
//        {
//        	String format = "yyyy-MM-dd";
//        	if (period == Calendar.MONTH)
//        		format = "yyyy-MM";
//        	
//        	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        	for(; amount >= 0; amount-- )
//        	{
//    			Composite key = new Composite();
//    			key.add(0, dateFormat.format(cal.getTime().getTime()));
//    			key.add(1, ID);
//    			dateIDList.add(key);
//        		cal.add(period, -1);
//        	}
//        	
//        	
//        }
//        return dateIDList;
//	}
//    
//    private CounterRows<Composite, String> fetchAggsForKeys(String AGG_CF, List<Composite> keys)
//    {
//        MultigetSliceCounterQuery<Composite, String> sliceQuery = HFactory.createMultigetSliceCounterQuery(getKeyspace(), compositeSerializer, stringSerializer);
//        
//        sliceQuery.setColumnFamily(AGG_CF);            
//        sliceQuery.setRange("", "", false, 1000);  //get all the columns
//        sliceQuery.setKeys(keys);
//        
//        QueryResult<CounterRows<Composite, String>> result = sliceQuery.execute();
//        CounterRows<Composite, String> rows = result.get();            
//        return rows;
//		
//    }
//    
//    Map<String, Map<String, Long>>  summarizeByID(CounterRows<Composite, String> rows)
//    {
//    	return summarize(rows, false, true, false, "");
//    }
//    
//    Map<String, Map<String, Long>>  summarizeByDate(CounterRows<Composite, String> rows)
//    {
//    	return summarize(rows, true, false, false, "");
//    }
//
//    Map<String, Map<String, Long>>  summarizeByDateID(CounterRows<Composite, String> rows)
//    {
//    	return summarize(rows, true, true, false, "");
//    }
//
//    Map<String, Map<String, Long>>  summarizeByWeekID(CounterRows<Composite, String> rows, String endDate)
//    {
//    	return summarize(rows, true, true, true, endDate);
//    }
//
//    Map<String, Map<String, Long>>  summarizeByWeek(CounterRows<Composite, String> rows, String endDate)
//    {
//    	return summarize(rows, true, false, true, endDate);
//    }
//
//    Map<String, Map<String, Long>>  summarize(CounterRows<Composite, String> rows)
//    {
//    	return summarize(rows, false, false, false, "");
//    }
//
//    private Map<String, Map<String, Long>> summarize(CounterRows<Composite, String> rows, boolean includeAggDate, boolean includeID, boolean bucketByWeek, String endDate)
//    {
//    	//An Aggregate composite rowkey consists of 1.) AggDate (2.) ID of either driver,vehicle,group
//        Map<String, Map<String, Long>>keys2ColumnsMap = new HashMap<String, Map<String, Long>>();
//
//        for (CounterRow<Composite, String> row : rows)
//        {
//        	String key = "";
//        	
////        	String aggDate = row.getKey().getComponent(0).toString();
//        	String aggDate = stringSerializer.fromByteBuffer((ByteBuffer) row.getKey().get(0));
//
//        	if (bucketByWeek)
//        		aggDate = convertDayToWeek(endDate, aggDate);
//        	
//        	
//        	String id = bigIntegerSerializer.fromByteBuffer((ByteBuffer) row.getKey().get(1)).toString();
//        	
//        	if (includeAggDate)
//        		key += aggDate;
//        	
//        	if (includeID)
//        	{
//        		if (!key.isEmpty())
//        			key += ":";
//        		key += id;
//        	}
//
//
//        	Map<String, Long> columnMap = (Map<String, Long>) keys2ColumnsMap.get(key);
//        	if (columnMap == null)
//        		columnMap = new HashMap<String, Long>();
//
//			CounterSlice<String> columnSlice = row.getColumnSlice();
//        	List<HCounterColumn<String>> columnList = columnSlice.getColumns();
//        	for (HCounterColumn<String> column : columnList)
//        	{
//    			Long value = getValue(columnMap,column.getName());
//    			if (value == null)
//    				value = 0L;
//    			value += column.getValue();
//    			
//    			columnMap.put(column.getName(), value);
//        	}
//			keys2ColumnsMap.put(key, columnMap);
//        }
//        return keys2ColumnsMap;
//    }
//    
//    private Integer getResultForScoreType(ScoreType scoreType, Map<String, Long> columnMap)
//    {
//    	Double score = 0.0;
//    	long totalMiles = getValue(columnMap,ODOMETER6)/100;
//
//    	long speedPenalty = 0;
//    	long stylePenalty = 0;
//    	long seatbeltPenalty = 0;
//    	
//    	switch (scoreType)
//    	{
//    		case SCORE_OVERALL:
//    	    	speedPenalty = getValue(columnMap, SPEEDPENALTY1)+getValue(columnMap, SPEEDPENALTY2)+getValue(columnMap, SPEEDPENALTY3)+getValue(columnMap, SPEEDPENALTY4)+getValue(columnMap, SPEEDPENALTY5);
//    	    	stylePenalty = getValue(columnMap,AGGRESSIVEBRAKEPENALTY)+getValue(columnMap, AGGRESSIVEACCELPENALTY)+getValue(columnMap,AGGRESSIVELEFTPENALTY)+getValue(columnMap,AGGRESSIVERIGHTPENALTY)+getValue(columnMap,AGGRESSIVEBUMPPENALTY);
//    	    	seatbeltPenalty = getValue(columnMap,SEATBELTPENALTY);
//    	    	score = ScoringFormulas.getOverallFromPenalty(totalMiles, convertLong2Double(seatbeltPenalty), convertLong2Double(stylePenalty), convertLong2Double(speedPenalty));
//    	    	break;
//    		case SCORE_SPEEDING:
//    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY1)+getValue(columnMap,SPEEDPENALTY2)+getValue(columnMap,SPEEDPENALTY3)+getValue(columnMap,SPEEDPENALTY4)+getValue(columnMap,SPEEDPENALTY5);
//    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
//    			break;
//    		case SCORE_SEATBELT:
//    	    	seatbeltPenalty = getValue(columnMap,SEATBELTPENALTY);
//    	    	score = ScoringFormulas.getSeatBeltScore(totalMiles, convertLong2Double(seatbeltPenalty));
//    			break;
//    		case SCORE_DRIVING_STYLE:
//    	    	stylePenalty = getValue(columnMap,AGGRESSIVEBRAKEPENALTY)+getValue(columnMap,AGGRESSIVEACCELPENALTY)+getValue(columnMap,AGGRESSIVELEFTPENALTY)+getValue(columnMap,AGGRESSIVERIGHTPENALTY)+getValue(columnMap,AGGRESSIVEBUMPPENALTY);
//    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
//    		case SCORE_COACHING_EVENTS:
//    	    	score = (double)getValue(columnMap,SPEEDCOACHING1)+getValue(columnMap,SPEEDCOACHING2)+getValue(columnMap,SPEEDCOACHING3)+getValue(columnMap,SPEEDCOACHING4)+getValue(columnMap,SPEEDCOACHING5);
//    	    // subTypes of SPEEDING
//    		case SCORE_SPEEDING_21_30:
//    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY1);
//    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
//    			break;
//    		case SCORE_SPEEDING_31_40:
//    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY2);
//    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
//    			break;
//    		case SCORE_SPEEDING_41_54:
//    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY3);
//    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
//    			break;
//    		case SCORE_SPEEDING_55_64:
//    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY4);
//    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
//    			break;
//    		case SCORE_SPEEDING_65_80:
//    	    	speedPenalty = getValue(columnMap,SPEEDPENALTY5);
//    	    	score = ScoringFormulas.getSpeedingScore(totalMiles, convertLong2Double(speedPenalty));
//    			break;
//    		case SCORE_SEATBELT_30_DAYS:
//    		case SCORE_SEATBELT_3_MONTHS:
//    		case SCORE_SEATBELT_6_MONTHS:
//    		case SCORE_SEATBELT_12_MONTHS:
//    	    // subTypes of DRIVING_STYLE
//    		case SCORE_DRIVING_STYLE_HARD_BRAKE:
//    	    	stylePenalty = getValue(columnMap,AGGRESSIVEBRAKEPENALTY);
//    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
//    	    	break;
//    		case SCORE_DRIVING_STYLE_HARD_ACCEL:
//    	    	stylePenalty = getValue(columnMap,AGGRESSIVEACCELPENALTY);
//    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
//    	    	break;
//    		case SCORE_DRIVING_STYLE_HARD_TURN:
//    	    	stylePenalty = getValue(columnMap,AGGRESSIVELEFTPENALTY) + getValue(columnMap,AGGRESSIVERIGHTPENALTY);
//    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
//    	    	break;
//    		case SCORE_DRIVING_STYLE_HARD_BUMP:
//    	    	stylePenalty = getValue(columnMap,AGGRESSIVEBUMPPENALTY);
//    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
//    	    	break;
//    		case SCORE_DRIVING_STYLE_HARD_LTURN:
//    	    	stylePenalty = getValue(columnMap,AGGRESSIVELEFTPENALTY);
//    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
//    	    	break;
//    		case SCORE_DRIVING_STYLE_HARD_RTURN:
//    	    	stylePenalty = getValue(columnMap,AGGRESSIVERIGHTPENALTY);
//    	    	score = ScoringFormulas.getStyleScore(totalMiles, convertLong2Double(stylePenalty));
//    	    	break;
//    		
//    	}
//    	return (int) Math.round(score);
//    }
//    
//    private String getKeyPart(String key, int part)
//    {
//    	String keyPart = "";
//    	if (part == 1)
//    		keyPart = key.substring(0, key.indexOf(":") - 1);  
//    	if (part == 2)
//    		keyPart = key.substring(key.indexOf(":") + 1);
//    	
//    	return keyPart;
//    }
//    
//    private Date getEndingDate(String strDate)
//    {
//		//We are converting a Trip Day (UTC) into a Driver Day (Driver TZ)
//	    GregorianCalendar cal = new GregorianCalendar();
////	    cal.setTimeZone(driverTZ);
//	    int year = Integer.parseInt(strDate.substring(0,4));
//	    int month = Integer.parseInt(strDate.substring(5,7));
//	    int day = (strDate.length() < 8) ? 1 :Integer.parseInt(strDate.substring(8,10));
//	    cal.set(year, month-1, day, 0, 0, 0);
//
//	    if (strDate.length() < 8)
//	    	cal.add(Calendar.DATE, 1);
//	    else
//	    	cal.add(Calendar.MONTH, 1);
//
//	    return cal.getTime();
//    }
//    
//	private double convertLong2Double(long penaltyAsDouble)
//	{
//		return penaltyAsDouble/1000000D;
//	}
//
//
//	private String getDriverAggCF(Integer binSize)
//	{
//	    String aggCF = AGGDRIVER_CF;
//	    if (Duration.getCalendarPeriod(binSize) == Calendar.MONTH)
//	    	aggCF = AGGDRIVERMONTH_CF;
//	    return aggCF;
//	}
//
//	private String getVehicleAggCF(Integer binSize)
//	{
//	    String aggCF = AGGVEHICLE_CF;
//	    if (Duration.getCalendarPeriod(binSize) == Calendar.MONTH)
//	    	aggCF = AGGVEHICLEMONTH_CF;
//	    return aggCF;
//	}
//
//	private String getDriverGroupAggCF(Integer binSize)
//	{
//	    String aggCF = AGGDRIVERGROUP_CF;
//	    if (Duration.getCalendarPeriod(binSize) == Calendar.MONTH)
//	    	aggCF = AGGDRIVERGROUPMONTH_CF;
//	    return aggCF;
//	}
//
//
//	private int getScoreQuintile(int score)
//	{
//	  	if (score >= 41)
//	  		return 4;
//	  	else if (score >= 31)
//	  		return 3;
//	  	else if (score >= 21)
//	  		return 2;
//	  	else if (score >= 11)
//	  		return 1;
//	  	else return 0;
//	}
//
//	private String getToday(TimeZone tz)
//	{
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setTimeZone(tz);
//        return dateFormat.format(new Date());
//	}
//
//	private Date getDatefromString(String strDate, TimeZone tz)
//	{
//		//if passing in a month
//		if (strDate.length() < 8)
//			strDate += "-01";
//			
//	    Calendar cal = new GregorianCalendar();
//	    cal.setTimeZone(tz);
//	    int year = Integer.parseInt(strDate.substring(0,4));
//	    int month = Integer.parseInt(strDate.substring(5,7));
//	    int day = Integer.parseInt(strDate.substring(8,10));
//	    cal.set(year, month-1, day, 0, 0, 0);
//	    return cal.getTime();
//	}
//	
//	private String getDateStringfromDate(Date date)
//	{
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        return dateFormat.format(date);
//	}
//	
//	private String convertDayToWeek(String endDate, String date)
//	{
//		int DAY_IN_MILLISECS = (24 * 60 * 60 * 1000);
//		
//	    Calendar endCal = Calendar.getInstance();
//	    int year = Integer.parseInt(endDate.substring(0,4));
//	    int month = Integer.parseInt(endDate.substring(5,7));
//	    int day = Integer.parseInt(endDate.substring(8,10));
//	    endCal.set(year, month-1, day, 0, 0, 0);
//	    
//	    Calendar cal = Calendar.getInstance();
//	    year = Integer.parseInt(date.substring(0,4));
//	    month = Integer.parseInt(date.substring(5,7));
//	    day = Integer.parseInt(date.substring(8,10));
//	    cal.set(year, month-1, day, 0, 0, 0);
//	    
//	    int dayDiff = (int) (endCal.getTimeInMillis() - cal.getTimeInMillis())/DAY_IN_MILLISECS;
//	    int weekDiff = (int) Math.floor(dayDiff/7.0);
//	    
//	    endCal.add(Calendar.DATE, 7*weekDiff*-1);
//	    
//        return getDateStringfromDate(endCal.getTime());
//	}
//
//	private long getValue(Map<String, Long> columnMap, String column)
//	{
//		Long value = columnMap.get(column);
//		if (value == null)
//			value = 0L;
//		
//		return value;
//	}
//}
