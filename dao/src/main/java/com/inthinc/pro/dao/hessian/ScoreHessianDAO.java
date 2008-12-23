package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.GQVMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.QuintileMap;
import com.inthinc.pro.model.ReportMileageType;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleReportItem;

public class ScoreHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer> implements ScoreDAO
{
    private static final Logger logger = Logger.getLogger(ScoreHessianDAO.class);
    private static final Integer SECONDS_TO_HOURS = 3600;

    private ReportService       reportService;

    public ReportService getReportService()
    {
        return reportService;
    }

    public void setReportService(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @Override
    public List<ScoreableEntity> getTopFiveScores(Integer groupID)
    {
        try
        {
            List<ScoreableEntity> scoreList = getSortedScoreList(groupID);
            return scoreList.subList(0, scoreList.size() > 5 ? 5 : scoreList.size());
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    private List<ScoreableEntity> getSortedScoreList(Integer groupID)
    {
        // TODO: not sure which duration to use for this one
        List<DVQMap> dvqList = getMapper().convertToModelObject(reportService.getDVScoresByGT(groupID, 1), DVQMap.class);
        List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
        for (DVQMap dvq : dvqList)
        {
            ScoreableEntity scoreableEntity = new ScoreableEntity();
            scoreableEntity.setEntityID(dvq.getDriver().getDriverID());
            scoreableEntity.setEntityType(EntityType.ENTITY_DRIVER);
            scoreableEntity.setScoreType(ScoreType.SCORE_OVERALL);
            scoreableEntity.setIdentifier(dvq.getDriver().getPerson().getFirst() + " " + dvq.getDriver().getPerson().getLast());
            scoreableEntity.setScore(dvq.getDriveQ().getOverall());
            scoreList.add(scoreableEntity);
        }
        Collections.sort(scoreList);
        return scoreList;
    }

    @Override
    public List<ScoreableEntity> getBottomFiveScores(Integer groupID)
    {
        try
        {
            List<ScoreableEntity> scoreList = getSortedScoreList(groupID);
            Collections.reverse(scoreList);
            return scoreList.subList(0, scoreList.size() > 5 ? 5 : scoreList.size());
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public ScoreableEntity getAverageScoreByType(Integer groupID, Duration duration, ScoreType scoreType)
    {
        try
        {
            
            // TODO: not sure if this duration mapping is correct
//groupID = 16777218;            
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
    public ScoreableEntity getAverageScoreByTypeAndMiles(Integer driverID, Integer milesBack, ScoreType scoreType)
    {
        try
        {
            DriveQMap driveQMap = getMapper().convertToModelObject(reportService.getDScoreByDM(driverID, milesBack * 100), DriveQMap.class);
            ScoreableEntity scoreableEntity = new ScoreableEntity();
            scoreableEntity.setEntityID(driverID);
            scoreableEntity.setEntityType(EntityType.ENTITY_DRIVER);
            scoreableEntity.setIdentifier("");
            scoreableEntity.setScoreType(scoreType);
            Integer score = driveQMap.getScoreMap().get(scoreType);
            scoreableEntity.setScore((score == null) ? 0 : score);
            scoreableEntity.setIdentifier(""+driveQMap.getEndingOdometer());
            return scoreableEntity;
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
    }
    
    @Override
    public ScoreableEntity getVehicleAverageScoreByTypeAndMiles(Integer vehicleID, Integer milesBack, ScoreType st)
    {
        try
        {
            return getMapper().convertToModelObject(reportService.getVehicleAverageScoreByTypeAndMiles(vehicleID, milesBack, st), ScoreableEntity.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return null;
            else
                throw e;
        }
    }

    @Override
    public List<ScoreableEntity> getScores(Integer groupID, Duration duration, ScoreType scoreType)
    {
        try
        {
//groupID = 16777218;            
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
                scoreableEntity.setScore((score == null) ? 0 : score);
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
            List<Map<String, Object>> list = reportService.getSDTrendsByGTC(groupID, duration.getCode(), ScoreType.SCORE_OVERALL.getDriveQMetric());
            List<GQVMap> gqvList = getMapper().convertToModelObject(list, GQVMap.class);
            
            Map<Integer, List<ScoreableEntity>> returnMap = new HashMap<Integer, List<ScoreableEntity>>();
            for (GQVMap gqv : gqvList)
            {
                List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
                for (DriveQMap driveQMap  :gqv.getDriveQV())
                {
                    ScoreableEntity entity = new ScoreableEntity();
                    entity.setEntityID(gqv.getGroup().getGroupID());
                    entity.setEntityType(EntityType.ENTITY_GROUP);
                    entity.setScore(driveQMap.getOverall());
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
//groupID = 16777218;            
            Map<String, Object> returnMap = reportService.getDPctByGT(groupID, duration.getCode(), scoreType.getDriveQMetric());
            
            QuintileMap quintileMap = getMapper().convertToModelObject(returnMap, QuintileMap.class);

            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
            for (Integer score : quintileMap.getPercentList())
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(groupID);
                entity.setEntityType(EntityType.ENTITY_GROUP);
                entity.setScore(score);
                entity.setScoreType(scoreType);
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
    public List<ScoreableEntity> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, ScoreType scoreType)
    {
        try
        {
            ReportMileageType reportMileageType = ReportMileageType.valueOf(milesBack);
            List<Map<String, Object>> list = reportService.getDTrendByDMC(driverID, reportMileageType.getMilesPerBin()*100, reportMileageType.getBinCount());
            List<DriveQMap> driveQList = getMapper().convertToModelObject(list, DriveQMap.class);
            
            List<ScoreableEntity> scoreList = new ArrayList<ScoreableEntity>();
            for (DriveQMap driveQMap  :driveQList)
            {
                ScoreableEntity entity = new ScoreableEntity();
                entity.setEntityID(driverID);
                entity.setEntityType(EntityType.ENTITY_DRIVER);
                entity.setScoreType(scoreType);
                Integer score = driveQMap.getScoreMap().get(scoreType);
                entity.setScore((score == null) ? 0 : score);
                entity.setIdentifier(""+driveQMap.getEndingOdometer());
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
    public List<ScoreableEntity> getVehicleScoreHistoryByMiles(Integer vehicleID, Integer milesBack, ScoreType scoreType)
    {
        try
        {
            return getMapper().convertToModelObject(reportService.getVehicleScoreHistoryByMiles(vehicleID, milesBack, scoreType.getCode()), ScoreableEntity.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }

    }

    @Override
    public List<ScoreTypeBreakdown> getScoreBreakdownByType(Integer groupID, Duration duration, ScoreType scoreType)
    {
        try
        {
            
            // TODO: not sure if this duration mapping is correct
//groupID = 16777218;            
    
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
            List<DVQMap> result = getMapper().convertToModelObject(
                    reportService.getVDScoresByGT(groupID, duration.getCode()), DVQMap.class);            
            List<VehicleReportItem> lVri = new ArrayList<VehicleReportItem>();
            VehicleReportItem vri = null;
            
            for ( DVQMap d : result ) {
                vri = new VehicleReportItem();
                Vehicle v = d.getVehicle();
                DriveQMap dqm = d.getDriveQ();
                
                vri.setGroupID(d.getVehicle().getGroupID());
                vri.setVehicle(d.getVehicle());
                vri.setMakeModelYear(
                        v.getMake() + "/" +
                        v.getModel() + "/" +
                        v.getYear());
                
                //May or may not have a driver assigned
                vri.setDriver(null);
                if ( d.getDriver() != null ) {
                    vri.setDriver(d.getDriver());
                }
                vri.setMilesDriven(dqm.getEndingOdometer());
                vri.setOverallScore(dqm.getOverall());
                vri.setSpeedScore(dqm.getSpeeding());
                vri.setStyleScore(dqm.getDrivingStyle());
  
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
            List<DVQMap> result = getMapper().convertToModelObject(
                    reportService.getDVScoresByGT(groupID, duration.getCode()), DVQMap.class);            
            List<DriverReportItem> lDri = new ArrayList<DriverReportItem>();
            DriverReportItem dri = null;
            
            for ( DVQMap d : result ) {
                dri = new DriverReportItem();
                Driver v = d.getDriver();
                DriveQMap dqm = d.getDriveQ();
                
                dri.setDriver(v);
                
                dri.setGroupID(v.getPerson().getGroupID());
                dri.setEmployeeID(v.getPerson().getEmpid());
                dri.setEmployee(v.getPerson().getFirst() + " " +
                        v.getPerson().getLast());
                
                //May or may not have a vehicle assigned
                dri.setVehicle(null);
                if ( d.getVehicle() != null ) {
                    dri.setVehicle(d.getVehicle());
                }
                dri.setMilesDriven(dqm.getEndingOdometer());
                dri.setOverallScore(dqm.getOverall());
                dri.setSpeedScore(dqm.getSpeeding());
                dri.setStyleScore(dqm.getDrivingStyle());
                dri.setSeatBeltScore(dqm.getSeatbelt());                
  
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
            List<DVQMap> result = getMapper().convertToModelObject(
                    reportService.getDVScoresByGSE(groupID, start, end), DVQMap.class);            
            List<IdlingReportItem> lIri = new ArrayList<IdlingReportItem>();
            IdlingReportItem iri = null;
            
            for ( DVQMap d : result ) {
                iri = new IdlingReportItem();
                Driver v = d.getDriver();
                DriveQMap dqm = d.getDriveQ();
                                
                iri.setGroupID(v.getPerson().getGroupID());
                iri.setDriver(v);                
                iri.setVehicle(d.getVehicle());
                
                iri.setDriveTime(0);
                iri.setMilesDriven(0);
                iri.setLowHrs("0");
                iri.setHighHrs("0");
                if ( dqm.getDriveTime() != null ) {
                    iri.setDriveTime(dqm.getDriveTime()/SECONDS_TO_HOURS);
                }
                if ( dqm.getEndingOdometer() != null ) {
                    iri.setMilesDriven(dqm.getEndingOdometer());
                }
                if ( dqm.getIdleLo() != null ) {
                    iri.setLowHrs(String.valueOf(dqm.getIdleLo()/SECONDS_TO_HOURS));
                }
                if ( dqm.getIdleHi() != null ) {
                    iri.setHighHrs(String.valueOf(dqm.getIdleHi()/SECONDS_TO_HOURS));                
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
    public Map<ScoreType, ScoreableEntity> getScoreBreakdownByTypeAndMiles(Integer driverID, Integer milesBack, ScoreType scoreType)
    {
        try
        {
            Map<ScoreType, ScoreableEntity> returnMap = new HashMap<ScoreType, ScoreableEntity>();
            DriveQMap driveQMap = getMapper().convertToModelObject(reportService.getDScoreByDM(driverID, milesBack * 100), DriveQMap.class);

            List<ScoreType> subTypeList = scoreType.getSubTypes();
            
            for (ScoreType subType : subTypeList)
            {
                ScoreableEntity scoreableEntity = new ScoreableEntity();
                scoreableEntity.setEntityID(driverID);
                scoreableEntity.setEntityType(EntityType.ENTITY_DRIVER);
                scoreableEntity.setIdentifier("");
                scoreableEntity.setScoreType(subType);
                Integer score = driveQMap.getScoreMap().get(subType);
                scoreableEntity.setScore((score == null) ? 0 : score);
                scoreableEntity.setIdentifier(""+driveQMap.getEndingOdometer());
                
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
