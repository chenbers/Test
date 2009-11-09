package com.inthinc.pro.dao.hessian.report;

import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.hessian.proserver.ReportService;

public abstract class AbstractReportHessianDAO {

    protected ReportService reportService;
    protected Mapper mapper;
    
    public AbstractReportHessianDAO() {
        mapper = new SimpleMapper();
    }

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

}
