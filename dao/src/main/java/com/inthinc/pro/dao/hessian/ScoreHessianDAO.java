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
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.GQVMap;
import com.inthinc.pro.model.IdlePercentItem;
import com.inthinc.pro.model.IdlingReportItem;
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
    private static final float SECONDS_TO_HOURS = 3600.0f;
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
    public List<DriverScore> getSortedDriverScoreList(Integer groupID, Duration duration)
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

    @Override
    public ScoreableEntity getAverageScoreByType(Integer groupID, Duration duration, ScoreType scoreType)
    {
        try
        {

            Map<String, Object> returnMap = reportService.getGDScoreByGT(groupID, duration.getCode());
            DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);

            ScoreableEntity scoreableEntity = new ScoreableEntity();
            scoreableEntity.setEntityID(groupID);
            scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
            scoreableEntity.setScoreType(scoreType);
            scoreableEntity.setScore(dqMap.getScoreMap().get(scoreType));
            return scoreableEntity;

        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
    @Override
    public List<ScoreableEntity> getScores(Integer groupID, Duration duration, ScoreType scoreType)
    {
        try
        {
            // groupID = 16777218;
            List<Map<String, Object>> returnMapList = reportService.getSDScoresByGT(groupID, duration.getCode());

            List<GQMap> gqMapList = getMapper().convertToModelObject(returnMapList, GQMap.class);

            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
            for (GQMap gqMap : gqMapList)
            {

                ScoreableEntity scoreableEntity = new ScoreableEntity();
                scoreableEntity.setEntityID(gqMap.getGroup().getGroupID());
                scoreableEntity.setEntityType(EntityType.ENTITY_GROUP);
                scoreableEntity.setIdentifier(gqMap.getGroup().getName() == null ? "unknown" : gqMap.getGroup().getName());
                scoreableEntity.setScoreType(scoreType);
                Integer score = gqMap.getDriveQ().getScoreMap().get(scoreType);
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
    public ScoreableEntity getTrendSummaryScore(Integer groupID, Duration duration, ScoreType scoreType)
    {
    	return this.getAverageScoreByType(groupID, duration, scoreType);
    }

    @Override
    public Map<Integer, List<ScoreableEntity>> getTrendScores(Integer groupID, Duration duration)
    {

        try
        {
        	// subgroups
            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, duration.getCode(), duration.getDvqCount());
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
                    scoreList.add(entity);
                    
                }
                returnMap.put(gqv.getGroup().getGroupID(), scoreList);

            }
            returnMap.put(groupID, getTopGroupScoreList(groupID, returnMap));
            return returnMap;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyMap();
        }
    }

    private List<ScoreableEntity> getTopGroupScoreList(Integer topGroupID, Map<Integer, List<ScoreableEntity>> returnMap) {
    	
        List<ScoreableEntity> topGroupScoreList = null;
        for (Integer groupID : returnMap.keySet())
        {
        	List<ScoreableEntity> groupScores = returnMap.get(groupID);
        	if (topGroupScoreList == null)
        	{
        		topGroupScoreList = new ArrayList<ScoreableEntity>();
            	for (ScoreableEntity entity : groupScores)
            	{
                    ScoreableEntity topGroupEntity = new ScoreableEntity();
                    topGroupEntity.setEntityID(topGroupID);
                    topGroupEntity.setEntityType(EntityType.ENTITY_GROUP);
                    topGroupEntity.setScore(0);
                    topGroupEntity.setScoreType(ScoreType.SCORE_OVERALL);
                    topGroupEntity.setIdentifierNum(0);
                    topGroupScoreList.add(topGroupEntity);
            	}
        		
        	}
        	int idx = 0;
        	for (ScoreableEntity entity : groupScores)
        	{
                if (entity.getScore() != NO_SCORE)
                {
                	ScoreableEntity topGroupEntity = topGroupScoreList.get(idx);
                	topGroupEntity.setScore(topGroupEntity.getScore() + entity.getScore());
                	topGroupEntity.setIdentifierNum(topGroupEntity.getIdentifierNum().longValue()+ 1l);
                }
        		idx++;
        	}
        }
        
        
    	for (ScoreableEntity entity : topGroupScoreList)
    	{
    		if (entity.getIdentifierNum().longValue() > 0l)
    		{
    			entity.setScore((int)((long)entity.getScore().intValue()/entity.getIdentifierNum().longValue()));
    		}
    		else
    		{
    			entity.setScore(NO_SCORE);
    		}
    	}
		return topGroupScoreList;
	}

	@Override
    public List<ScoreableEntity> getScoreBreakdown(Integer groupID, Duration duration, ScoreType scoreType)
    {
        try
        {
            Map<String, Object> returnMap = reportService.getDPctByGT(groupID, duration.getCode(), scoreType.getDriveQMetric());

            QuintileMap quintileMap = getMapper().convertToModelObject(returnMap, QuintileMap.class);

            StringBuilder debugBuffer = new StringBuilder();
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
            for (Integer score : quintileMap.getPercentList())
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(groupID);
                entity.setEntityType(EntityType.ENTITY_GROUP);
                entity.setScore(score);
                entity.setScoreType(scoreType);
                scoreList.add(entity);
                debugBuffer.append(score + ",");
            }
            logger.debug("getScoreBreakdown: groupID[" + groupID + "] durationCode[" + duration.getCode() + "] metric[" + scoreType.getDriveQMetric() + "] scores["
                    + debugBuffer.toString() + "]");

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
        try
        {
            List<Map<String, Object>> cumulativList = null;
            if (entityType.equals(EntityType.ENTITY_DRIVER))
            {
            	cumulativList = reportService.getDTrendByDTC(id, duration.getCode(), duration.getDvqCount());
            }
            else
            {
            	cumulativList = reportService.getVTrendByVTC(id, duration.getCode(), duration.getDvqCount());
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
    public List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType)
    {
        try
        {

            List<ScoreTypeBreakdown> scoreTypeBreakdownList = new ArrayList<ScoreTypeBreakdown>();
            List<ScoreType> subTypeList = scoreType.getSubTypes();

            for (ScoreType subType : subTypeList)
            {
                ScoreTypeBreakdown scoreTypeBreakdown = new ScoreTypeBreakdown();

                scoreTypeBreakdown.setScoreType(subType);
                scoreTypeBreakdown.setPercentageList(getScoreBreakdown(groupID, (subType.getDuration() == null) ? duration : subType.getDuration(), subType));
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
    public List<VehicleReportItem> getVehicleReportData(Integer groupID, Duration duration)
    {
        try
        {
        	List<Map<String, Object>> list = reportService.getVDScoresByGT(groupID, duration.getCode());
            List<DVQMap> result = getMapper().convertToModelObject(list, DVQMap.class);
            List<VehicleReportItem> lVri = new ArrayList<VehicleReportItem>();
            VehicleReportItem vri = null;

            for (DVQMap d : result)
            {
                vri = new VehicleReportItem();
                Vehicle v = d.getVehicle();
                DriveQMap dqm = d.getDriveQ();

                vri.setGroupID(d.getVehicle().getGroupID());
                vri.setVehicle(d.getVehicle());
                vri.setMakeModelYear(v.getMake() + "/" + v.getModel() + "/" + v.getYear());

                // May or may not have a driver assigned
                vri.setDriver(null);
                if (d.getDriver() != null)
                {
                    vri.setDriver(d.getDriver());
                }
                vri.setMilesDriven((dqm.getOdometer() == null ? 0 : dqm.getOdometer().longValue() / 100));
                vri.setOverallScore(dqm.getOverall() == null ? NO_SCORE : dqm.getOverall());
                vri.setSpeedScore(dqm.getSpeeding() == null ? NO_SCORE : dqm.getSpeeding());
                vri.setStyleScore(dqm.getDrivingStyle() == null ? NO_SCORE : dqm.getDrivingStyle());

                lVri.add(vri);
                vri = null;
            }

            Collections.sort(lVri); // Sort by vehicle.name

            return lVri;

        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }

    @Override
    public List<DriverReportItem> getDriverReportData(Integer groupID, Duration duration)
    {
        try
        {
            List<DVQMap> result = getMapper().convertToModelObject(reportService.getDVScoresByGT(groupID, duration.getCode()), DVQMap.class);
            List<DriverReportItem> driverReportItemList = new ArrayList<DriverReportItem>();
            DriverReportItem driverReportItem = null;

            for (DVQMap dvq : result)
            {
                driverReportItem = new DriverReportItem();
                Driver driver = dvq.getDriver();
                DriveQMap driverQMap = dvq.getDriveQ();

                driverReportItem.setDriver(driver);

                driverReportItem.setGroupID(driver.getGroupID());
                driverReportItem.setEmployeeID(driver.getPerson().getEmpid());
                driverReportItem.setEmployee(driver.getPerson().getFirst() + " " + driver.getPerson().getLast());

                // May or may not have a vehicle assigned
                driverReportItem.setVehicle(null);
                if (dvq.getVehicle() != null)
                {
                    driverReportItem.setVehicle(dvq.getVehicle());
                }
                driverReportItem.setMilesDriven(driverQMap.getOdometer() == null ? 0 : driverQMap.getOdometer().longValue() / 100);
                driverReportItem.setOverallScore(driverQMap.getOverall() == null ? NO_SCORE : driverQMap.getOverall());
                driverReportItem.setSpeedScore(driverQMap.getSpeeding() == null ? NO_SCORE : driverQMap.getSpeeding());
                driverReportItem.setStyleScore(driverQMap.getDrivingStyle() == null ? NO_SCORE : driverQMap.getDrivingStyle());
                driverReportItem.setSeatBeltScore(driverQMap.getSeatbelt() == null ? NO_SCORE : driverQMap.getSeatbelt());

                driverReportItemList.add(driverReportItem);
                driverReportItem = null;
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
    public List<IdlingReportItem> getIdlingReportData(Integer groupID, Date start, Date end)
    {
        try
        {
            List<DVQMap> result = getMapper().convertToModelObject(
                    reportService.getDVScoresByGSE(groupID, DateUtil.convertDateToSeconds(start), DateUtil.convertDateToSeconds(end)), DVQMap.class);
            List<IdlingReportItem> lIri = new ArrayList<IdlingReportItem>();
            IdlingReportItem iri = null;

            for (DVQMap d : result)
            {
                iri = new IdlingReportItem();
                Driver v = d.getDriver();
                DriveQMap dqm = d.getDriveQ();

                iri.setGroupID(v.getGroupID());
                iri.setDriver(v);
                iri.setVehicle(d.getVehicle());

                iri.setDriveTime(0.0f);
                iri.setMilesDriven(0);
                iri.setLowHrs(0.0f);
                iri.setHighHrs(0.0f);

                if (dqm.getEndingOdometer() != null)
                {
                    iri.setMilesDriven(dqm.getEndingOdometer());
                }
                if (dqm.getIdleLo() != null)
                {
                    iri.setLowHrs(dqm.getIdleLo().floatValue() / SECONDS_TO_HOURS);
                }
                if (dqm.getIdleHi() != null)
                {
                    iri.setHighHrs(dqm.getIdleHi().floatValue() / SECONDS_TO_HOURS);
                }

                if (dqm.getDriveTime() != null)
                {
                    iri.setDriveTime((dqm.getDriveTime().floatValue() / SECONDS_TO_HOURS));
                }
                
                //Total idling            
                Float tot = iri.getLowHrs() + iri.getHighHrs();
                iri.setTotalHrs(tot);
                
//                //Percentages, if any driving
//                iri.setLowPercent("0.0");
//                iri.setHighPercent("0.0");
//                iri.setTotalPercent("0.0");
//                Float totHrs = new Float(iri.getDriveTime()) +
//                    iri.getLowHrs() + iri.getHighHrs();                
//                NumberFormat format = NumberFormat.getInstance();
//                format.setMaximumFractionDigits(1);
//                format.setMinimumFractionDigits(1);
//                if ( totHrs != 0.0f ) {
//                    Float low = 100.0f*iri.getLowHrs()/totHrs; 
//                    iri.setLowPercent(format.format(low));  
//                    
//                    Float hi = 100.0f*iri.getHighHrs()/totHrs;
//                    iri.setHighPercent(format.format(hi));
//                    
//                    Float total = 100.0f*iri.getTotalHrs()/totHrs;
//                    iri.setTotalPercent(format.format(total));
//                } 

                lIri.add(iri);
                iri = null;
            }

            Collections.sort(lIri); // Sort by driver name

            return lIri;

        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }
	@Override
    public List<ScoreItem> getAverageScores(Integer id, EntityType entityType, Duration duration)
    {
        try
        {
            DriveQMap driveQMap = null;
            if (entityType.equals(EntityType.ENTITY_DRIVER))
            {
            	driveQMap = getMapper().convertToModelObject(reportService.getDScoreByDT(id, duration.getCode()), DriveQMap.class);
            }
            else
            {
            	driveQMap = getMapper().convertToModelObject(reportService.getVScoreByVT(id, duration.getCode()), DriveQMap.class);
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
	public CrashSummary getGroupCrashSummaryData(Integer groupID) {
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
	public List<SpeedPercentItem> getSpeedPercentItems(Integer groupID, Duration duration) {
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
	public List<IdlePercentItem> getIdlePercentItems(Integer groupID, Duration duration) {
        try
        {
            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, duration.getAggregationBinSize(), duration.getDvqCount());
logger.info("getIdlePercentItems " + groupID + "," + duration.getAggregationBinSize() + "," + duration.getDvqCount());
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
                	logger.info("nVehicles: " + driveQMap.getnVehicles() + " emuVehicles: " + driveQMap.getEmuRpmVehicles());
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
