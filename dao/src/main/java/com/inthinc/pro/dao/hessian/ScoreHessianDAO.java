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
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.GQVMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.QuintileMap;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreTypeBreakdown;
import com.inthinc.pro.model.ScoreableEntity;

public class ScoreHessianDAO extends GenericHessianDAO<ScoreableEntity, Integer> implements ScoreDAO
{
    private static final Logger logger = Logger.getLogger(ScoreHessianDAO.class);

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
            List<ScoreableEntity> scoreList = getMapper().convertToModelObject(reportService.getTopFiveScores(groupID), ScoreableEntity.class);
            return scoreList;
            // TODO Auto-generated method stub
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
    public List<ScoreableEntity> getBottomFiveScores(Integer groupID)
    {
        try
        {
            List<ScoreableEntity> scoreList = getMapper().convertToModelObject(reportService.getBottomFiveScores(groupID), ScoreableEntity.class);
            return scoreList;
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
    public ScoreableEntity getAverageScoreByTypeAndMiles(Integer groupID, Integer milesBack, ScoreType st)
    {
        try
        {
            return getMapper().convertToModelObject(reportService.getAverageScoreByTypeAndMiles(groupID, milesBack, st), ScoreableEntity.class);
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
            return getMapper().convertToModelObject(reportService.getDriverScoreHistoryByMiles(driverID, milesBack, scoreType.getCode()), ScoreableEntity.class);
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
}
