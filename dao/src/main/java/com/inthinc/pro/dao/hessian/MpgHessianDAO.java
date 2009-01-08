package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.ScoreableEntity;

public class MpgHessianDAO extends GenericHessianDAO<MpgEntity, Integer> implements MpgDAO
{
    private static final Logger logger = Logger.getLogger(MpgHessianDAO.class);
    
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
    public List<MpgEntity> getEntities(Group group, Duration duration)
    {
        try
        {
//groupID = 16777218;            
            List<MpgEntity> scoreList = new ArrayList<MpgEntity>();
            Integer groupID = group.getGroupID();
            
            if (group.getType().equals(GroupType.TEAM))
            {
                List<Map<String, Object>> returnMapList = reportService.getDVScoresByGT(groupID, duration.getCode());
                List<DVQMap> dvqList = getMapper().convertToModelObject(returnMapList, DVQMap.class);
                for (DVQMap dvq : dvqList)
                {
                    MpgEntity mpgEntity = new MpgEntity();
                    mpgEntity.setEntityID(dvq.getDriver().getDriverID());
                    mpgEntity.setGroupID(groupID);
                    mpgEntity.setEntityName(dvq.getDriver().getPerson().getFirst() + " " + dvq.getDriver().getPerson().getLast());
                    
                    mpgEntity.setHeavyValue(dvq.getDriveQ().getMpgHeavy());
                    mpgEntity.setLightValue(dvq.getDriveQ().getMpgLight());
                    mpgEntity.setMediumValue(dvq.getDriveQ().getMpgMedium());
                    
                    scoreList.add(mpgEntity);
                    
                }
                
            }
            else
            {
                List<Map<String, Object>> returnMapList = reportService.getSDScoresByGT(groupID, duration.getCode());
                List<GQMap> gqMapList = getMapper().convertToModelObject(returnMapList, GQMap.class);
    
                for (GQMap gqMap : gqMapList)
                {
                    
                    MpgEntity mpgEntity = new MpgEntity();
                    mpgEntity.setEntityID(gqMap.getGroup().getGroupID());
                    mpgEntity.setGroupID(gqMap.getGroup().getGroupID());
                    mpgEntity.setEntityName(gqMap.getGroup().getName());
                    
                    mpgEntity.setHeavyValue(gqMap.getDriveQ().getMpgHeavy());
                    mpgEntity.setLightValue(gqMap.getDriveQ().getMpgLight());
                    mpgEntity.setMediumValue(gqMap.getDriveQ().getMpgMedium());
                    
                    scoreList.add(mpgEntity);
    
                }
            }
            return scoreList;
            
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }

    @Override
    public List<MpgEntity> getDriverEntities(Integer driverID, Duration duration, Integer count)
    {
        List<MpgEntity> scoreList = new ArrayList<MpgEntity>();
          
        
        List<Map<String, Object>> returnMapList = reportService.getDTrendByDTC(driverID, duration.getCode(), count);
        List<DriveQMap> dqMapList = getMapper().convertToModelObject(returnMapList, DriveQMap.class);

        for (DriveQMap dqMap : dqMapList)
        {
            MpgEntity mpgEntity = new MpgEntity();   
            mpgEntity.setHeavyValue(dqMap.getMpgHeavy());
            mpgEntity.setLightValue(dqMap.getMpgLight());
            mpgEntity.setMediumValue(dqMap.getMpgMedium());
            mpgEntity.setOdometer(dqMap.getOdometer());
            
            scoreList.add(mpgEntity);
        }
        return scoreList;
    }

    @Override
    public List<MpgEntity> getVehicleEntities(Integer vehicleID, Duration duration, Integer count)
    {
        List<MpgEntity> scoreList = new ArrayList<MpgEntity>();
          
        
        List<Map<String, Object>> returnMapList = reportService.getVTrendByVTC(vehicleID, duration.getCode(), count);
        List<DriveQMap> dqMapList = getMapper().convertToModelObject(returnMapList, DriveQMap.class);

        for (DriveQMap dqMap : dqMapList)
        {
            MpgEntity mpgEntity = new MpgEntity();   
            mpgEntity.setHeavyValue(dqMap.getMpgHeavy());
            mpgEntity.setLightValue(dqMap.getMpgLight());
            mpgEntity.setMediumValue(dqMap.getMpgMedium());
            mpgEntity.setOdometer(dqMap.getOdometer());
            
            scoreList.add(mpgEntity);
        }
        return scoreList;
    }
}
