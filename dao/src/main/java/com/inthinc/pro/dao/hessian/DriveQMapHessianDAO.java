package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.DriveQMapDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.DriveQMap;

public class DriveQMapHessianDAO extends GenericHessianDAO<DriveQMap, Integer> implements DriveQMapDAO
{
    private static final Logger logger = Logger.getLogger(DriveQMapHessianDAO.class);
    
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
    public DriveQMap getDScoreByDM(Integer driverID, Integer mileage)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getDScoreByDM(driverID,mileage), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        
    }
        
    @Override    
    public DriveQMap getDScoreByDT(Integer driverID, Integer duration)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getDScoreByDT(driverID,duration), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        
    }

    @Override
    public List<DriveQMap> getDTrendByDTC(Integer driverID, Integer duration, Integer count)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getDTrendByDTC(driverID,duration,count), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }

    @Override
    public List<DriveQMap> getDTrendByDMC(Integer driverID, Integer mileage, Integer count)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getDTrendByDMC(driverID,mileage,count), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }    
   
       
    @Override    
    public DriveQMap getVScoreByVM(Integer vehicleID, Integer mileage)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getVScoreByVM(vehicleID,mileage), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        
    }

    @Override    
    public DriveQMap getVScoreByVT(Integer vehicleID, Integer duration)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getVScoreByVT(vehicleID,duration), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        
    }        

    @Override
    public List<DriveQMap> getVTrendByVMC(Integer vehicleID, Integer mileage, Integer count)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getVTrendByVMC(vehicleID,mileage,count), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    } 
    
    @Override
    public List<DriveQMap> getVTrendByVTC(Integer vehicleID, Integer duration, Integer count)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getVTrendByVTC(vehicleID,duration,count), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }
        
    @Override    
    public DriveQMap getGDScoreByGT(Integer groupID, Integer duration)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getGDScoreByGT(groupID,duration), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        
    }      

    @Override    
    public DriveQMap getGDTrendByGTC(Integer groupID, Integer duration)
    {
        try
        {
            return getMapper().convertToModelObject(getReportService().getGDTrendByGTC(groupID,duration), DriveQMap.class);           
        }
        catch (EmptyResultSetException e)
        {
            return null;
        }
        
    }       
}

