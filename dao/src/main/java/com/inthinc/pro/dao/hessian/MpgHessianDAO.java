package com.inthinc.pro.dao.hessian;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.MpgDAO;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.model.MpgEntity;

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
    public List<MpgEntity> getEntities(Integer groupID, Integer startDate, Integer endDate)
    {
        List<MpgEntity> scoreList = getMapper().convertToModelObject(reportService.getMpgValues(groupID, startDate, endDate), MpgEntity.class);
        return scoreList;
    }
}
