package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GQVMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.QuintileMap;
import com.inthinc.pro.model.ScoreItem;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SpeedPercentItem;
import com.inthinc.pro.model.TrendItem;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;

public class ScoreHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer> implements ScoreDAO
{
    private static final Logger logger = Logger.getLogger(ScoreHessianDAO.class);
    private static final Integer NO_SCORE = -1;

    private ReportService reportService;

    public ReportService getReportService()
    {
        return reportService;
    }

    public void setReportService(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @Override
    public List<DriverScore> getSortedDriverScoreList(Integer groupID, Duration duration, GroupHierarchy gh)
    {
    	logger.debug("getSortedDriverScoreList: " + groupID);
        try
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
    }

    @Override
    public ScoreableEntity getAverageScoreByType(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getAverageScoreByType: " + groupID);
        try
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
    }
    @Override
    public ScoreableEntity getSummaryScore(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getSummaryScore: " + groupID);
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
    }
    @Override
    public List<ScoreableEntity> getScores(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getScores: " + groupID);
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
    }
    
    @Override
    public ScoreableEntity getTrendSummaryScore(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	return this.getAverageScoreByType(groupID, duration, scoreType, gh);
    }

    @Override
    public Map<Integer, List<ScoreableEntity>> getTrendScores(Integer groupID, Duration duration, GroupHierarchy gh)
    {
    	logger.debug("getTrendScores: " + groupID);
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
    }

    	
      private List<ScoreableEntity> getTopGroupScoreList(Integer topGroupID, Duration duration) {
        
        
      	Integer binSize = Duration.BINSIZE_1_MONTH;
    	if (duration.equals(Duration.DAYS)) 
    		binSize = Duration.BINSIZE_7_DAY;
        
        List<Map<String, Object>> topGroupMap = null;
        try {
        	topGroupMap = reportService.getGDTrendByGTC(topGroupID, binSize, duration.getDvqCount());
        }
        catch (EmptyResultSetException e) {
        	return new ArrayList<ScoreableEntity>();
        }

        List<DriveQMap> topGroupList = getMapper().convertToModelObject(topGroupMap, DriveQMap.class);
        
        List<ScoreableEntity> topGroupScoreList = new ArrayList<ScoreableEntity>();
        for (DriveQMap driveQMap : topGroupList)
        {
            ScoreableEntity entity = new ScoreableEntity();
            entity.setEntityID(topGroupID);
            entity.setEntityType(EntityType.ENTITY_GROUP);
            entity.setScore(driveQMap.getOverall() == null ? NO_SCORE : driveQMap.getOverall());
            entity.setScoreType(ScoreType.SCORE_OVERALL);
            entity.setDate(driveQMap.getEndingDate());
            topGroupScoreList.add(entity);
            
        }
              
		return topGroupScoreList;
	}

	@Override
    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getScoreBreakdown: " + groupID);
        try
        {
            Map<String, Object> returnMap = reportService.getDPctByGT(groupID, duration.getCode(), scoreType.getDriveQMetric());

            QuintileMap quintileMap = getMapper().convertToModelObject(returnMap, QuintileMap.class);

//            StringBuilder debugBuffer = new StringBuilder();
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
            for (Integer score : quintileMap.getPercentList())
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(groupID);
                entity.setEntityType(EntityType.ENTITY_GROUP);
                entity.setScore(score);
                entity.setScoreType(scoreType);
                scoreList.add(entity);
//                debugBuffer.append(score + ",");
            }
//            logger.debug("getScoreBreakdown: groupID[" + groupID + "] durationCode[" + duration.getCode() + "] metric[" + scoreType.getDriveQMetric() + "] scores["
//                    + debugBuffer.toString() + "]");

            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
	
	
	@Override
    public List<TrendItem> getTrendCumulative(Integer id, EntityType entityType, Duration duration)
    {
    	logger.debug("getTrendCumulative: " + id);
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
        	logger.debug("TrendItemList count(): " + trendItemList.size());
            return trendItemList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
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
    	logger.debug("getTrendScores: " + id);
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
    }
    @Override
    public List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType, GroupHierarchy gh)
    {
    	logger.debug("getScoreBreakdownByType: " + groupID);
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
    public List<VehicleReportItem> getVehicleReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap)
    {
    	logger.debug("getVehicleReportData: " + groupID);
        try
        {
        	List<Map<String, Object>> list = reportService.getVDScoresByGT(groupID, duration.getDvqCode());
            List<DVQMap> result = getMapper().convertToModelObject(list, DVQMap.class);
            List<VehicleReportItem> vehicleReportItemList = new ArrayList<VehicleReportItem>();

            for (DVQMap d : result)
            {
            	VehicleReportItem vehicleReportItem = new VehicleReportItem();
                Vehicle vehicle = d.getVehicle();
                Driver driver = d.getDriver();
                DriveQMap dqm = d.getDriveQ();

                if (driver != null) {
                	vehicleReportItem.setDriverID(driver.getDriverID());
                	vehicleReportItem.setDriverName((driver.getPerson() != null) ? driver.getPerson().getFullName() : null);
                }
                if (vehicle != null) {
                	Group group = groupMap.get(vehicle.getGroupID());
                    vehicleReportItem.setGroupName((group != null) ? group.getName() : "");
                    vehicleReportItem.setGroupID(vehicle.getGroupID());
                	vehicleReportItem.setVehicleName(vehicle.getName() != null ? vehicle.getName() : "");
                	vehicleReportItem.setVehicleID(vehicle.getVehicleID());
                	vehicleReportItem.setVehicleYMM(vehicle.getFullName());
                    vehicleReportItem.setOdometer(vehicle.getOdometer() != null ? vehicle.getOdometer() : 0);
                }

                vehicleReportItem.setMilesDriven((dqm.getOdometer() == null ? 0d : dqm.getOdometer()));
                vehicleReportItem.setOverallScore(dqm.getOverall() == null ? NO_SCORE : dqm.getOverall());
                vehicleReportItem.setSpeedScore(dqm.getSpeeding() == null ? NO_SCORE : dqm.getSpeeding());
                vehicleReportItem.setStyleScore(dqm.getDrivingStyle() == null ? NO_SCORE : dqm.getDrivingStyle());
                

                vehicleReportItemList.add(vehicleReportItem);
            }

            Collections.sort(vehicleReportItemList); // Sort by vehicle.name

            return vehicleReportItemList;

        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }

    @Override
    public List<DriverReportItem> getDriverReportData(Integer groupID, Duration duration, Map<Integer, Group> groupMap)
    {
    	logger.debug("getDriverReportData: " + groupID);
        try
        {
            List<DVQMap> result = getMapper().convertToModelObject(reportService.getDVScoresByGT(groupID, duration.getDvqCode()), DVQMap.class);
            List<DriverReportItem> driverReportItemList = new ArrayList<DriverReportItem>();

            for (DVQMap dvq : result)
            {
            	DriverReportItem driverReportItem = new DriverReportItem();
                Driver driver = dvq.getDriver();
                Vehicle vehicle = dvq.getVehicle();
                DriveQMap driverQMap = dvq.getDriveQ();


                if (driver != null) {
                	Group group = groupMap.get(driver.getGroupID());
                    driverReportItem.setGroupName((group != null) ? group.getName() : "");
                    driverReportItem.setGroupID(driver.getGroupID());
                	driverReportItem.setDriverID(driver.getDriverID());
                	driverReportItem.setDriverName((driver.getPerson() != null) ? driver.getPerson().getFullName() : null);
                    driverReportItem.setEmployeeID((driver.getPerson() != null) ? driver.getPerson().getEmpid() : "");
                }

                // May or may not have a vehicle assigned
                if (vehicle != null)
                {
                    driverReportItem.setVehicleID(vehicle.getVehicleID());
                    driverReportItem.setVehicleName(vehicle.getName() != null ? vehicle.getName() : "");
                }
                driverReportItem.setMilesDriven(driverQMap.getOdometer() == null ? 0d : driverQMap.getOdometer());
                driverReportItem.setOverallScore(driverQMap.getOverall() == null ? NO_SCORE : driverQMap.getOverall());
                driverReportItem.setSpeedScore(driverQMap.getSpeeding() == null ? NO_SCORE : driverQMap.getSpeeding());
                driverReportItem.setStyleScore(driverQMap.getDrivingStyle() == null ? NO_SCORE : driverQMap.getDrivingStyle());
                driverReportItem.setSeatbeltScore(driverQMap.getSeatbelt() == null ? NO_SCORE : driverQMap.getSeatbelt());

                driverReportItemList.add(driverReportItem);
            }

            Collections.sort(driverReportItemList);

            return driverReportItemList;

        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }
	@Override
    public List<ScoreItem> getAverageScores(Integer id, EntityType entityType, Duration duration)
    {
    	logger.debug("getAverageScores: " + id);
        try
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
    }
	@Override
	public CrashSummary getGroupCrashSummaryData(Integer groupID, GroupHierarchy gh) {
		try {
			
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
	}

	@Override
	public CrashSummary getDriverCrashSummaryData(Integer driverID) {

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
	}

	@Override
	public CrashSummary getVehicleCrashSummaryData(Integer vehicleID) {

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
	}

	@Override
	public List<SpeedPercentItem> getSpeedPercentItems(Integer groupID, Duration duration, GroupHierarchy gh) {
    	logger.debug("getSpeedPercentItems: " + groupID);
        try
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
        
        
	}

	@Override
	public List<IdlePercentItem> getIdlePercentItems(Integer groupID, Duration duration, GroupHierarchy gh) {
    	logger.debug("getIdlePercentItems: " + groupID);
        try
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
        }
        
        
	}
    
}
