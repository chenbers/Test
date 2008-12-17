package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DVQMapDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.QuintileMap;

public class DVQMapHessianDAO extends GenericHessianDAO<DVQMap, Integer> implements DVQMapDAO
{
    private static final Logger logger = Logger.getLogger(DVQMapHessianDAO.class);
    
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
    public List<DVQMap> getDVScoresByGT(Integer groupID, Integer duration)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getDVScoresByGT(groupID,duration), DVQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }
        
    @Override
    public List<DVQMap> getSDScoresByGT(Integer groupID, Integer duration)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getSDScoresByGT(groupID,duration), DVQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }
    
    @Override
    public List<DVQMap> getVDScoresByGT(Integer groupID, Integer duration)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getVDScoresByGT(groupID,duration), DVQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }

    public QuintileMap getDPctByGT(Integer groupID, Integer duration, Integer metric) 
    {
        try
        {
            return getMapper().convertToModelObject(reportService.getDPctByGT(groupID, duration, metric), QuintileMap.class);
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }        
   
    }

}
