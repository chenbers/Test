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
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.MpgEntity;

public class MpgHessianDAO extends GenericHessianDAO<MpgEntity, Integer> implements MpgDAO
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MpgHessianDAO.class);

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
    public List<MpgEntity> getEntities(Group group, Duration duration, GroupHierarchy gh)
    {
    	logger.debug("getEntities: " + group.getGroupID());

        try
        {
            List<MpgEntity> scoreList = new ArrayList<MpgEntity>();
            Integer groupID = group.getGroupID();

            if (group.getType() == null || group.getType().equals(GroupType.TEAM))
            {
            	// want 30 days daily scores on this, not 7 day aggregates
                List<Map<String, Object>> returnMapList = reportService.getDVScoresByGT(groupID, duration.getDvqCode());
                List<DVQMap> dvqList = getMapper().convertToModelObject(returnMapList, DVQMap.class);
                for (DVQMap dvq : dvqList)
                {
                    MpgEntity mpgEntity = new MpgEntity();
                    mpgEntity.setEntityID(dvq.getDriver().getDriverID());
                    mpgEntity.setGroupID(groupID);
                    mpgEntity.setEntityName(dvq.getDriver().getPerson().getFirst() + " " + dvq.getDriver().getPerson().getLast());

                    mpgEntity.setHeavyValue(dvq.getDriveQ().getMpgHeavy()==null?null:dvq.getDriveQ().getMpgHeavy().doubleValue()/100);
                    mpgEntity.setLightValue(dvq.getDriveQ().getMpgLight()==null?null:dvq.getDriveQ().getMpgLight().doubleValue()/100);
                    mpgEntity.setMediumValue(dvq.getDriveQ().getMpgMedium()==null?null:dvq.getDriveQ().getMpgMedium().doubleValue()/100);

                    mpgEntity.setDate(dvq.getDriveQ().getEndingDate());

                    scoreList.add(mpgEntity);

                }

            }
            else
            {
            	// want 30 days daily scores on this, not 7 day aggregates
                List<Map<String, Object>> returnMapList = reportService.getSDScoresByGT(groupID, duration.getDvqCode());
                List<GQMap> gqMapList = getMapper().convertToModelObject(returnMapList, GQMap.class);

                for (GQMap gqMap : gqMapList)
                {

                    MpgEntity mpgEntity = new MpgEntity();
                    mpgEntity.setEntityID(gqMap.getGroup().getGroupID());
                    mpgEntity.setGroupID(gqMap.getGroup().getGroupID());
                    mpgEntity.setEntityName(gqMap.getGroup().getName());

                    mpgEntity.setHeavyValue(gqMap.getDriveQ().getMpgHeavy()==null?null:gqMap.getDriveQ().getMpgHeavy().doubleValue()/100);
                    mpgEntity.setLightValue(gqMap.getDriveQ().getMpgLight()==null?null:gqMap.getDriveQ().getMpgLight().doubleValue()/100);
                    mpgEntity.setMediumValue(gqMap.getDriveQ().getMpgMedium()==null?null:gqMap.getDriveQ().getMpgMedium().doubleValue()/100);

                    mpgEntity.setDate(gqMap.getDriveQ().getEndingDate());

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
    	logger.debug("getDriverEntities: " + driverID);
        try
        {
            List<MpgEntity> scoreList = new ArrayList<MpgEntity>();
//logger.debug("getDriverEntities calling getDTrendByDTC(" + driverID + "," + duration.getDvqMetric() + "," +  ((count == null) ? duration.getDvqCount() : count)+")");
            List<Map<String, Object>> returnMapList = reportService.getDTrendByDTC(driverID, duration.getAggregationBinSize(), (count == null) ? duration.getDvqCount() : count);
            List<DriveQMap> dqMapList = getMapper().convertToModelObject(returnMapList, DriveQMap.class);

            for (DriveQMap dqMap : dqMapList)
            {
                MpgEntity mpgEntity = new MpgEntity();
                mpgEntity.setHeavyValue(dqMap.getMpgHeavy()==null?null:dqMap.getMpgHeavy().doubleValue()/100);
                mpgEntity.setLightValue(dqMap.getMpgLight()==null?null:dqMap.getMpgLight().doubleValue()/100);
                mpgEntity.setMediumValue(dqMap.getMpgMedium()==null?null:dqMap.getMpgMedium().doubleValue()/100);
                mpgEntity.setOdometer(dqMap.getOdometer());
                mpgEntity.setDate(dqMap.getEndingDate());
//logger.debug("L["+ dqMap.getMpgLight()+"] M["+ dqMap.getMpgMedium()+"] H["+ dqMap.getMpgMedium()+"]");

                scoreList.add(mpgEntity);
            }
            return scoreList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<MpgEntity> getVehicleEntities(Integer vehicleID, Duration duration, Integer count)
    {
    	logger.debug("getVehicleEntities: " + vehicleID);
        try
        {
            List<MpgEntity> scoreList = new ArrayList<MpgEntity>();
//logger.debug("getVehicleEntities calling getVTrendByDTC(" + vehicleID + "," + duration.getDvqMetric() + "," +  ((count == null) ? duration.getDvqCount() : count)+")");

            List<Map<String, Object>> returnMapList = reportService.getVTrendByVTC(vehicleID, duration.getAggregationBinSize(), (count == null) ? duration.getDvqCount() : count);
            List<DriveQMap> dqMapList = getMapper().convertToModelObject(returnMapList, DriveQMap.class);

            for (DriveQMap dqMap : dqMapList)
            {
                MpgEntity mpgEntity = new MpgEntity();
                mpgEntity.setHeavyValue(dqMap.getMpgHeavy()==null?null:dqMap.getMpgHeavy().doubleValue()/100);
                mpgEntity.setLightValue(dqMap.getMpgLight()==null?null:dqMap.getMpgLight().doubleValue()/100);
                mpgEntity.setMediumValue(dqMap.getMpgMedium()==null?null:dqMap.getMpgMedium().doubleValue()/100);
                mpgEntity.setOdometer(dqMap.getOdometer());
//logger.debug("L["+ dqMap.getMpgLight()+"] M["+ dqMap.getMpgMedium()+"] H["+ dqMap.getMpgMedium()+"]");
                mpgEntity.setDate(dqMap.getEndingDate());

                scoreList.add(mpgEntity);
            }
            return scoreList;

        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }

    }
}
