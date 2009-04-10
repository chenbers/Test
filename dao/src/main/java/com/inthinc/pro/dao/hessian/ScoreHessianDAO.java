package com.inthinc.pro.dao.hessian;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverScore;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.GQVMap;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.QuintileMap;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
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
    public List<DriverScore> getSortedDriverScoreList(Integer groupID)
    {
        List<DVQMap> dvqList = getMapper().convertToModelObject(reportService.getDVScoresByGT(groupID, 1), DVQMap.class);
        List<DriverScore> scoreList = new ArrayList<DriverScore>();
        for (DVQMap dvq : dvqList)
        {
            DriverScore driverScore = new DriverScore();
            driverScore.setDriver(dvq.getDriver());
            driverScore.setVehicle(dvq.getVehicle());
            driverScore.setMilesDriven(dvq.getDriveQ().getEndingOdometer() == null ? 0 : dvq.getDriveQ().getEndingOdometer() / 100);
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
    public ScoreableEntity getDriverAverageScoreByType(Integer driverID, Duration duration, ScoreType scoreType)
    {
        try
        {

            // TODO: not sure if this duration mapping is correct
            Map<String, Object> returnMap = reportService.getDScoreByDT(driverID, duration.getCode());
            DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);

            ScoreableEntity scoreableEntity = new ScoreableEntity();
            scoreableEntity.setEntityID(driverID);
            scoreableEntity.setEntityType(EntityType.ENTITY_DRIVER);
            scoreableEntity.setScoreType(scoreType);
            scoreableEntity.setScore(dqMap.getScoreMap().get(scoreType) == null ? NO_SCORE : dqMap.getScoreMap().get(scoreType));
            return scoreableEntity;

        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }

    @Override
    public ScoreableEntity getVehicleAverageScoreByType(Integer vehicleID, Duration duration, ScoreType scoreType)
    {
        try
        {

            // TODO: not sure if this duration mapping is correct
            Map<String, Object> returnMap = reportService.getVScoreByVT(vehicleID, duration.getCode());
            DriveQMap dqMap = getMapper().convertToModelObject(returnMap, DriveQMap.class);

            ScoreableEntity scoreableEntity = new ScoreableEntity();
            scoreableEntity.setEntityID(vehicleID);
            scoreableEntity.setEntityType(EntityType.ENTITY_VEHICLE);
            scoreableEntity.setScoreType(scoreType);
            scoreableEntity.setScore(dqMap.getScoreMap().get(scoreType) == null ? NO_SCORE : dqMap.getScoreMap().get(scoreType));
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
    public Map<Integer, List<ScoreableEntity>> getTrendScores(Integer groupID, Duration duration)
    {

        try
        {
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
                    // entity.setScore(null);
                    entity.setScore(driveQMap.getOverall() == null ? NO_SCORE : driveQMap.getOverall());
                    entity.setScoreType(ScoreType.SCORE_OVERALL);
                    scoreList.add(entity);
                }
                returnMap.put(gqv.getGroup().getGroupID(), scoreList);

            }
            return returnMap;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyMap();
        }
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
    public List<ScoreableEntity> getDriverTrendCumulative(Integer driverID, Duration duration, ScoreType scoreType)
    {
        // Condition added for Coaching Events.  Do not get rolling average for Coaching events only
        Integer code;
        if(scoreType == ScoreType.SCORE_COACHING_EVENTS)
            code = duration.getDvqMetric();
        else
            code = duration.getCode();
           
        try
        {
            List<Map<String, Object>> cumulativList = reportService.getDTrendByDTC(driverID, code, duration.getDvqCount());
            List<DriveQMap> driveQList = getMapper().convertToModelObject(cumulativList, DriveQMap.class);
             
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();

            for (DriveQMap driveQMap : driveQList)
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(driverID);
                entity.setEntityType(EntityType.ENTITY_DRIVER);
                entity.setScoreType(scoreType);
                entity.setIdentifierNum(driveQMap.getOdometer());
                entity.setScore(driveQMap.getScoreMap().get(scoreType));
                entity.setDate(driveQMap.getEndingDate());
                scoreList.add(entity);
            }

            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<ScoreableEntity> getDriverTrendDaily(Integer driverID, Duration duration, ScoreType scoreType)
    {
        try
        {
            List<Map<String, Object>> dailyList = reportService.getDTrendByDTC(driverID, duration.getDvqMetric(), duration.getDvqCount());
            List<DriveQMap> driveQList = getMapper().convertToModelObject(dailyList, DriveQMap.class);
             
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();

            for (DriveQMap driveQMap : driveQList)
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(driverID);
                entity.setEntityType(EntityType.ENTITY_DRIVER);
                entity.setScoreType(scoreType);
                entity.setIdentifierNum(driveQMap.getOdometer());
                entity.setScore(driveQMap.getScoreMap().get(scoreType));
                entity.setDate(driveQMap.getEndingDate());
                scoreList.add(entity);
            }

            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ScoreableEntity> getVehicleTrendCumulative(Integer vehicleID, Duration duration, ScoreType scoreType)
    { 
        // Condition added for Coaching Events.  Do not get cumulative average for Coaching events only
        Integer code;
        if(scoreType == ScoreType.SCORE_COACHING_EVENTS)
            code = duration.getDvqMetric();
        else
            code = duration.getCode();
        
        try
        {
            List<Map<String, Object>> list = reportService.getVTrendByVTC(vehicleID, code, duration.getDvqCount());
            List<DriveQMap> driveQList = getMapper().convertToModelObject(list, DriveQMap.class);

            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();

            for (DriveQMap driveQMap : driveQList)
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(vehicleID);
                entity.setEntityType(EntityType.ENTITY_VEHICLE);
                entity.setScoreType(scoreType);
                entity.setIdentifierNum(driveQMap.getOdometer());
                entity.setScore(driveQMap.getScoreMap().get(scoreType));
                entity.setDate(driveQMap.getEndingDate());
                scoreList.add(entity);
            }

            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }
    
    @Override
    public List<ScoreableEntity> getVehicleTrendDaily(Integer vehicleID, Duration duration, ScoreType scoreType)
    { 
         
        try
        {
            List<Map<String, Object>> list = reportService.getVTrendByVTC(vehicleID, duration.getDvqMetric(), duration.getDvqCount());
            List<DriveQMap> driveQList = getMapper().convertToModelObject(list, DriveQMap.class);

            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();

            for (DriveQMap driveQMap : driveQList)
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(vehicleID);
                entity.setEntityType(EntityType.ENTITY_VEHICLE);
                entity.setScoreType(scoreType);
                entity.setIdentifierNum(driveQMap.getOdometer());
                entity.setScore(driveQMap.getScoreMap().get(scoreType));
                entity.setDate(driveQMap.getEndingDate());
                scoreList.add(entity);
            }

            return scoreList;
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
            List<DVQMap> result = getMapper().convertToModelObject(reportService.getVDScoresByGT(groupID, duration.getCode()), DVQMap.class);
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
                vri.setMilesDriven((dqm.getOdometer() == null ? 0 : dqm.getOdometer() / 100));
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
                driverReportItem.setMilesDriven(driverQMap.getOdometer() == null ? 0 : driverQMap.getOdometer() / 100);
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
                    iri.setLowHrs((float) dqm.getIdleLo() / this.SECONDS_TO_HOURS);
                }
                if (dqm.getIdleHi() != null)
                {
                    iri.setHighHrs((float) dqm.getIdleHi() / this.SECONDS_TO_HOURS);
                }

                if (dqm.getDriveTime() != null)
                {
                    iri.setDriveTime(((float) dqm.getDriveTime() / this.SECONDS_TO_HOURS));
                }
                
                //Total idling            
                Float tot = iri.getLowHrs() + iri.getHighHrs();
                iri.setTotalHrs(tot);
                
                //Percentages, if any driving
                iri.setLowPercent("0.0");
                iri.setHighPercent("0.0");
                iri.setTotalPercent("0.0");
                Float totHrs = new Float(iri.getDriveTime()) +
                    iri.getLowHrs() + iri.getHighHrs();                
                NumberFormat format = NumberFormat.getInstance();
                format.setMaximumFractionDigits(1);
                format.setMinimumFractionDigits(1);
                if ( totHrs != 0.0f ) {
                    Float low = 100.0f*iri.getLowHrs()/totHrs; 
                    iri.setLowPercent(format.format(low));  
                    
                    Float hi = 100.0f*iri.getHighHrs()/totHrs;
                    iri.setHighPercent(format.format(hi));
                    
                    Float total = 100.0f*iri.getTotalHrs()/totHrs;
                    iri.setTotalPercent(format.format(total));
                } 

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
    public Map<ScoreType, ScoreableEntity> getDriverScoreBreakdownByType(Integer driverID, Duration duration, ScoreType scoreType)
    {
        try
        {
            Map<ScoreType, ScoreableEntity> returnMap = new HashMap<ScoreType, ScoreableEntity>();
            DriveQMap driveQMap = getMapper().convertToModelObject(reportService.getDScoreByDT(driverID, duration.getCode()), DriveQMap.class);

            List<ScoreType> subTypeList = scoreType.getSubTypes();

            for (ScoreType subType : subTypeList)
            {
                ScoreableEntity scoreableEntity = new ScoreableEntity();
                scoreableEntity.setEntityID(driverID);
                scoreableEntity.setEntityType(EntityType.ENTITY_DRIVER);
                scoreableEntity.setScoreType(subType);
                Integer score = driveQMap.getScoreMap().get(subType);
                scoreableEntity.setScore((score == null) ? NO_SCORE : score);
                scoreableEntity.setIdentifier("" + driveQMap.getEndingOdometer());

                returnMap.put(subType, scoreableEntity);
            }
            return returnMap;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyMap();
        }
    }

    @Override
    public Map<ScoreType, ScoreableEntity> getVehicleScoreBreakdownByType(Integer vehicleID, Duration duration, ScoreType scoreType)
    {
        try
        {
            Map<ScoreType, ScoreableEntity> returnMap = new HashMap<ScoreType, ScoreableEntity>();
            DriveQMap driveQMap = getMapper().convertToModelObject(reportService.getVScoreByVT(vehicleID, duration.getCode()), DriveQMap.class);

            List<ScoreType> subTypeList = scoreType.getSubTypes();

            for (ScoreType subType : subTypeList)
            {
                ScoreableEntity scoreableEntity = new ScoreableEntity();
                scoreableEntity.setEntityID(vehicleID);
                scoreableEntity.setEntityType(EntityType.ENTITY_VEHICLE);
                scoreableEntity.setIdentifier("");
                scoreableEntity.setScoreType(subType);
                Integer score = driveQMap.getScoreMap().get(subType);
                scoreableEntity.setScore((score == null) ? NO_SCORE : score);
                scoreableEntity.setIdentifier("" + driveQMap.getEndingOdometer());

                returnMap.put(subType, scoreableEntity);
            }
            return returnMap;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyMap();
        }
    }

}
