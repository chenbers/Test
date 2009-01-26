package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
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
            driverScore.setMilesDriven(dvq.getDriveQ().getEndingOdometer() == null ? 0 : dvq.getDriveQ().getEndingOdometer()/100);
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
            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, duration.getDvqMetric(), duration.getDvqCount());            
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
                    //entity.setScore(null);
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
            logger.debug("getScoreBreakdown: groupID[" + groupID +"] durationCode[" + duration.getCode() + "] metric[" + scoreType.getDriveQMetric() + "] scores[" + debugBuffer.toString() + "]");

            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    // TODO: refactor since count is determined from the duration, we can get rid of that param on the dao call
    @Override
    public List<ScoreableEntity> getDriverScoreHistory(Integer driverID, Duration duration, ScoreType scoreType, Integer count)
    {
        try
        {
            List<Map<String, Object>> list = reportService.getDTrendByDTC(
                    driverID, duration.getDvqMetric(), duration.getDvqCount());
            List<DriveQMap> driveQList = getMapper().convertToModelObject(list, DriveQMap.class);

            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();

            for (DriveQMap driveQMap : driveQList)
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(driverID);
                entity.setEntityType(EntityType.ENTITY_DRIVER);
                entity.setScoreType(scoreType);
                Integer score = driveQMap.getScoreMap().get(scoreType);
                entity.setScore((score == null) ? 50 : score);                   
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

    // TODO: refactor since count is determined from the duration, we can get rid of that param on the dao call
    @Override
    public List<ScoreableEntity> getVehicleScoreHistory(Integer vehicleID, Duration duration, ScoreType scoreType, Integer count)
    {
        
        try
        {
            List<Map<String, Object>> list = reportService.getVTrendByVTC(
                    vehicleID, duration.getDvqMetric(), duration.getDvqCount());
            List<DriveQMap> driveQList = getMapper().convertToModelObject(list, DriveQMap.class);

            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();

            for (DriveQMap driveQMap : driveQList)
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(vehicleID);
                entity.setEntityType(EntityType.ENTITY_VEHICLE);
                entity.setScoreType(scoreType);
                Integer score = driveQMap.getScoreMap().get(scoreType);
                entity.setScore((score == null) ? 50 : score);                
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
                vri.setMilesDriven(dqm.getOdometer()/100);
                vri.setOverallScore(dqm.getOverall() == null ? NO_SCORE : dqm.getOverall());
                vri.setSpeedScore(dqm.getSpeeding() == null ? NO_SCORE : dqm.getSpeeding());
                vri.setStyleScore(dqm.getDrivingStyle() == null ? NO_SCORE :dqm.getDrivingStyle());
                
                lVri.add(vri);
                vri = null;
            }

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
            List<DriverReportItem> lDri = new ArrayList<DriverReportItem>();
            DriverReportItem dri = null;

            for (DVQMap d : result)
            {
                dri = new DriverReportItem();
                Driver v = d.getDriver();
                DriveQMap dqm = d.getDriveQ();

                dri.setDriver(v);

                dri.setGroupID(v.getGroupID());
                dri.setEmployeeID(v.getPerson().getEmpid());
                dri.setEmployee(v.getPerson().getFirst() + " " + v.getPerson().getLast());

                // May or may not have a vehicle assigned
                dri.setVehicle(null);
                if (d.getVehicle() != null)
                {
                    dri.setVehicle(d.getVehicle());
                }
                dri.setMilesDriven(dqm.getOdometer() == null ? 0 : dqm.getOdometer()/100);
                dri.setOverallScore(dqm.getOverall() == null ? NO_SCORE : dqm.getOverall());
                dri.setSpeedScore(dqm.getSpeeding() == null ? NO_SCORE : dqm.getSpeeding());
                dri.setStyleScore(dqm.getDrivingStyle() == null ? NO_SCORE : dqm.getDrivingStyle());
                dri.setSeatBeltScore(dqm.getSeatbelt() == null ? NO_SCORE : dqm.getSeatbelt());
                
                lDri.add(dri);
                dri = null;
            }

            return lDri;

        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }

    @Override
    public List<IdlingReportItem> getIdlingReportData(Integer groupID, Integer start, Integer end)
    {
        try
        {
            List<DVQMap> result = getMapper().convertToModelObject(reportService.getDVScoresByGSE(groupID, start, end), DVQMap.class);
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
                    iri.setLowHrs((float)dqm.getIdleLo() / this.SECONDS_TO_HOURS);
                }
                if (dqm.getIdleHi() != null)
                {
                    iri.setHighHrs((float)dqm.getIdleHi() / this.SECONDS_TO_HOURS);
                }
                
                // total drive time needs to have the idle time included, per Dave H
                if (dqm.getDriveTime() != null)
                {                  
                    float totalDriveTime = ((float)dqm.getDriveTime()/this.SECONDS_TO_HOURS) +
                        iri.getLowHrs() + iri.getHighHrs();
                    iri.setDriveTime(totalDriveTime);
                }

                lIri.add(iri);
                iri = null;
            }

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
