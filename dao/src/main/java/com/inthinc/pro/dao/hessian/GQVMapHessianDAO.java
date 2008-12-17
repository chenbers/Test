package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.GQVMapDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.GQVMap;

public class GQVMapHessianDAO extends GenericHessianDAO<GQVMap, Integer> implements GQVMapDAO
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
    public List<GQVMap> getSDScoresByGT(Integer groupID, Integer duration)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getSDScoresByGT(groupID,duration), GQVMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
        
    @Override
    public List<GQVMap> getSDTrendsByGTC(Integer groupID, Integer duration, Integer metric)
    {
        try
        {
            List<Map<String,Object>> dog = getReportService().getSDTrendsByGTC(groupID,duration,metric);            
            
            return getMapper().convertToModelObject(dog, GQVMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
        
}
